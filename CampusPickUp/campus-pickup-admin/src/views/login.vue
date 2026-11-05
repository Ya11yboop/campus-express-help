<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-brand">
        <div class="brand-logo">
          <el-icon :size="30"><Van /></el-icon>
        </div>
        <h2 class="brand-title">校园快递配送系统</h2>
        <p class="brand-sub">Campus Pickup Delivery</p>
      </div>

      <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入账号" size="large">
            <template #prefix><el-icon><User /></el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            show-password
            @keyup.enter="handleLogin"
          >
            <template #prefix><el-icon><Lock /></el-icon></template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" class="login-btn" :loading="loading" @click="handleLogin">
            登 录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/modules/user'

const router = useRouter()
const userStore = useUserStore()

const loginRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  username: 'admin',
  password: 'admin123'
})

const loginRules = {
  username: [{ required: true, trigger: 'blur', message: '请输入账号' }],
  password: [{ required: true, trigger: 'blur', message: '请输入密码' }]
}

function handleLogin() {
  loginRef.value.validate((valid) => {
    if (!valid) return
    loading.value = true
    userStore
      .login(loginForm)
      .then(() => {
        ElMessage.success('登录成功')
        router.push('/')
      })
      .catch(() => {})
      .finally(() => {
        loading.value = false
      })
  })
}
</script>

<style scoped>
.login-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: linear-gradient(135deg, #1e3c72 0%, #2a5298 45%, #409eff 100%);
  overflow: hidden;
}

.login-container::before {
  content: '';
  position: absolute;
  width: 520px;
  height: 520px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.06);
  top: -180px;
  right: -120px;
}

.login-container::after {
  content: '';
  position: absolute;
  width: 420px;
  height: 420px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.05);
  bottom: -160px;
  left: -100px;
}

.login-box {
  position: relative;
  z-index: 1;
  width: 420px;
  padding: 40px 36px;
  background: #fff;
  border-radius: 14px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.25);
}

.login-brand {
  text-align: center;
  margin-bottom: 28px;
}

.brand-logo {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 64px;
  height: 64px;
  margin-bottom: 14px;
  border-radius: 16px;
  color: #fff;
  background: linear-gradient(135deg, #409eff, #2a5298);
  box-shadow: 0 8px 20px rgba(64, 158, 255, 0.35);
}

.brand-title {
  margin: 0;
  font-size: 22px;
  color: #1f2d3d;
  letter-spacing: 1px;
}

.brand-sub {
  margin: 8px 0 0;
  color: #909399;
  font-size: 13px;
  letter-spacing: 0.5px;
}

.login-form {
  margin-top: 8px;
}

.login-btn {
  width: 100%;
  letter-spacing: 6px;
  font-weight: 600;
}
</style>
