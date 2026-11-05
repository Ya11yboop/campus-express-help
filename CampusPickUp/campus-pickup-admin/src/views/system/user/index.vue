<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" class="search-form">
      <el-form-item label="用户名">
        <el-input v-model="queryParams.userName" placeholder="用户名/姓名" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="学号">
        <el-input v-model="queryParams.studentNo" placeholder="学号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="学校">
        <el-select v-model="queryParams.schoolId" placeholder="全部学校" clearable filterable :disabled="userStore.isCampusAdmin" style="width: 160px" @change="handleQuery">
          <el-option v-for="s in schoolList" :key="s.schoolId" :label="s.schoolName" :value="s.schoolId" />
        </el-select>
      </el-form-item>
      <el-form-item label="用户类型">
        <el-select v-model="queryParams.userType" placeholder="全部" clearable style="width: 120px">
          <el-option label="管理员" value="admin" />
          <el-option label="普通用户" value="user" />
          <el-option label="快递员" value="courier" />
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

    <el-table v-loading="loading" :data="userList" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="用户名" prop="userName" align="center" />
      <el-table-column label="真实姓名" prop="realName" align="center" />
      <el-table-column label="学号" prop="studentNo" align="center" />
      <el-table-column label="手机号" prop="phone" align="center" />
      <el-table-column label="信誉分" prop="creditScore" align="center" width="80" />
      <el-table-column label="类型" prop="userType" align="center" width="90">
        <template #default="scope">
          <el-tag>{{ { admin: '管理员', user: '用户', courier: '快递员' }[scope.row.userType] || scope.row.userType }}</el-tag>
        </template>
      </el-table-column>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="620px" scrollable @close="resetForm">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="form.userName" placeholder="登录账号" :disabled="form.userId != null" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="昵称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="昵称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="真实姓名" prop="realName">
              <el-input v-model="form.realName" placeholder="真实姓名" />
            </el-form-item>
          </el-col>
          <el-col v-if="form.userId == null" :span="12">
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" type="password" placeholder="留空默认123456" show-password />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学校" prop="schoolId">
              <el-select v-model="form.schoolId" placeholder="请选择学校" clearable filterable :disabled="userStore.isCampusAdmin" style="width: 100%">
                <el-option v-for="s in schoolList" :key="s.schoolId" :label="s.schoolName" :value="s.schoolId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学号" prop="studentNo">
              <el-input v-model="form.studentNo" placeholder="学号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证" prop="idCard">
              <el-input v-model="form.idCard" placeholder="身份证号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" placeholder="手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择" style="width: 100%">
                <el-option label="男" value="0" />
                <el-option label="女" value="1" />
                <el-option label="未知" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="宿舍楼" prop="dormitoryId">
              <el-select v-model="form.dormitoryId" placeholder="请选择" clearable style="width: 100%">
                <el-option v-for="d in dormitoryList" :key="d.dormitoryId" :label="d.dormitoryName" :value="d.dormitoryId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房间号" prop="roomNo">
              <el-input v-model="form.roomNo" placeholder="房间号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="信誉分" prop="creditScore">
              <el-input-number v-model="form.creditScore" :min="0" :max="1000" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户类型" prop="userType">
              <el-select v-model="form.userType" placeholder="请选择" style="width: 100%">
                <el-option label="管理员" value="admin" />
                <el-option label="普通用户" value="user" />
                <el-option label="快递员" value="courier" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio label="0">正常</el-radio>
                <el-radio label="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
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
import { listUser, getUser, addUser, updateUser, delUser } from '@/api/system/user'
import { optionselectDormitory } from '@/api/system/dormitory'
import { optionselectSchool } from '@/api/system/school'
import { useUserStore } from '@/store/modules/user'

const loading = ref(false)
const userList = ref([])
const total = ref(0)
const ids = ref([])
const dormitoryList = ref([])
const schoolList = ref([])
const userStore = useUserStore()
const dialogVisible = ref(false)
const dialogTitle = ref('')

const queryParams = reactive({ pageNum: 1, pageSize: 10, userName: undefined, studentNo: undefined, schoolId: undefined, userType: undefined })

const formRef = ref(null)
const form = reactive({})

const rules = { userName: [{ required: true, message: '用户名不能为空', trigger: 'blur' }] }

function getList() {
  const params = { ...queryParams }
  if (params.schoolId == null || params.schoolId === '') delete params.schoolId
  loading.value = true
  listUser(params).then((res) => {
    userList.value = res.rows || []
    total.value = res.total || 0
  }).finally(() => (loading.value = false))
}

function loadOptions() {
  optionselectDormitory().then((res) => (dormitoryList.value = res.data || []))
  optionselectSchool().then((res) => (schoolList.value = res.data || []))
}

function handleQuery() { queryParams.pageNum = 1; getList() }
function resetQuery() { queryParams.userName = undefined; queryParams.studentNo = undefined; queryParams.schoolId = undefined; queryParams.userType = undefined; handleQuery() }
function resetForm() { Object.keys(form).forEach((key) => delete form[key]) }
function handleSelectionChange(selection) { ids.value = selection.map((r) => r.userId) }

function handleAdd() {
  resetForm()
  if (userStore.isCampusAdmin) form.schoolId = userStore.mySchoolId
  dialogTitle.value = '新增用户'
  dialogVisible.value = true
}

function handleUpdate(row) {
  resetForm()
  getUser(row.userId).then((res) => {
    Object.assign(form, res.data)
    form.password = undefined
    dialogTitle.value = '修改用户'
    dialogVisible.value = true
  })
}

function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) return
    if (form.userId != null) {
      updateUser(form).then(() => { ElMessage.success('修改成功'); dialogVisible.value = false; getList() })
    } else {
      addUser(form).then(() => { ElMessage.success('新增成功'); dialogVisible.value = false; getList() })
    }
  })
}

function handleDelete(row) {
  ElMessageBox.confirm(`确定删除用户「${row.userName}」吗？`, '提示', { type: 'warning' })
    .then(() => delUser(row.userId).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}

function handleBatchDelete() {
  ElMessageBox.confirm(`确定删除选中的 ${ids.value.length} 个用户吗？`, '提示', { type: 'warning' })
    .then(() => delUser(ids.value.join(',')).then(() => { ElMessage.success('删除成功'); getList() }))
    .catch(() => {})
}

onMounted(() => { queryParams.schoolId = userStore.activeSchoolId; getList(); loadOptions() })
</script>

<style scoped>
.search-form { margin-bottom: 10px; }
.toolbar { margin-bottom: 10px; }
.el-pagination { margin-top: 15px; justify-content: flex-end; }
</style>
