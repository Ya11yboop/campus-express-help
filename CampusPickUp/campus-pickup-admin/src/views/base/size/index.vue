<template>
  <div class="app-container">
    <el-row class="toolbar">
      <el-button type="primary" icon="Plus" @click="handleAdd">新增</el-button>
      <el-button type="danger" icon="Delete" :disabled="!ids.length" @click="handleBatchDelete">批量删除</el-button>
    </el-row>

    <el-table v-loading="loading" :data="list" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="分类名称" prop="sizeName" align="center" />
      <el-table-column label="说明" prop="sizeDesc" align="center" />
      <el-table-column label="配送费" prop="fee" align="center" width="90" />
      <el-table-column label="排序" prop="sort" align="center" width="70" />
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
        <el-form-item label="分类名称" prop="sizeName">
          <el-input v-model="form.sizeName" placeholder="如 小件/中件/大件" />
        </el-form-item>
        <el-form-item label="说明" prop="sizeDesc">
          <el-input v-model="form.sizeDesc" placeholder="说明" />
        </el-form-item>
        <el-form-item label="配送费" prop="fee">
          <el-input-number v-model="form.fee" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" :min="0" style="width: 100%" />
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
import { listSize, getSize, addSize, updateSize, delSize } from '@/api/system/size'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const ids = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const queryParams = reactive({ pageNum: 1, pageSize: 10 })
const formRef = ref(null)
const form = reactive({})
const rules = { sizeName: [{ required: true, message: '分类名称不能为空', trigger: 'blur' }] }

function getList() {
  loading.value = true
  listSize(queryParams).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function resetForm() { Object.keys(form).forEach((key) => delete form[key]) }
function handleAdd() { resetForm(); dialogTitle.value = '新增分类'; dialogVisible.value = true }
function handleUpdate(row) {
  resetForm()
  getSize(row.sizeId).then((res) => { Object.assign(form, res.data); dialogTitle.value = '修改分类'; dialogVisible.value = true })
}
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) return
    if (form.sizeId != null) {
      updateSize(form).then(() => { ElMessage.success('修改成功'); dialogVisible.value = false; getList() })
    } else {
      addSize(form).then(() => { ElMessage.success('新增成功'); dialogVisible.value = false; getList() })
    }
  })
}
function handleSelectionChange(selection) { ids.value = selection.map((r) => r.sizeId) }
function handleBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${ids.value.length} 个分类吗？`, '提示', { type: 'warning' })
    .then(() => delSize(ids.value.join(',')).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
function handleDelete(row) {
  ElMessageBox.confirm(`确定删除「${row.sizeName}」吗？`, '提示', { type: 'warning' })
    .then(() => delSize(row.sizeId).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
onMounted(() => getList())
</script>

<style scoped>
.toolbar { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
