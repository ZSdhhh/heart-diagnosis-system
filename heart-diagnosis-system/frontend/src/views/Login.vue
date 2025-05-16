<template>
  <div class="login-container">
    <div class="login-box">
      <div class="title">心脏病辅助诊断系统</div>
      <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" label-width="0">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" prefix-icon="User" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" prefix-icon="Lock" type="password" placeholder="密码" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" class="login-button" @click="handleLogin">登录</el-button>
        </el-form-item>
        <div class="options">
          <router-link class="register-link" to="/register">注册账号</router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { reactive, ref, toRefs } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { login } from '@/api/user'
import md5 from 'md5'

export default {
  name: 'Login',
  setup() {
    const router = useRouter()
    const store = useStore()
    const loginFormRef = ref(null)
    
    const state = reactive({
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', message: '请输入用户名' }],
        password: [{ required: true, trigger: 'blur', message: '请输入密码' }]
      },
      loading: false
    })
    
    const handleLogin = () => {
      if (!loginFormRef.value) {
        ElMessage.error('表单引用错误');
        return;
      }
      
      loginFormRef.value.validate(valid => {
        if (valid) {
          state.loading = true
          // 使用MD5加密密码
          const loginData = {
            username: state.loginForm.username,
            password: md5(state.loginForm.password)
          }
          
          login(loginData).then(res => {
            store.dispatch('login', res.data)
            ElMessage.success('登录成功')
            router.push('/home')
          }).catch(error => {
            ElMessage.error(error.message || '登录失败')
          }).finally(() => {
            state.loading = false
          })
        }
      })
    }
    
    return {
      loginFormRef,
      ...toRefs(state),
      handleLogin
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-box {
  width: 350px;
  padding: 30px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 30px;
  text-align: center;
  color: #409EFF;
}

.login-button {
  width: 100%;
}

.options {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}

.register-link {
  color: #409EFF;
  text-decoration: none;
  font-size: 14px;
}
</style> 