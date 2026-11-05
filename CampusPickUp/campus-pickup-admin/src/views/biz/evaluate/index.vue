<template>
  <div class="app-container">
    <el-row class="toolbar">
      <el-select v-model="queryParams.schoolId" placeholder="全部学校" clearable filterable :disabled="userStore.isCampusAdmin" style="width: 150px; margin-right: 10px" @change="handleQuery">
        <el-option v-for="s in schoolList" :key="s.schoolId" :label="s.schoolName" :value="s.schoolId" />
      </el-select>
      <el-button type="danger" icon="Delete" :disabled="!ids.length" @click="handleBatchDelete">批量删除</el-button>
    </el-row>

    <el-table v-loading="loading" :data="list" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="订单ID" prop="orderId" align="center" width="80" />
      <el-table-column label="评分" prop="score" align="center" width="150">
        <template #default="scope">
          <el-rate :model-value="scope.row.score" disabled />
        </template>
      </el-table-column>
      <el-table-column label="评价内容" prop="content" align="center" show-overflow-tooltip />
      <el-table-column label="客服回复" prop="reply" align="center" show-overflow-tooltip />
      <el-table-column label="评价时间" prop="createTime" align="center" width="160" />
      <el-table-column label="操作" align="center" width="140">
        <template #default="scope">
          <el-button link type="primary" icon="ChatLineRound" @click="handleReply(scope.row)">回复</el-button>
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

    <el-dialog v-model="dialogVisible" title="回复评价" width="500px">
      <el-form label-width="80px">
        <el-form-item label="回复内容">
          <el-input v-model="form.reply" type="textarea" :rows="3" placeholder="输入回复内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReply">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listEvaluate, replyEvaluate, delEvaluate } from '@/api/evaluate'
import { optionselectSchool } from '@/api/system/school'
import { useUserStore } from '@/store/modules/user'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const ids = ref([])
const dialogVisible = ref(false)
const queryParams = reactive({ pageNum: 1, pageSize: 10, schoolId: undefined })
const form = reactive({ evaluateId: undefined, reply: '' })
const userStore = useUserStore()
const schoolList = ref([])
function loadSchool() { optionselectSchool().then((res) => (schoolList.value = res.data || [])) }

function getList() {
  const params = { ...queryParams }
  if (params.schoolId == null || params.schoolId === '') delete params.schoolId
  loading.value = true
  listEvaluate(params).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function handleQuery() { queryParams.pageNum = 1; getList() }
function handleReply(row) { form.evaluateId = row.evaluateId; form.reply = row.reply || ''; dialogVisible.value = true }
function submitReply() {
  replyEvaluate({ evaluateId: form.evaluateId, reply: form.reply }).then(() => { ElMessage.success('回复成功'); dialogVisible.value = false; getList() })
}
function handleSelectionChange(selection) { ids.value = selection.map((r) => r.evaluateId) }
function handleBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${ids.value.length} 条评价吗？`, '提示', { type: 'warning' })
    .then(() => delEvaluate(ids.value.join(',')).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
function handleDelete(row) {
  ElMessageBox.confirm('确定删除该评价吗？', '提示', { type: 'warning' })
    .then(() => delEvaluate(row.evaluateId).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
onMounted(() => { queryParams.schoolId = userStore.activeSchoolId; getList(); loadSchool() })
</script>

<style scoped>
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
