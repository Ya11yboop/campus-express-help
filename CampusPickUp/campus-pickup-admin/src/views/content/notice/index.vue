<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" class="search-form">
      <el-form-item label="标题">
        <el-input v-model="queryParams.title" placeholder="标题" clearable @keyup.enter="handleQuery" />
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
      <el-table-column label="标题" prop="title" align="center" />
      <el-table-column label="类型" prop="noticeType" align="center" width="80">
        <template #default="scope">{{ scope.row.noticeType === '1' ? '通知' : '公告' }}</template>
      </el-table-column>
      <el-table-column label="状态" prop="status" align="center" width="80">
        <template #default="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'info'">{{ scope.row.status === '0' ? '发布' : '下线' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" align="center" width="160" />
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="560px" scrollable @close="resetForm">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="标题" />
        </el-form-item>
        <el-form-item label="学校" prop="schoolId">
          <el-select v-model="form.schoolId" placeholder="请选择学校" clearable filterable :disabled="userStore.isCampusAdmin" style="width: 100%">
            <el-option v-for="s in schoolList" :key="s.schoolId" :label="s.schoolName" :value="s.schoolId" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="5" style="width: 100%" placeholder="内容" />
        </el-form-item>
        <el-form-item label="类型" prop="noticeType">
          <el-radio-group v-model="form.noticeType">
            <el-radio label="1">通知</el-radio>
            <el-radio label="2">公告</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">发布</el-radio>
            <el-radio label="1">下线</el-radio>
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
import { listNotice, getNotice, addNotice, updateNotice, delNotice } from '@/api/notice'
import { optionselectSchool } from '@/api/system/school'
import { useUserStore } from '@/store/modules/user'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const ids = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const queryParams = reactive({ pageNum: 1, pageSize: 10, title: undefined, schoolId: undefined })
const formRef = ref(null)
const form = reactive({})
const userStore = useUserStore()
const schoolList = ref([])
function loadSchool() { optionselectSchool().then((res) => (schoolList.value = res.data || [])) }
const rules = { title: [{ required: true, message: '标题不能为空', trigger: 'blur' }] }

function getList() {
  const params = { ...queryParams }
  if (params.schoolId == null || params.schoolId === '') delete params.schoolId
  loading.value = true
  listNotice(params).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function handleQuery() { queryParams.pageNum = 1; getList() }
function resetQuery() { queryParams.title = undefined; handleQuery() }
function resetForm() { Object.keys(form).forEach((key) => delete form[key]) }
function handleAdd() { resetForm(); dialogTitle.value = '新增公告'; dialogVisible.value = true }
function handleUpdate(row) {
  resetForm()
  getNotice(row.noticeId).then((res) => { Object.assign(form, res.data); dialogTitle.value = '修改公告'; dialogVisible.value = true })
}
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) return
    if (form.noticeId != null) {
      updateNotice(form).then(() => { ElMessage.success('修改成功'); dialogVisible.value = false; getList() })
    } else {
      addNotice(form).then(() => { ElMessage.success('新增成功'); dialogVisible.value = false; getList() })
    }
  })
}
function handleSelectionChange(selection) { ids.value = selection.map((r) => r.noticeId) }
function handleBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${ids.value.length} 个公告吗？`, '提示', { type: 'warning' })
    .then(() => delNotice(ids.value.join(',')).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
function handleDelete(row) {
  ElMessageBox.confirm(`确定删除公告「${row.title}」吗？`, '提示', { type: 'warning' })
    .then(() => delNotice(row.noticeId).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
onMounted(() => { queryParams.schoolId = userStore.activeSchoolId; getList(); loadSchool() })
</script>

<style scoped>
.search-form { margin-bottom: 10px; }
.toolbar { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
