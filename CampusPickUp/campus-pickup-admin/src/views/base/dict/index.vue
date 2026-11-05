<template>
  <div class="app-container">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="字典类型" name="type">
        <el-row class="toolbar">
          <el-button type="primary" icon="Plus" @click="typeAdd">新增类型</el-button>
          <el-button type="danger" icon="Delete" :disabled="!typeIds.length" @click="typeBatchDelete">批量删除</el-button>
        </el-row>
        <el-table v-loading="typeLoading" :data="typeList" border @selection-change="handleTypeSelection">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" type="index" width="70" align="center" />
          <el-table-column label="字典名称" prop="dictName" align="center" />
          <el-table-column label="字典类型" prop="dictType" align="center" />
          <el-table-column label="状态" prop="status" align="center" width="80">
            <template #default="scope">
              <el-tag :type="scope.row.status === '0' ? 'success' : 'info'">{{ scope.row.status === '0' ? '正常' : '停用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="140">
            <template #default="scope">
              <el-button link type="primary" icon="Edit" @click="typeEdit(scope.row)">修改</el-button>
              <el-button link type="danger" icon="Delete" @click="typeDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="字典数据" name="data">
        <el-row class="toolbar">
          <el-button type="primary" icon="Plus" @click="dataAdd">新增数据</el-button>
          <el-button type="danger" icon="Delete" :disabled="!dataIds.length" @click="dataBatchDelete">批量删除</el-button>
        </el-row>
        <el-table v-loading="dataLoading" :data="dataList" border @selection-change="handleDataSelection">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" type="index" width="70" align="center" />
          <el-table-column label="字典类型" prop="dictType" align="center" />
          <el-table-column label="标签" prop="dictLabel" align="center" />
          <el-table-column label="键值" prop="dictValue" align="center" />
          <el-table-column label="排序" prop="dictSort" align="center" width="70" />
          <el-table-column label="状态" prop="status" align="center" width="80">
            <template #default="scope">
              <el-tag :type="scope.row.status === '0' ? 'success' : 'info'">{{ scope.row.status === '0' ? '正常' : '停用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="140">
            <template #default="scope">
              <el-button link type="primary" icon="Edit" @click="dataEdit(scope.row)">修改</el-button>
              <el-button link type="danger" icon="Delete" @click="dataDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <!-- 类型弹窗 -->
    <el-dialog v-model="typeVisible" :title="typeTitle" width="460px" scrollable>
      <el-form ref="typeRef" :model="typeForm" :rules="typeRules" label-width="90px">
        <el-form-item label="字典名称" prop="dictName">
          <el-input v-model="typeForm.dictName" placeholder="字典名称" />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input v-model="typeForm.dictType" placeholder="如 order_status" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="typeForm.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="typeVisible = false">取消</el-button>
        <el-button type="primary" @click="typeSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 数据弹窗 -->
    <el-dialog v-model="dataVisible" :title="dataTitle" width="460px" scrollable>
      <el-form ref="dataRef" :model="dataForm" :rules="dataRules" label-width="90px">
        <el-form-item label="字典类型" prop="dictType">
          <el-input v-model="dataForm.dictType" placeholder="如 order_status" />
        </el-form-item>
        <el-form-item label="标签" prop="dictLabel">
          <el-input v-model="dataForm.dictLabel" placeholder="字典标签" />
        </el-form-item>
        <el-form-item label="键值" prop="dictValue">
          <el-input v-model="dataForm.dictValue" placeholder="字典键值" />
        </el-form-item>
        <el-form-item label="排序" prop="dictSort">
          <el-input-number v-model="dataForm.dictSort" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="dataForm.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dataVisible = false">取消</el-button>
        <el-button type="primary" @click="dataSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listDictType, getDictType, addDictType, updateDictType, delDictType, listDictData, getDictData, addDictData, updateDictData, delDictData } from '@/api/system/dict'

const activeTab = ref('type')
const typeLoading = ref(false)
const dataLoading = ref(false)
const typeList = ref([])
const dataList = ref([])
const typeIds = ref([])
const dataIds = ref([])
const typeVisible = ref(false)
const dataVisible = ref(false)
const typeTitle = ref('')
const dataTitle = ref('')
const typeRef = ref(null)
const dataRef = ref(null)
const typeForm = reactive({})
const dataForm = reactive({})
const typeRules = { dictName: [{ required: true, message: '字典名称不能为空', trigger: 'blur' }], dictType: [{ required: true, message: '字典类型不能为空', trigger: 'blur' }] }
const dataRules = { dictType: [{ required: true, message: '字典类型不能为空', trigger: 'blur' }], dictLabel: [{ required: true, message: '标签不能为空', trigger: 'blur' }] }

function getTypeList() {
  typeLoading.value = true
  listDictType({ pageNum: 1, pageSize: 100 }).then((res) => { typeList.value = res.rows || [] }).finally(() => (typeLoading.value = false))
}
function getDataList() {
  dataLoading.value = true
  listDictData({ pageNum: 1, pageSize: 100 }).then((res) => { dataList.value = res.rows || [] }).finally(() => (dataLoading.value = false))
}
function resetType() { Object.keys(typeForm).forEach((key) => delete typeForm[key]) }
function resetData() { Object.keys(dataForm).forEach((key) => delete dataForm[key]) }
function typeAdd() { resetType(); typeTitle.value = '新增类型'; typeVisible.value = true }
function typeEdit(row) { resetType(); getDictType(row.dictId).then((res) => { Object.assign(typeForm, res.data); typeTitle.value = '修改类型'; typeVisible.value = true }) }
function typeSubmit() {
  typeRef.value.validate((valid) => {
    if (!valid) return
    if (typeForm.dictId != null) {
      updateDictType(typeForm).then(() => { ElMessage.success('修改成功'); typeVisible.value = false; getTypeList() })
    } else {
      addDictType(typeForm).then(() => { ElMessage.success('新增成功'); typeVisible.value = false; getTypeList() })
    }
  })
}
function handleTypeSelection(selection) { typeIds.value = selection.map((r) => r.dictId) }
function typeBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${typeIds.value.length} 个字典类型吗？`, '提示', { type: 'warning' })
    .then(() => delDictType(typeIds.value.join(',')).then(() => { ElMessage.success('删除成功'); getTypeList() }))
    .catch(() => {})
}
function handleDataSelection(selection) { dataIds.value = selection.map((r) => r.dictCode) }
function dataBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${dataIds.value.length} 条字典数据吗？`, '提示', { type: 'warning' })
    .then(() => delDictData(dataIds.value.join(',')).then(() => { ElMessage.success('删除成功'); getDataList() }))
    .catch(() => {})
}
function typeDelete(row) {  ElMessageBox.confirm(`确定删除「${row.dictName}」吗？`, '提示', { type: 'warning' })
    .then(() => delDictType(row.dictId).then(() => { ElMessage.success('删除成功'); getTypeList() }))
    .catch(() => {})
}
function dataAdd() { resetData(); dataTitle.value = '新增数据'; dataVisible.value = true }
function dataEdit(row) { resetData(); getDictData(row.dictCode).then((res) => { Object.assign(dataForm, res.data); dataTitle.value = '修改数据'; dataVisible.value = true }) }
function dataSubmit() {
  dataRef.value.validate((valid) => {
    if (!valid) return
    if (dataForm.dictCode != null) {
      updateDictData(dataForm).then(() => { ElMessage.success('修改成功'); dataVisible.value = false; getDataList() })
    } else {
      addDictData(dataForm).then(() => { ElMessage.success('新增成功'); dataVisible.value = false; getDataList() })
    }
  })
}
function dataDelete(row) {
  ElMessageBox.confirm(`确定删除「${row.dictLabel}」吗？`, '提示', { type: 'warning' })
    .then(() => delDictData(row.dictCode).then(() => { ElMessage.success('删除成功'); getDataList() }))
    .catch(() => {})
}
onMounted(() => { getTypeList(); getDataList() })
</script>

<style scoped>
.toolbar { margin-bottom: 10px; }
</style>
