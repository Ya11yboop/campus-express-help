<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" class="search-form">
      <el-form-item label="驿站名称">
        <el-input v-model="queryParams.pointName" placeholder="驿站名称" clearable @keyup.enter="handleQuery" />
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
      <el-button type="primary" icon="Plus" @click="handleAdd">新增</el-button>
      <el-button type="danger" icon="Delete" :disabled="!ids.length" @click="handleBatchDelete">批量删除</el-button>
    </el-row>

    <el-table v-loading="loading" :data="list" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="驿站名称" prop="pointName" align="center" />
      <el-table-column label="位置" prop="location" align="center" />
      <el-table-column label="负责人" prop="managerName" align="center" width="90" />
      <el-table-column label="电话" prop="phone" align="center" />
      <el-table-column label="状态" prop="status" align="center" width="80">
        <template #default="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'info'">{{ scope.row.status === '0' ? '营业' : '停用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="140">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px" scrollable @close="resetForm">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="驿站名称" prop="pointName">
          <el-input v-model="form.pointName" placeholder="驿站名称" />
        </el-form-item>
        <el-form-item label="学校" prop="schoolId">
          <el-select v-model="form.schoolId" placeholder="请选择学校" clearable filterable :disabled="userStore.isCampusAdmin" style="width: 100%">
            <el-option v-for="s in schoolList" :key="s.schoolId" :label="s.schoolName" :value="s.schoolId" />
          </el-select>
        </el-form-item>
        <el-form-item label="位置" prop="location">
          <el-input v-model="form.location" placeholder="具体位置" />
        </el-form-item>
        <el-form-item label="负责人" prop="managerName">
          <el-input v-model="form.managerName" placeholder="负责人" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="联系电话" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">营业</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
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
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listPoint, getPoint, addPoint, updatePoint, delPoint } from '@/api/system/point'
import { optionselectSchool } from '@/api/system/school'
import { useUserStore } from '@/store/modules/user'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const ids = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const queryParams = reactive({ pageNum: 1, pageSize: 10, pointName: undefined, schoolId: undefined })
const formRef = ref(null)
const form = reactive({})
const userStore = useUserStore()
const schoolList = ref([])
function loadSchool() { optionselectSchool().then((res) => (schoolList.value = res.data || [])) }
const rules = { pointName: [{ required: true, message: '驿站名称不能为空', trigger: 'blur' }] }

function getList() {
  const params = { ...queryParams }
  if (params.schoolId == null || params.schoolId === '') delete params.schoolId
  loading.value = true
  listPoint(params).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function handleQuery() { queryParams.pageNum = 1; getList() }
function resetQuery() { queryParams.pointName = undefined; handleQuery() }
function resetForm() { Object.keys(form).forEach((key) => delete form[key]) }
function handleAdd() { resetForm(); dialogTitle.value = '新增驿站'; dialogVisible.value = true }
function handleUpdate(row) {
  resetForm()
  getPoint(row.pointId).then((res) => { Object.assign(form, res.data); dialogTitle.value = '修改驿站'; dialogVisible.value = true })
}
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) return
    if (form.pointId != null) {
      updatePoint(form).then(() => { ElMessage.success('修改成功'); dialogVisible.value = false; getList() })
    } else {
      addPoint(form).then(() => { ElMessage.success('新增成功'); dialogVisible.value = false; getList() })
    }
  })
}
function handleSelectionChange(selection) { ids.value = selection.map((r) => r.pointId) }
function handleBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${ids.value.length} 个驿站吗？`, '提示', { type: 'warning' })
    .then(() => delPoint(ids.value.join(',')).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
function handleDelete(row) {
  ElMessageBox.confirm(`确定删除驿站「${row.pointName}」吗？`, '提示', { type: 'warning' })
    .then(() => delPoint(row.pointId).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
onMounted(() => { queryParams.schoolId = userStore.activeSchoolId; getList(); loadSchool() })
</script>

<style scoped>
.search-form { margin-bottom: 10px; }
.toolbar { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
