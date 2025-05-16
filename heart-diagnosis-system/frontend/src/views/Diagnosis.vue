<template>
  <div class="diagnosis-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>心脏病诊断</span>
        </div>
      </template>
      
      <el-form :model="form" label-width="120px">
        <el-form-item label="选择患者">
          <el-select v-model="form.patientId" placeholder="请选择患者" style="width: 100%">
            <el-option 
              v-for="patient in patients" 
              :key="patient.id" 
              :label="patient.name" 
              :value="patient.id">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="选择算法">
          <el-select v-model="form.algorithm" placeholder="请选择诊断算法" style="width: 100%;">
            <el-option 
              v-for="algo in algorithms" 
              :key="algo.id" 
              :label="algo.name" 
              :value="algo.name">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleDiagnosis" :loading="loading">开始诊断</el-button>
        </el-form-item>
      </el-form>
      
      <div v-if="diagnosisResult !== null" class="result-container">
        <h3>诊断结果</h3>
        <el-progress 
          type="circle" 
          :percentage="Math.round(diagnosisResult * 100)" 
          :color="getResultColor(diagnosisResult)"
          :status="diagnosisResult > riskThresholds.high ? 'exception' : (diagnosisResult > riskThresholds.medium ? 'warning' : 'success')">
        </el-progress>
        <div class="risk-text">
          心脏病风险: <span :style="{color: getResultColor(diagnosisResult)}">{{ getRiskLevel(diagnosisResult) }}</span>
        </div>
        <div class="risk-description">
          {{ getRiskDescription(diagnosisResult) }}
        </div>
      </div>
      
      <div v-if="form.patientId && historyList.length > 0" class="history-container">
        <h3>历史诊断记录</h3>
        <el-table :data="historyList" style="width: 100%">
          <el-table-column prop="createTime" label="诊断时间" width="180">
            <template #default="scope">
              {{ scope.row.createTime ? new Date(scope.row.createTime).toLocaleString() : '未知时间' }}
            </template>
          </el-table-column>
          <el-table-column prop="algorithm" label="算法">
            <template #default="scope">
              {{ decodeAlgorithmName(scope.row.algorithm) }}
            </template>
          </el-table-column>
          <el-table-column label="风险值">
            <template #default="scope">
              {{ (scope.row.result * 100).toFixed(2) }}%
            </template>
          </el-table-column>
          <el-table-column label="风险等级">
            <template #default="scope">
              <span :style="{color: getResultColor(scope.row.result)}">
                {{ getRiskLevel(scope.row.result) }}
              </span>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination-container">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[5, 10, 20, 50]"
            :small="false"
            :disabled="false"
            :background="true"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { ref, reactive, computed, toRefs, watch, onMounted } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { getAllPatients } from '@/api/patient'
import { diagnose, getDiagnosisByPatientId, getDiagnosisByPatientIdWithPaging } from '@/api/diagnosis'
import { getEnabledAlgorithms } from '@/api/algorithm'

export default {
  name: 'Diagnosis',
  setup() {
    const store = useStore()
    
    // 风险级别阈值配置
    const riskThresholds = {
      high: 0.7,  // 高风险阈值 
      medium: 0.3 // 中风险阈值
    }
    
    // 风险级别颜色配置
    const riskLevelColors = {
      high: '#F56C6C',  // 高风险-红色
      medium: '#E6A23C', // 中风险-橙色
      low: '#67C23A'     // 低风险-绿色
    }
    
    const state = reactive({
      form: {
        patientId: '',
        algorithm: ''
      },
      patients: [],
      algorithms: [],
      diagnosisResult: null,
      loading: false,
      historyList: [],
      currentPage: 1,
      pageSize: 10,
      total: 0
    })
    
    // 获取患者列表
    const getPatients = async () => {
      try {
        const res = await getAllPatients()
        state.patients = res.data
      } catch (error) {
        ElMessage.error('获取患者列表失败')
      }
    }
    
    // 获取算法列表
    const getAlgorithms = async () => {
      try {
        const res = await getEnabledAlgorithms()
        state.algorithms = res.data
        store.dispatch('fetchAlgorithms', res.data)
      } catch (error) {
        ElMessage.error('获取算法列表失败')
      }
    }
    
    // 获取历史诊断记录
    const getDiagnosisHistory = async (patientId) => {
      if (!patientId) return
      
      try {
        const res = await getDiagnosisByPatientIdWithPaging(patientId, state.currentPage, state.pageSize)
        state.historyList = res.data.records
        state.total = res.data.total
      } catch (error) {
        ElMessage.error('获取诊断历史失败')
      }
    }
    
    // 开始诊断
    const handleDiagnosis = async () => {
      if (!state.form.patientId) {
        ElMessage.warning('请选择患者')
        return
      }
      
      state.loading = true
      try {
        // 确保算法名称使用UTF-8编码
        const data = {
          patientId: state.form.patientId,
          algorithm: state.form.algorithm
        }
        
        const res = await diagnose(data)
        state.diagnosisResult = res.data
        
        // 刷新诊断历史
        getDiagnosisHistory(state.form.patientId)
      } catch (error) {
        ElMessage.error('诊断失败')
      } finally {
        state.loading = false
      }
    }
    
    // 获取风险颜色
    const getResultColor = (result) => {
      if (result > riskThresholds.high) return riskLevelColors.high
      if (result > riskThresholds.medium) return riskLevelColors.medium
      return riskLevelColors.low
    }
    
    // 获取风险等级
    const getRiskLevel = (result) => {
      if (result > riskThresholds.high) return '高风险'
      if (result > riskThresholds.medium) return '中风险'
      return '低风险'
    }
    
    // 获取风险描述
    const getRiskDescription = (result) => {
      if (result > riskThresholds.high) {
        return '患者心脏病风险较高，建议尽快就医检查。'
      } else if (result > riskThresholds.medium) {
        return '患者心脏病风险中等，建议定期检查心脏状况。'
      } else {
        return '患者心脏病风险较低，建议保持健康的生活方式。'
      }
    }
    
    // 解码算法名称
    const decodeAlgorithmName = (algorithmName) => {
      if (!algorithmName) return '未知算法';
      return algorithmName;
    }
    
    // 监听患者变化，获取诊断历史
    watch(() => state.form.patientId, (newVal) => {
      if (newVal) {
        getDiagnosisHistory(newVal)
      } else {
        state.historyList = []
      }
    })
    
    // 处理分页
    const handleSizeChange = (newSize) => {
      state.pageSize = newSize
      getDiagnosisHistory(state.form.patientId)
    }
    
    const handleCurrentChange = (newPage) => {
      state.currentPage = newPage
      getDiagnosisHistory(state.form.patientId)
    }
    
    // 初始化
    onMounted(() => {
      getPatients()
      getAlgorithms()
    })
    
    return {
      ...toRefs(state),
      handleDiagnosis,
      getResultColor,
      getRiskLevel,
      getRiskDescription,
      riskThresholds,
      decodeAlgorithmName,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.diagnosis-container {
  min-height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.result-container {
  margin-top: 20px;
  text-align: center;
  padding: 20px;
  border-top: 1px solid #EBEEF5;
}

.risk-text {
  margin-top: 15px;
  font-size: 18px;
  font-weight: bold;
}

.risk-description {
  margin-top: 10px;
  color: #606266;
}

.history-container {
  margin-top: 30px;
  border-top: 1px solid #EBEEF5;
  padding-top: 20px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}
</style> 