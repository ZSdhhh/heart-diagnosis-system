<template>
  <div class="register-container">
    <div class="register-box">
      <div class="title">注册账号</div>
      <el-form ref="registerFormRef" :model="registerForm" :rules="registerRules" label-width="0">
        <el-form-item prop="username">
          <el-input v-model="registerForm.username" prefix-icon="User" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="registerForm.password" prefix-icon="Lock" type="password" placeholder="密码" show-password></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" prefix-icon="Lock" type="password" placeholder="确认密码" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" class="register-button" @click="handleRegister">注册</el-button>
        </el-form-item>
        <div class="options">
          <router-link class="login-link" to="/login">已有账号？去登录</router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { reactive, ref, toRefs } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { register } from '@/api/user'
import md5 from 'md5'

export default {
  name: 'Register',
  setup() {
    const router = useRouter()
    const registerFormRef = ref(null)
    
    const validateConfirmPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== state.registerForm.password) {
        callback(new Error('两次输入密码不一致'))
      } else {
        callback()
      }
    }
    
    const state = reactive({
      registerForm: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      registerRules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入用户名' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, trigger: 'blur', message: '请输入密码' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, trigger: 'blur', message: '请确认密码' },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ]
      },
      loading: false
    })
    
    const handleRegister = () => {
      if (!registerFormRef.value) {
        ElMessage.error('表单引用错误');
        return;
      }
      
      registerFormRef.value.validate(valid => {
        if (valid) {
          state.loading = true
          // 密码MD5加密
          const registerData = {
            username: state.registerForm.username,
            password: md5(state.registerForm.password),
            role: 'user'
          }
          
          register(registerData).then(res => {
            ElMessage.success('注册成功')
            router.push('/login')
          }).catch(error => {
            ElMessage.error(error.message || '注册失败')
          }).finally(() => {
            state.loading = false
          })
        }
      })
    }
    
    return {
      registerFormRef,
      ...toRefs(state),
      handleRegister
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.register-box {
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

.register-button {
  width: 100%;
}

.options {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}

.login-link {
  color: #409EFF;
  text-decoration: none;
  font-size: 14px;
}
</style>