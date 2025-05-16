<template>
  <div class="statistics-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>数据统计</span>
          <el-button type="primary" @click="getStatisticsData">刷新数据</el-button>
        </div>
      </template>
      
      <el-skeleton :loading="loading" animated>
        <template #template>
          <div style="padding: 20px">
            <el-skeleton-item variant="p" style="width: 100%; height: 300px" />
            <el-skeleton-item variant="p" style="width: 100%; height: 300px; margin-top: 20px" />
          </div>
        </template>
        
        <template #default>
          <div v-if="statistics">
            <!-- 统计概要卡片 -->
            <el-row :gutter="20" class="summary-cards">
              <el-col :span="8">
                <el-card shadow="hover" class="summary-card">
                  <template #header>
                    <div class="card-header">
                      <span>诊断总数</span>
                    </div>
                  </template>
                  <div class="summary-card-value">{{ statistics.diagnosisCount || 0 }}</div>
                </el-card>
              </el-col>
              
              <el-col :span="8">
                <el-card shadow="hover" class="summary-card">
                  <template #header>
                    <div class="card-header">
                      <span>算法数量</span>
                    </div>
                  </template>
                  <div class="summary-card-value">{{ getAlgorithmCount() }}</div>
                </el-card>
              </el-col>
              
              <el-col :span="8">
                <el-card shadow="hover" class="summary-card">
                  <template #header>
                    <div class="card-header">
                      <span>高风险比例</span>
                    </div>
                  </template>
                  <div class="summary-card-value">{{ getHighRiskPercentage() }}%</div>
                </el-card>
              </el-col>
            </el-row>
            
            <!-- 图表展示 -->
            <el-row :gutter="20" class="charts-row">
              <el-col :span="12">
                <el-card shadow="hover" class="chart-card">
                  <template #header>
                    <div class="card-header">
                      <span>诊断算法分布</span>
                    </div>
                  </template>
                  <div v-show="chartsReady" ref="algorithmChartRef" class="chart"></div>
                </el-card>
              </el-col>
              <el-col :span="12">
                <el-card shadow="hover" class="chart-card">
                  <template #header>
                    <div class="card-header">
                      <span>风险等级分布</span>
                    </div>
                  </template>
                  <div v-show="chartsReady" ref="riskChartRef" class="chart"></div>
                </el-card>
              </el-col>
            </el-row>
          </div>
          <div v-else class="no-data">暂无统计数据</div>
        </template>
      </el-skeleton>
    </el-card>
  </div>
</template>

<script>
import { ref, reactive, onMounted, toRefs, onBeforeUnmount, nextTick, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { getStatistics } from '@/api/diagnosis'
import { getAllPatients } from '@/api/patient'
import * as echarts from 'echarts'

export default {
  name: 'Statistics',
  setup() {
    const algorithmChartRef = ref(null)
    const riskChartRef = ref(null)
    let algorithmChart = null
    let riskChart = null
    const chartsReady = ref(false)
    
    // 风险级别颜色配置
    const riskLevelColors = {
      high: '#F56C6C',  // 高风险-红色
      medium: '#E6A23C', // 中风险-橙色
      low: '#67C23A'     // 低风险-绿色
    }
    
    // 风险阈值配置
    const riskThresholds = {
      high: 0.7,  // 高风险阈值 
      medium: 0.3 // 中风险阈值
    }
    
    const state = reactive({
      loading: false,
      statistics: null,
      patients: [],
      riskDistribution: {
        high: 0,
        medium: 0,
        low: 0
      }
    })
    
    // 获取算法数量
    const getAlgorithmCount = () => {
      if (!state.statistics || !state.statistics.algorithmDistribution) return 0
      return state.statistics.algorithmDistribution.length
    }
    
    // 获取高风险百分比
    const getHighRiskPercentage = () => {
      if (!state.statistics || !state.statistics.diagnosisList || state.statistics.diagnosisList.length === 0) {
        return 0
      }
      
      const diagnosisList = state.statistics.diagnosisList
      let highRiskCount = 0
      
      diagnosisList.forEach(item => {
        if (item && item.result !== undefined && item.result !== null) {
          const risk = parseFloat(item.result)
          if (!isNaN(risk) && risk > riskThresholds.high) {
            highRiskCount++
          }
        }
      })
      
      return Math.round((highRiskCount / diagnosisList.length) * 100)
    }
    
    // 获取统计数据
    const getStatisticsData = async () => {
      state.loading = true
      chartsReady.value = false // 先隐藏图表
      
      try {
        const res = await getStatistics()
        state.statistics = res.data
        
        // 获取患者数据
        const patientRes = await getAllPatients()
        state.patients = patientRes.data
        
        // 计算风险分布
        calculateRiskDistribution()
        
        // 在DOM更新后渲染图表，使用延迟确保DOM完全就绪
        nextTick(() => {
          // 延迟300ms渲染图表，避免ResizeObserver错误
          setTimeout(() => {
            chartsReady.value = true // 显示图表容器
            nextTick(() => {
              renderCharts() // 渲染图表
            })
          }, 300)
        })
      } catch (error) {
        console.error('获取统计数据失败', error)
        ElMessage.error('获取统计数据失败')
      } finally {
        state.loading = false
      }
    }
    
    // 计算风险分布
    const calculateRiskDistribution = () => {
      state.riskDistribution = {
        high: 0,
        medium: 0,
        low: 0
      }
      
      if (state.statistics && state.statistics.diagnosisList) {
        const diagnosisList = state.statistics.diagnosisList
        
        diagnosisList.forEach(item => {
          if (item && item.result !== undefined && item.result !== null) {
            const risk = parseFloat(item.result)
            if (!isNaN(risk)) {
              if (risk > riskThresholds.high) {
                state.riskDistribution.high++
              } else if (risk > riskThresholds.medium) {
                state.riskDistribution.medium++
              } else {
                state.riskDistribution.low++
              }
            }
          }
        })
      }
    }
    
    // 渲染图表
    const renderCharts = () => {
      if (!state.statistics) return
      
      console.log('正在渲染图表...')
      
      // 算法分布图表
      renderAlgorithmChart()
      
      // 风险等级分布图表
      renderRiskLevelChart()
    }
    
    // 渲染算法分布图表
    const renderAlgorithmChart = () => {
      if (!algorithmChartRef.value) {
        console.error('算法图表DOM引用不存在')
        return
      }
      
      try {
        // 销毁旧图表
        if (algorithmChart) {
          algorithmChart.dispose()
        }
        
        // 初始化图表
        algorithmChart = echarts.init(algorithmChartRef.value)
        
        // 处理数据
        const algorithmData = state.statistics.algorithmDistribution || []
        console.log('算法分布数据(原始):', JSON.stringify(algorithmData))
        
        let seriesData = []
        if (Array.isArray(algorithmData) && algorithmData.length > 0) {
          seriesData = algorithmData.map(item => {
            console.log('算法项目:', JSON.stringify(item))
            // 确保我们有正确的属性名称
            const name = item.algorithm !== undefined ? item.algorithm : 
                          (item.name !== undefined ? item.name : '未知算法')
            const value = item.count !== undefined ? item.count : 
                          (item.value !== undefined ? item.value : 0)
            
            return {
              name: name || '未知算法',
              value: value || 0
            }
          })
        }
        
        console.log('处理后的图表数据:', JSON.stringify(seriesData))
        
        // 如果没有数据，添加默认数据
        if (seriesData.length === 0) {
          seriesData = [{ name: '暂无数据', value: 0 }]
        }
        
        // 设置图表配置
        const option = {
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
          },
          legend: {
            orient: 'vertical',
            left: 10,
            top: 'center',
            data: seriesData.map(item => item.name)
          },
          series: [
            {
              name: '算法分布',
              type: 'pie',
              radius: ['40%', '70%'],
              center: ['60%', '50%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2
              },
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '18',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: seriesData
            }
          ]
        }
        
        // 应用配置
        algorithmChart.setOption(option)
        console.log('算法分布图表渲染完成')
      } catch (error) {
        console.error('渲染算法分布图表失败:', error)
      }
    }
    
    // 渲染风险等级分布图表
    const renderRiskLevelChart = () => {
      if (!riskChartRef.value) {
        console.error('风险图表DOM引用不存在')
        return
      }
      
      try {
        // 销毁旧图表
        if (riskChart) {
          riskChart.dispose()
        }
        
        // 初始化图表
        riskChart = echarts.init(riskChartRef.value)
        
        // 处理数据
        // 风险级别分类
        const riskData = [
          { name: '高风险', value: state.riskDistribution.high },
          { name: '中风险', value: state.riskDistribution.medium },
          { name: '低风险', value: state.riskDistribution.low }
        ]
        
        console.log('风险分布数据:', JSON.stringify(riskData))
        
        // 设置图表配置
        const option = {
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
          },
          legend: {
            orient: 'vertical',
            left: 10,
            top: 'center',
            data: riskData.map(item => item.name)
          },
          series: [
            {
              name: '风险等级',
              type: 'pie',
              radius: ['40%', '70%'],
              center: ['60%', '50%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2,
                color: function(params) {
                  const colorList = [riskLevelColors.high, riskLevelColors.medium, riskLevelColors.low]
                  return colorList[params.dataIndex]
                }
              },
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '18',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: riskData
            }
          ]
        }
        
        // 应用配置
        riskChart.setOption(option)
        console.log('风险分布图表渲染完成')
      } catch (error) {
        console.error('渲染风险分布图表失败:', error)
      }
    }
    
    // 监听窗口大小变化
    const handleResize = () => {
      if (algorithmChart) {
        algorithmChart.resize()
      }
      if (riskChart) {
        riskChart.resize()
      }
    }
    
    onMounted(() => {
      console.log('组件已挂载，正在获取统计数据...')
      getStatisticsData()
      window.addEventListener('resize', handleResize)
    })
    
    onBeforeUnmount(() => {
      window.removeEventListener('resize', handleResize)
      if (algorithmChart) {
        algorithmChart.dispose()
        algorithmChart = null
      }
      if (riskChart) {
        riskChart.dispose()
        riskChart = null
      }
    })
    
    return {
      algorithmChartRef,
      riskChartRef,
      ...toRefs(state),
      getStatisticsData,
      getAlgorithmCount,
      getHighRiskPercentage,
      chartsReady
    }
  }
}
</script>

<style scoped>
.statistics-container {
  min-height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.summary-cards {
  margin-bottom: 20px;
}

.summary-card {
  text-align: center;
  transition: all 0.3s;
}

.summary-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.summary-card-value {
  font-size: 28px;
  font-weight: bold;
  color: #409EFF;
  padding: 15px 0;
}

.charts-row {
  margin-bottom: 20px;
}

.chart-card {
  margin-bottom: 20px;
  transition: all 0.3s;
}

.chart-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.chart {
  height: 350px;
  width: 100%;
}

.no-data {
  text-align: center;
  color: #909399;
  padding: 50px 0;
  font-size: 16px;
}
</style> 