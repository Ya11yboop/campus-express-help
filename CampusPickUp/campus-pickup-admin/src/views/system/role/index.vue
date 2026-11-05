<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" class="search-form">
      <el-form-item label="角色名称">
        <el-input v-model="queryParams.roleName" placeholder="角色名称" clearable @keyup.enter="handleQuery" />
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

    <el-table v-loading="loading" :data="roleList" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="角色名称" prop="roleName" align="center" />
      <el-table-column label="权限标识" prop="roleKey" align="center" />
      <el-table-column label="排序" prop="roleSort" align="center" width="80" />
      <el-table-column label="状态" prop="status" align="center" width="80">
        <template #default="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">{{ scope.row.status === '0' ? '正常' : '停用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160">
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
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="角色名称" />
        </el-form-item>
        <el-form-item label="权限标识" prop="roleKey">
          <el-input v-model="form.roleKey" placeholder="如 operation_admin" />
        </el-form-item>
        <el-form-item label="排序" prop="roleSort">
          <el-input-number v-model="form.roleSort" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" style="width: 100%" placeholder="备注" />
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
import { listRole, getRole, addRole, updateRole, delRole } from '@/api/system/role'

const loading = ref(false)
const roleList = ref([])
const total = ref(0)
const ids = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const queryParams = reactive({ pageNum: 1, pageSize: 10, roleName: undefined })
const formRef = ref(null)
const form = reactive({})
const rules = { roleName: [{ required: true, message: '角色名称不能为空', trigger: 'blur' }], roleKey: [{ required: true, message: '权限标识不能为空', trigger: 'blur' }] }

function getList() {
  loading.value = true
  listRole(queryParams).then((res) => { roleList.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function handleQuery() { queryParams.pageNum = 1; getList() }
function resetQuery() { queryParams.roleName = undefined; handleQuery() }
function resetForm() { Object.keys(form).forEach((key) => delete form[key]) }
function handleAdd() { resetForm(); dialogTitle.value = '新增角色'; dialogVisible.value = true }
function handleUpdate(row) {
  resetForm()
  getRole(row.roleId).then((res) => { Object.assign(form, res.data); dialogTitle.value = '修改角色'; dialogVisible.value = true })
}
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) return
    if (form.roleId != null) {
      updateRole(form).then(() => { ElMessage.success('修改成功'); dialogVisible.value = false; getList() })
    } else {
      addRole(form).then(() => { ElMessage.success('新增成功'); dialogVisible.value = false; getList() })
    }
  })
}
function handleSelectionChange(selection) { ids.value = selection.map((r) => r.roleId) }
function handleBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${ids.value.length} 个角色吗？`, '提示', { type: 'warning' })
    .then(() => delRole(ids.value.join(',')).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
function handleDelete(row) {
  ElMessageBox.confirm(`确定删除角色「${row.roleName}」吗？`, '提示', { type: 'warning' })
    .then(() => delRole(row.roleId).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
onMounted(() => getList())
</script>

<style scoped>
.search-form { margin-bottom: 10px; }
.toolbar { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
