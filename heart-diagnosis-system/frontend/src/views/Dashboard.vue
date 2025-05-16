<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>患者统计</span>
            </div>
          </template>
          <div class="card-body">
            <div class="stat-value">{{ patientCount }}</div>
            <div class="stat-label">患者总数</div>
          </div>
          <div class="card-footer">
            <el-button type="primary" @click="goToPatients">管理患者</el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>诊断统计</span>
            </div>
          </template>
          <div class="card-body">
            <div class="stat-value">{{ diagnosisCount }}</div>
            <div class="stat-label">诊断总数</div>
          </div>
          <div class="card-footer">
            <el-button type="primary" @click="goToDiagnosis">开始诊断</el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>算法统计</span>
            </div>
          </template>
          <div class="card-body">
            <div class="stat-value">{{ algorithmCount }}</div>
            <div class="stat-label">可用算法数</div>
          </div>
          <div class="card-footer">
            <el-button type="primary" @click="goToStatistics">查看统计</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <div class="welcome-section">
      <h2>欢迎使用心脏病辅助诊断系统</h2>
      <p>本系统提供心脏病风险评估的辅助诊断功能，帮助医生快速评估患者心脏病风险。</p>
      
      <div class="feature-list">
        <div class="feature-item">
          <el-icon><User /></el-icon>
          <div class="feature-info">
            <h3>患者管理</h3>
            <p>添加、编辑和管理患者信息</p>
          </div>
        </div>
        <div class="feature-item">
          <el-icon><Stethoscope /></el-icon>
          <div class="feature-info">
            <h3>风险诊断</h3>
            <p>基于患者数据进行心脏病风险评估</p>
          </div>
        </div>
        <div class="feature-item">
          <el-icon><DataLine /></el-icon>
          <div class="feature-info">
            <h3>数据统计</h3>
            <p>查看诊断统计和数据分析</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted, toRefs, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { getAllPatients } from '@/api/patient'
import { getStatistics } from '@/api/diagnosis'
import { getAllAlgorithms } from '@/api/algorithm'

export default {
  name: 'Dashboard',
  setup() {
    const router = useRouter()
    const store = useStore()
    
    const state = reactive({
      patientCount: 0,
      diagnosisCount: 0,
      algorithmCount: 0,
      loading: false
    })
    
    // 获取统计数据
    const getStatisticsData = async () => {
      state.loading = true
      try {
        // 获取患者数量
        const patientRes = await getAllPatients()
        state.patientCount = patientRes.data ? patientRes.data.length : 0
        
        // 获取诊断统计
        const statRes = await getStatistics()
        state.diagnosisCount = statRes.data && statRes.data.diagnosisCount ? statRes.data.diagnosisCount : 0
        
        // 获取算法统计
        const algoRes = await getAllAlgorithms()
        const algorithms = algoRes.data || []
        store.dispatch('fetchAlgorithms', algorithms)
        state.algorithmCount = algorithms.length
      } catch (error) {
        console.error('获取统计数据失败', error)
      } finally {
        state.loading = false
      }
    }
    
    // 页面跳转
    const goToPatients = () => {
      router.push('/home/patients')
    }
    
    const goToDiagnosis = () => {
      router.push('/home/diagnosis')
    }
    
    const goToStatistics = () => {
      router.push('/home/statistics')
    }
    
    onMounted(() => {
      getStatisticsData()
    })
    
    return {
      ...toRefs(state),
      goToPatients,
      goToDiagnosis,
      goToStatistics
    }
  }
}
</script>

<style scoped>
.dashboard-container {
  min-height: 100%;
}

.box-card {
  margin-bottom: 20px;
  height: 200px;
  display: flex;
  flex-direction: column;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-body {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px 0;
}

.card-footer {
  text-align: center;
  margin-top: auto;
  margin-bottom: 10px;
}

.stat-value {
  font-size: 36px;
  font-weight: bold;
  color: #409EFF;
}

.stat-label {
  margin-top: 10px;
  color: #606266;
}

.welcome-section {
  background-color: #fff;
  padding: 30px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  margin-top: 20px;
}

.welcome-section h2 {
  margin-top: 0;
  color: #303133;
}

.welcome-section p {
  color: #606266;
  margin-bottom: 30px;
}

.feature-list {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
}

.feature-item {
  display: flex;
  align-items: flex-start;
  width: 30%;
}

.feature-item .el-icon {
  font-size: 24px;
  color: #409EFF;
  margin-right: 10px;
  margin-top: 3px;
}

.feature-info h3 {
  margin: 0 0 10px 0;
  color: #303133;
}

.feature-info p {
  margin: 0;
  color: #606266;
}
</style> 