<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" class="search-form">
      <el-form-item label="订单号">
        <el-input v-model="queryParams.orderNo" placeholder="订单号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="学校">
        <el-select v-model="queryParams.schoolId" placeholder="全部学校" clearable filterable :disabled="userStore.isCampusAdmin" style="width: 150px" @change="handleQuery">
          <el-option v-for="s in schoolList" :key="s.schoolId" :label="s.schoolName" :value="s.schoolId" />
        </el-select>
      </el-form-item>
      <el-form-item label="订单类型">
        <el-select v-model="queryParams.orderType" placeholder="全部" clearable style="width: 110px">
          <el-option label="帮取" value="1" />
          <el-option label="帮寄" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.orderStatus" placeholder="全部" clearable style="width: 110px">
          <el-option label="待接单" value="0" />
          <el-option label="配送中" value="1" />
          <el-option label="已完成" value="2" />
          <el-option label="已取消" value="3" />
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
      <el-table-column label="订单号" prop="orderNo" align="center" min-width="160" />
      <el-table-column label="类型" prop="orderType" align="center" width="70">
        <template #default="scope">{{ scope.row.orderType === '1' ? '帮取' : '帮寄' }}</template>
      </el-table-column>
      <el-table-column label="下单用户" prop="userName" align="center" width="110" show-overflow-tooltip />
      <el-table-column label="接单人" prop="courierName" align="center" width="110" show-overflow-tooltip />
      <el-table-column label="快递单号" prop="trackingNo" align="center" />
      <el-table-column label="配送费" prop="fee" align="center" width="80" />
      <el-table-column label="状态" prop="orderStatus" align="center" width="90">
        <template #default="scope">
          <el-tag :type="{ 0: 'warning', 1: 'primary', 2: 'success', 3: 'info', 4: 'danger' }[scope.row.orderStatus]">
            {{ { 0: '待接单', 1: '配送中', 2: '已完成', 3: '已取消', 4: '申诉中' }[scope.row.orderStatus] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="下单时间" prop="createTime" align="center" width="160" />
      <el-table-column label="操作" align="center" width="140">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)">详情</el-button>
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

    <el-dialog v-model="detailVisible" title="订单详情" width="560px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单号">{{ detail.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="类型">{{ detail.orderType === '1' ? '帮取' : '帮寄' }}</el-descriptions-item>
        <el-descriptions-item label="下单用户">{{ detail.userName }}</el-descriptions-item>
        <el-descriptions-item label="接单人">{{ detail.courierName }}</el-descriptions-item>
        <el-descriptions-item label="快递单号">{{ detail.trackingNo }}</el-descriptions-item>
        <el-descriptions-item label="快递公司">{{ detail.expressCompany }}</el-descriptions-item>
        <el-descriptions-item label="取件地址">{{ detail.pickupAddress }}</el-descriptions-item>
        <el-descriptions-item label="送达地址">{{ detail.deliveryAddress }}</el-descriptions-item>
        <el-descriptions-item label="收件人">{{ detail.receiverName }}</el-descriptions-item>
        <el-descriptions-item label="收件电话">{{ detail.receiverPhone }}</el-descriptions-item>
        <el-descriptions-item label="配送费">{{ detail.fee }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ { 0: '待接单', 1: '配送中', 2: '已完成', 3: '已取消', 4: '申诉中' }[detail.orderStatus] }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ detail.remark }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listOrder, getOrder, delOrder } from '@/api/order'
import { optionselectSchool } from '@/api/system/school'
import { useUserStore } from '@/store/modules/user'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const ids = ref([])
const detailVisible = ref(false)
const detail = ref({})
const queryParams = reactive({ pageNum: 1, pageSize: 10, orderNo: undefined, orderType: undefined, orderStatus: undefined, schoolId: undefined })

function getList() {
  const params = { ...queryParams }
  if (params.schoolId == null || params.schoolId === '') delete params.schoolId
  loading.value = true
  listOrder(params).then((res) => { list.value = res.rows || []; total.value = res.total || 0 }).finally(() => (loading.value = false))
}
const schoolList = ref([])
const userStore = useUserStore()
function loadSchool() { optionselectSchool().then((res) => (schoolList.value = res.data || [])) }
function handleQuery() { queryParams.pageNum = 1; getList() }
function resetQuery() { queryParams.orderNo = undefined; queryParams.orderType = undefined; queryParams.orderStatus = undefined; queryParams.schoolId = undefined; handleQuery() }
function handleDetail(row) {
  getOrder(row.orderId).then((res) => { detail.value = res.data || {}; detailVisible.value = true })
}
function handleSelectionChange(selection) { ids.value = selection.map((r) => r.orderId) }
function handleBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${ids.value.length} 个订单吗？`, '提示', { type: 'warning' })
    .then(() => delOrder(ids.value.join(',')).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
function handleDelete(row) {
  ElMessageBox.confirm(`确定删除订单「${row.orderNo}」吗？`, '提示', { type: 'warning' })
    .then(() => delOrder(row.orderId).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}
onMounted(() => { queryParams.schoolId = userStore.activeSchoolId; getList(); loadSchool() })
</script>

<style scoped>
.search-form { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
