<template>
  <div class="patients-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>患者管理</span>
          <el-button type="primary" @click="handleAdd">添加患者</el-button>
        </div>
      </template>
      
      <el-table :data="patients" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="姓名" width="120"></el-table-column>
        <el-table-column prop="age" label="年龄" width="80"></el-table-column>
        <el-table-column label="性别" width="80">
          <template #default="scope">
            {{ scope.row.gender === 1 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="bloodPressure" label="血压"></el-table-column>
        <el-table-column prop="cholesterol" label="胆固醇"></el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页组件 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.pageNum"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[5, 10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    
    <!-- 患者信息编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '添加患者' : '编辑患者'"
      width="500px"
    >
      <el-form :model="patientForm" label-width="80px" ref="patientFormRef" :rules="rules">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="patientForm.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="patientForm.age" type="number"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="patientForm.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="血压" prop="bloodPressure">
          <el-input v-model="patientForm.bloodPressure"></el-input>
        </el-form-item>
        <el-form-item label="胆固醇" prop="cholesterol">
          <el-input v-model.number="patientForm.cholesterol" type="number"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive, onMounted, toRefs } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getPatientsByPage, addPatient, updatePatient, deletePatient } from '@/api/patient'

export default {
  name: 'Patients',
  setup() {
    const patientFormRef = ref(null)
    
    const state = reactive({
      patients: [],
      loading: false,
      dialogVisible: false,
      dialogType: 'add', // add-添加 edit-编辑
      patientForm: {
        id: null,
        name: '',
        age: null,
        gender: 1,
        bloodPressure: '',
        cholesterol: null
      },
      pagination: {
        pageNum: 1,
        pageSize: 10,
        total: 0
      },
      rules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
        gender: [{ required: true, message: '请选择性别', trigger: 'change' }]
      }
    })
    
    // 获取患者列表（分页）
    const getPatients = async () => {
      state.loading = true
      try {
        const res = await getPatientsByPage(state.pagination.pageNum, state.pagination.pageSize)
        if (res.code === 200 && res.data) {
          state.patients = res.data.records
          state.pagination.total = res.data.total
          state.pagination.pageNum = res.data.pageNum
          state.pagination.pageSize = res.data.pageSize
        } else {
          ElMessage.error(res.message || '获取患者列表失败')
        }
      } catch (error) {
        ElMessage.error('获取患者列表失败')
      } finally {
        state.loading = false
      }
    }
    
    // 每页条数变化
    const handleSizeChange = (val) => {
      state.pagination.pageSize = val
      getPatients()
    }
    
    // 页码变化
    const handleCurrentChange = (val) => {
      state.pagination.pageNum = val
      getPatients()
    }
    
    // 添加患者
    const handleAdd = () => {
      state.dialogType = 'add'
      state.patientForm = {
        id: null,
        name: '',
        age: null,
        gender: 1,
        bloodPressure: '',
        cholesterol: null
      }
      state.dialogVisible = true
    }
    
    // 编辑患者
    const handleEdit = (row) => {
      state.dialogType = 'edit'
      state.patientForm = { ...row }
      state.dialogVisible = true
    }
    
    // 删除患者
    const handleDelete = (row) => {
      ElMessageBox.confirm(
        `确定要删除患者 ${row.name} 吗?`,
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(async () => {
        try {
          await deletePatient(row.id)
          ElMessage.success('删除成功')
          getPatients()
        } catch (error) {
          ElMessage.error('删除失败')
        }
      }).catch(() => {})
    }
    
    // 提交表单
    const submitForm = () => {
      patientFormRef.value.validate(async (valid) => {
        if (valid) {
          try {
            if (state.dialogType === 'add') {
              await addPatient(state.patientForm)
              ElMessage.success('添加成功')
            } else {
              await updatePatient(state.patientForm.id, state.patientForm)
              ElMessage.success('更新成功')
            }
            state.dialogVisible = false
            getPatients()
          } catch (error) {
            ElMessage.error(state.dialogType === 'add' ? '添加失败' : '更新失败')
          }
        }
      })
    }
    
    onMounted(() => {
      getPatients()
    })
    
    return {
      patientFormRef,
      ...toRefs(state),
      handleAdd,
      handleEdit,
      handleDelete,
      submitForm,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.patients-container {
  min-height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style> 