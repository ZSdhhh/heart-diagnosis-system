<template>
  <div class="home-container">
    <el-container>
      <el-aside width="200px">
        <div class="logo">心脏病辅助诊断系统</div>
        <el-menu
          :default-active="activeMenu"
          class="el-menu-vertical"
          background-color="#304156"
          text-color="#fff"
          active-text-color="#409EFF"
          router
        >
          <el-menu-item index="/home/dashboard">
            <el-icon><DataLine /></el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/home/patients">
            <el-icon><User /></el-icon>
            <span>患者管理</span>
          </el-menu-item>
          <el-menu-item index="/home/diagnosis">
            <el-icon><Stethoscope /></el-icon>
            <span>诊断</span>
          </el-menu-item>
          <el-menu-item index="/home/statistics">
            <el-icon><PieChart /></el-icon>
            <span>统计</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div class="header-right">
            <span class="username">{{ username }}</span>
            <el-dropdown trigger="click" @command="handleCommand">
              <span class="el-dropdown-link">
                <el-icon><Setting /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import { ElMessageBox } from 'element-plus'

export default {
  name: 'Home',
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    
    const username = computed(() => {
      const user = store.getters.currentUser
      console.log('当前用户状态:', user)
      return user ? user.username : '用户'
    })
    
    const activeMenu = computed(() => route.path)
    
    const handleCommand = (command) => {
      if (command === 'logout') {
        ElMessageBox.confirm(
          '确定要退出登录吗?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
        ).then(() => {
          store.dispatch('logout')
          router.push('/login')
        }).catch(() => {})
      }
    }
    
    return {
      username,
      activeMenu,
      handleCommand
    }
  }
}
</script>

<style scoped>
.home-container {
  height: 100vh;
}

.el-container {
  height: 100%;
}

.el-aside {
  background-color: #304156;
  color: #fff;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 16px;
  font-weight: bold;
  color: #fff;
  background-color: #263445;
}

.el-header {
  background-color: #fff;
  color: #333;
  line-height: 60px;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.header-right {
  display: flex;
  align-items: center;
}

.username {
  margin-right: 10px;
}

.el-dropdown-link {
  cursor: pointer;
  font-size: 20px;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style> 