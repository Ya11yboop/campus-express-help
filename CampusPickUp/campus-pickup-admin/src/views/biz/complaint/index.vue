<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" class="search-form">
      <el-form-item label="处理状态">
        <el-select v-model="queryParams.handleStatus" placeholder="全部" clearable style="width: 120px">
          <el-option label="待处理" value="0" />
          <el-option label="已处理" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="学校">
        <el-select v-model="queryParams.schoolId" placeholder="全部学校" clearable filterable :disabled="userStore.isCampusAdmin" style="width: 150px" @change="handleQuery">
          <el-option v-for="s in schoolList" :key="s.schoolId" :label="s.schoolName" :value="s.schoolId" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row class="toolbar">
      <el-button type="danger" icon="Delete" :disabled="!ids.length" @click="handleBatchDelete">批量删除</el-button>
    </el-row>

    <el-table v-loading="loading" :data="list" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="订单号" prop="orderNo" align="center" width="180" show-overflow-tooltip />
      <el-table-column label="类型" prop="complaintType" align="center" width="70">
        <template #default="scope">{{ scope.row.complaintType === '1' ? '申诉' : '投诉' }}</template>
      </el-table-column>
      <el-table-column label="下单用户" prop="userName" align="center" width="110" show-overflow-tooltip />
      <el-table-column label="接单人" prop="targetUserName" align="center" width="110" show-overflow-tooltip />
      <el-table-column label="内容" prop="content" align="center" show-overflow-tooltip />
      <el-table-column label="处理状态" prop="handleStatus" align="center" width="90">
        <template #default="scope">
          <el-tag :type="scope.row.handleStatus === '0' ? 'warning' : 'success'">{{ scope.row.handleStatus === '0' ? '待处理' : '已处理' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="处理结果" prop="handleResult" align="center" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="140">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleOpen(scope.row)">处理</el-button>
          <el-button link type="danger" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
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

    <el-dialog v-model="dialogVisible" title="处理申诉/投诉" width="500px">
      <el-form label-width="80px">
        <el-form-item label="处理结果">
          <el-input v-model="form.handleResult" type="textarea" :rows="3" placeholder="输入处理结果/审核结果" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitHandle">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listComplaint, handleComplaint, delComplaint } from '@/api/complaint'
import { optionselectSchool } from '@/api/system/school'
import { useUserStore } from '@/store/modules/user'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const ids = ref([])
const dialogVisible = ref(false)
const queryParams = reactive({ pageNum: 1, pageSize: 10, handleStatus: undefined, schoolId: undefined })
const form = reactive({ complaintId: undefined, handleResult: '' })
const userStore = useUserStore()
const schoolList = ref([])
function loadSchool() { optionselectSchool().then((res) => (schoolList.value = res.data || [])) }

function getList() {
  const params = { ...queryParams }
  if (params.schoolId == null || params.schoolId === '') delete params.schoolId
  loading.value = true
  listComplaint(params).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function handleQuery() { queryParams.pageNum = 1; getList() }
function resetQuery() { queryParams.handleStatus = undefined; handleQuery() }
function handleOpen(row) { form.complaintId = row.complaintId; form.handleResult = row.handleResult || ''; dialogVisible.value = true }
function submitHandle() {
  handleComplaint({ complaintId: form.complaintId, handleResult: form.handleResult }).then(() => { ElMessage.success('处理成功'); dialogVisible.value = false; getList() })
}
function handleSelectionChange(selection) { ids.value = selection.map((r) => r.complaintId) }
function handleBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${ids.value.length} 条申诉/投诉吗？`, '提示', { type: 'warning' })
    .then(() => delComplaint(ids.value.join(',')).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
function handleDelete(row) {
  ElMessageBox.confirm('确定删除该申诉/投诉吗？', '提示', { type: 'warning' })
    .then(() => delComplaint(row.complaintId).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
onMounted(() => { queryParams.schoolId = userStore.activeSchoolId; getList(); loadSchool() })
</script>

<style scoped>
.search-form { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
