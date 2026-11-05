<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" class="search-form">
      <el-form-item label="审核状态">
        <el-select v-model="queryParams.applyStatus" placeholder="全部" clearable style="width: 150px" @change="handleQuery">
          <el-option label="待审核" value="0" />
          <el-option label="已通过" value="1" />
          <el-option label="已驳回" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="list" border>
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="订单号" prop="orderNo" align="center" min-width="160" show-overflow-tooltip />
      <el-table-column label="接单人" align="center" width="120">
        <template #default="scope">
          {{ scope.row.courierName || ('ID:' + scope.row.courierId) }}
        </template>
      </el-table-column>
      <el-table-column label="撤回理由" prop="reason" align="center" min-width="180" show-overflow-tooltip />
      <el-table-column label="审核状态" prop="applyStatus" align="center" width="90">
        <template #default="scope">
          <el-tag :type="statusType(scope.row.applyStatus)">{{ statusText(scope.row.applyStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核备注" prop="remark" align="center" min-width="160" show-overflow-tooltip />
      <el-table-column label="申请时间" prop="createTime" align="center" width="160" />
      <el-table-column label="操作" align="center" width="160">
        <template #default="scope">
          <template v-if="scope.row.applyStatus === '0'">
            <el-button link type="success" icon="Check" @click="handleAudit(scope.row, true)">通过</el-button>
            <el-button link type="danger" icon="Close" @click="handleAudit(scope.row, false)">驳回</el-button>
          </template>
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
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listWithdrawApply, auditWithdrawApply } from '@/api/withdrawApply'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const queryParams = reactive({ pageNum: 1, pageSize: 10, applyStatus: undefined })

function statusText(s) { return { '0': '待审核', '1': '已通过', '2': '已驳回' }[s] || s }
function statusType(s) { return { '0': 'warning', '1': 'success', '2': 'danger' }[s] || 'info' }

function getList() {
  loading.value = true
  listWithdrawApply(queryParams).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
function handleQuery() { queryParams.pageNum = 1; getList() }
function resetQuery() { queryParams.applyStatus = undefined; handleQuery() }
function handleAudit(row, approve) {
  if (approve) {
    ElMessageBox.confirm('确认通过该撤回申请吗？通过后订单将恢复为待接单', '提示', { type: 'warning' })
      .then(() => auditWithdrawApply(row.applyId, { approve: true, remark: '' }).then(() => { ElMessage.success('已通过'); getList() }))
      .catch(() => {})
  } else {
    ElMessageBox.prompt('请输入驳回原因', '驳回申请', { type: 'warning' })
      .then(({ value }) => auditWithdrawApply(row.applyId, { approve: false, remark: value || '' }).then(() => { ElMessage.success('已驳回'); getList() }))
      .catch(() => {})
  }
}
onMounted(() => getList())
</script>

<style scoped>
.search-form { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
