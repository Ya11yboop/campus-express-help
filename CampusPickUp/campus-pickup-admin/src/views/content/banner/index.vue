<template>
  <div class="app-container">
    <el-row class="toolbar">
      <el-button type="primary" icon="Plus" @click="handleAdd">新增</el-button>
      <el-button type="danger" icon="Delete" :disabled="!ids.length" @click="handleBatchDelete">批量删除</el-button>
    </el-row>

    <el-table v-loading="loading" :data="list" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="标题" prop="title" align="center" />
      <el-table-column label="图片" prop="image" align="center" width="180">
        <template #default="scope">
          <el-image :src="scope.row.image" fit="cover" style="width: 120px; height: 50px" />
        </template>
      </el-table-column>
      <el-table-column label="排序" prop="sort" align="center" width="70" />
      <el-table-column label="状态" prop="status" align="center" width="80">
        <template #default="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'info'">{{ scope.row.status === '0' ? '启用' : '停用' }}</el-tag>
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
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="标题" />
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <el-input v-model="form.image" placeholder="图片地址" />
          <el-upload :http-request="handleUpload" :show-file-list="false" accept="image/*" style="margin-top: 8px">
            <el-button type="primary" plain>上传图片</el-button>
          </el-upload>
          <el-image
            v-if="form.image"
            :src="form.image"
            fit="cover"
            :preview-src-list="[form.image]"
            preview-teleported
            style="width: 240px; height: 100px; margin-top: 8px; border-radius: 6px; display: block"
          />
        </el-form-item>
        <el-form-item label="跳转地址" prop="linkUrl">
          <el-input v-model="form.linkUrl" placeholder="跳转链接" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">启用</el-radio>
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
import { listBanner, getBanner, addBanner, updateBanner, delBanner } from '@/api/banner'
import { uploadFile } from '@/api/upload'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const ids = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const queryParams = reactive({ pageNum: 1, pageSize: 10 })
const formRef = ref(null)
const form = reactive({})
const rules = { image: [{ required: true, message: '图片不能为空', trigger: 'blur' }] }

function getList() {
  loading.value = true
  listBanner(queryParams).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function resetForm() { Object.keys(form).forEach((key) => delete form[key]) }
function handleAdd() { resetForm(); dialogTitle.value = '新增轮播图'; dialogVisible.value = true }
function handleUpdate(row) {
  resetForm()
  getBanner(row.bannerId).then((res) => { Object.assign(form, res.data); dialogTitle.value = '修改轮播图'; dialogVisible.value = true })
}
function handleUpload(options) {
  const formData = new FormData()
  formData.append('file', options.file)
  uploadFile(formData).then((res) => { form.image = res.url; ElMessage.success('上传成功') })
}
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) return
    if (form.bannerId != null) {
      updateBanner(form).then(() => { ElMessage.success('修改成功'); dialogVisible.value = false; getList() })
    } else {
      addBanner(form).then(() => { ElMessage.success('新增成功'); dialogVisible.value = false; getList() })
    }
  })
}
function handleSelectionChange(selection) { ids.value = selection.map((r) => r.bannerId) }
function handleBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${ids.value.length} 个轮播图吗？`, '提示', { type: 'warning' })
    .then(() => delBanner(ids.value.join(',')).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
function handleDelete(row) {
  ElMessageBox.confirm(`确定删除轮播图「${row.title}」吗？`, '提示', { type: 'warning' })
    .then(() => delBanner(row.bannerId).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
onMounted(() => getList())
</script>

<style scoped>
.toolbar { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
