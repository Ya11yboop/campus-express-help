<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" class="search-form">
      <el-form-item label="学校名称">
        <el-input v-model="queryParams.schoolName" placeholder="学校名称" clearable @keyup.enter="handleQuery" />
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
      <el-table-column label="学校名称" prop="schoolName" align="center" />
      <el-table-column label="学校编码" prop="schoolCode" align="center" />
      <el-table-column label="地址" prop="address" align="center" />
      <el-table-column label="状态" prop="status" align="center" width="80">
        <template #default="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'info'">{{ scope.row.status === '0' ? '正常' : '停用' }}</el-tag>
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
        <el-form-item label="学校名称" prop="schoolName">
          <el-input v-model="form.schoolName" placeholder="学校名称" />
        </el-form-item>
        <el-form-item label="学校编码" prop="schoolCode">
          <el-input v-model="form.schoolCode" placeholder="学校编码" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="学校地址" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
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
import { listSchool, getSchool, addSchool, updateSchool, delSchool } from '@/api/system/school'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const ids = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const queryParams = reactive({ pageNum: 1, pageSize: 10, schoolName: undefined })
const formRef = ref(null)
const form = reactive({})
const rules = { schoolName: [{ required: true, message: '学校名称不能为空', trigger: 'blur' }] }

function getList() {
  loading.value = true
  listSchool(queryParams).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function handleQuery() { queryParams.pageNum = 1; getList() }
function resetQuery() { queryParams.schoolName = undefined; handleQuery() }
function resetForm() { Object.keys(form).forEach((key) => delete form[key]) }
function handleSelectionChange(selection) { ids.value = selection.map((r) => r.schoolId) }
function handleAdd() { resetForm(); dialogTitle.value = '新增学校'; dialogVisible.value = true }
function handleUpdate(row) {
  resetForm()
  getSchool(row.schoolId).then((res) => { Object.assign(form, res.data); dialogTitle.value = '修改学校'; dialogVisible.value = true })
}
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) return
    if (form.schoolId != null) {
      updateSchool(form).then(() => { ElMessage.success('修改成功'); dialogVisible.value = false; getList() })
    } else {
      addSchool(form).then(() => { ElMessage.success('新增成功'); dialogVisible.value = false; getList() })
    }
  })
}
function handleDelete(row) {
  ElMessageBox.confirm(`确定删除学校「${row.schoolName}」吗？`, '提示', { type: 'warning' })
    .then(() => delSchool(row.schoolId).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
function handleBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${ids.value.length} 个学校吗？`, '提示', { type: 'warning' })
    .then(() => delSchool(ids.value.join(',')).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
onMounted(() => getList())
</script>

<style scoped>
.search-form { margin-bottom: 10px; }
.toolbar { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
