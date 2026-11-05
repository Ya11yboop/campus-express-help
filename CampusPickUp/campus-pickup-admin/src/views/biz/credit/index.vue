<template>
  <div class="app-container">
    <el-row class="toolbar">
      <el-select v-model="queryParams.schoolId" placeholder="全部学校" clearable filterable :disabled="userStore.isCampusAdmin" style="width: 150px; margin-right: 10px" @change="handleQuery">
        <el-option v-for="s in schoolList" :key="s.schoolId" :label="s.schoolName" :value="s.schoolId" />
      </el-select>
      <el-button type="primary" icon="Plus" @click="dialogVisible = true">标记信誉分</el-button>
    </el-row>

    <el-table v-loading="loading" :data="list" border>
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="用户ID" prop="userId" align="center" width="90" />
      <el-table-column label="变更分数" prop="changeScore" align="center" width="90">
        <template #default="scope">
          <span :style="{ color: scope.row.changeScore >= 0 ? 'green' : 'red' }">{{ scope.row.changeScore }}</span>
        </template>
      </el-table-column>
      <el-table-column label="变更后信誉分" prop="creditScore" align="center" width="110" />
      <el-table-column label="原因" prop="reason" align="center" show-overflow-tooltip />
      <el-table-column label="操作人ID" prop="operatorId" align="center" width="90" />
      <el-table-column label="记录时间" prop="createTime" align="center" width="160" />
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

    <el-dialog v-model="dialogVisible" title="标记信誉分" width="450px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="用户ID" prop="userId">
          <el-input-number v-model="form.userId" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="变更分数" prop="changeScore">
          <el-input-number v-model="form.changeScore" style="width: 100%" />
        </el-form-item>
        <el-form-item label="原因" prop="reason">
          <el-input v-model="form.reason" type="textarea" :rows="2" placeholder="变更原因" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitChange">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { listCredit, changeCredit } from '@/api/credit'
import { optionselectSchool } from '@/api/system/school'
import { useUserStore } from '@/store/modules/user'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const queryParams = reactive({ pageNum: 1, pageSize: 10, schoolId: undefined })
const formRef = ref(null)
const form = reactive({ userId: undefined, changeScore: 0, reason: '' })
const userStore = useUserStore()
const schoolList = ref([])
function loadSchool() { optionselectSchool().then((res) => (schoolList.value = res.data || [])) }
const rules = { userId: [{ required: true, message: '用户ID不能为空', trigger: 'blur' }], changeScore: [{ required: true, message: '变更分数不能为空', trigger: 'blur' }] }

function getList() {
  const params = { ...queryParams }
  if (params.schoolId == null || params.schoolId === '') delete params.schoolId
  loading.value = true
  listCredit(params).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function handleQuery() { queryParams.pageNum = 1; getList() }
function submitChange() {
  formRef.value.validate((valid) => {
    if (!valid) return
    changeCredit(form).then(() => { ElMessage.success('更新成功'); dialogVisible.value = false; form.reason = ''; getList() })
  })
}
onMounted(() => { queryParams.schoolId = userStore.activeSchoolId; getList(); loadSchool() })
</script>

<style scoped>
.toolbar { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
