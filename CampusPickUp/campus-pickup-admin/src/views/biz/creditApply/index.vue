<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" class="search-form">
      <el-form-item label="审核状态">
        <el-select v-model="queryParams.applyStatus" placeholder="全部" clearable style="width: 150px" @change="handleQuery">
          <el-option label="待审核" value="0" />
          <el-option label="已通过" value="1" />
          <el-option label="已驳回" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row class="toolbar">
      <el-button v-if="canSubmit" type="primary" icon="Plus" @click="handleAdd">新增扣除申请</el-button>
    </el-row>

    <el-table v-loading="loading" :data="list" border>
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="用户" align="center" width="140">
        <template #default="scope">
          {{ scope.row.userName || ('ID:' + scope.row.userId) }}
        </template>
      </el-table-column>
      <el-table-column label="扣除分数" prop="changeScore" align="center" width="90">
        <template #default="scope">
          <span style="color: #f56c6c">{{ scope.row.changeScore }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请原因" prop="reason" align="center" show-overflow-tooltip />
      <el-table-column label="申请人ID" prop="applyUserId" align="center" width="90" />
      <el-table-column label="审核状态" prop="applyStatus" align="center" width="90">
        <template #default="scope">
          <el-tag :type="statusType(scope.row.applyStatus)">{{ statusText(scope.row.applyStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核备注" prop="remark" align="center" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="160">
        <template #default="scope">
          <template v-if="scope.row.applyStatus === '0' && canAudit">
            <el-button link type="success" icon="Check" @click="handleAudit(scope.row, true)">通过</el-button>
            <el-button link type="danger" icon="Close" @click="handleAudit(scope.row, false)">驳回</el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-model:current-page="queryParams.pageNum"
      v-model:page-size="queryParams.pageSize"
      :total="total"
      :page-sizes="[10, 20, 30, 50]"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="getList"
      @current-change="getList"
    />

    <el-dialog v-model="dialogVisible" title="新增扣除申请" width="480px" scrollable @close="resetForm">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="用户" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择用户" filterable style="width: 100%">
            <el-option v-for="u in userList" :key="u.userId" :label="u.userName + (u.realName ? ' - ' + u.realName : '')" :value="u.userId" />
          </el-select>
        </el-form-item>
        <el-form-item label="扣除分数" prop="changeScore">
          <el-input-number v-model="form.changeScore" :max="-1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="申请原因" prop="reason">
          <el-input v-model="form.reason" type="textarea" :rows="2" style="width: 100%" placeholder="申请原因" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listCreditApply, addCreditApply, auditCreditApply } from '@/api/creditApply'
import { optionselectUser } from '@/api/system/user'
import { useUserStore } from '@/store/modules/user'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const userList = ref([])
const queryParams = reactive({ pageNum: 1, pageSize: 10, applyStatus: undefined })
const formRef = ref(null)
const form = reactive({})
const userStore = useUserStore()

// 客服可提交申请
const canSubmit = computed(() => userStore.roles.some((r) => ['cs_admin', 'campus_cs_admin', 'super_admin'].includes(r)))
// 超管/管理员可审核
const canAudit = computed(() => userStore.roles.some((r) => ['super_admin', 'operation_admin', 'campus_admin', 'campus_operation_admin'].includes(r)))

const rules = {
  userId: [{ required: true, message: '用户ID不能为空', trigger: 'blur' }],
  changeScore: [{ required: true, message: '扣除分数不能为空', trigger: 'blur' }],
  reason: [{ required: true, message: '申请原因不能为空', trigger: 'blur' }]
}

function statusText(s) { return { '0': '待审核', '1': '已通过', '2': '已驳回' }[s] || s }
function statusType(s) { return { '0': 'warning', '1': 'success', '2': 'danger' }[s] || 'info' }

function getList() {
  loading.value = true
  listCreditApply(queryParams).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function handleQuery() { queryParams.pageNum = 1; getList() }
function resetQuery() { queryParams.applyStatus = undefined; handleQuery() }
function resetForm() { Object.keys(form).forEach((key) => delete form[key]) }
function handleAdd() { resetForm(); dialogVisible.value = true }
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) return
    addCreditApply(form).then(() => { ElMessage.success('提交成功'); dialogVisible.value = false; getList() })
  })
}
function handleAudit(row, approve) {
  if (approve) {
    ElMessageBox.confirm('确定通过该扣除申请吗？通过后将扣除该用户信誉分', '提示', { type: 'warning' })
      .then(() => auditCreditApply(row.applyId, { approve: true, remark: '' }).then(() => { ElMessage.success('已通过'); getList() }))
      .catch(() => {})
  } else {
    ElMessageBox.prompt('请输入驳回原因', '驳回申请', { type: 'warning' })
      .then(({ value }) => auditCreditApply(row.applyId, { approve: false, remark: value || '' }).then(() => { ElMessage.success('已驳回'); getList() }))
      .catch(() => {})
  }
}
function loadUsers() {
  optionselectUser().then((res) => { userList.value = res.data || [] })
}
onMounted(() => { getList(); loadUsers() })
</script>

<style scoped>
.search-form { margin-bottom: 10px; }
.toolbar { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
