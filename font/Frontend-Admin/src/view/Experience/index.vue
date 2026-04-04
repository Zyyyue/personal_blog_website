<template>
  <div class="experience-page">
    <el-card>
      <template #header>
        <div class="header">
          <span>经历列表</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增经历
          </el-button>
        </div>
      </template>

      <el-table :data="experienceStore.list" v-loading="experienceStore.loading">
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.type === 1">教育经历</el-tag>
            <el-tag v-else-if="row.type === 2" type="success">工作经历</el-tag>
            <el-tag v-else-if="row.type === 3" type="warning">项目经历</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" min-width="150" />
        <el-table-column prop="company" label="公司/学校" min-width="150" />
        <el-table-column prop="startDate" label="开始日期" width="120" />
        <el-table-column prop="endDate" label="结束日期" width="120" />
        <el-table-column label="操作" width="150" align="center" fixed="right">
          <template #default="{ row }">
            <el-button link size="small" @click="handleEdit(row)">
              编辑
            </el-button>
            <el-divider direction="vertical" />
            <el-button link size="small" type="danger" @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="700px"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="类型" prop="type">
          <el-select v-model="formData.type" placeholder="请选择类型">
            <el-option label="教育经历" :value="1" />
            <el-option label="工作经历" :value="2" />
            <el-option label="项目经历" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="公司/学校" prop="company">
          <el-input v-model="formData.company" placeholder="请输入公司/学校名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="formData.description"
            type="textarea"
            :rows="4"
            placeholder="请输入描述"
          />
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker
            v-model="formData.startDate"
            type="month"
            placeholder="选择开始日期"
            format="YYYY-MM"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker
            v-model="formData.endDate"
            type="month"
            placeholder="选择结束日期"
            format="YYYY-MM"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="formData.sort" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useExperienceStore } from '@/stores'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const experienceStore = useExperienceStore()

const formRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('新增经历')
const isEdit = ref(false)

const formData = reactive({
  id: null,
  type: 1,
  title: '',
  company: '',
  description: '',
  startDate: '',
  endDate: '',
  sort: 0
})

const rules = {
  type: [{ required: true, message: '请选择类型', trigger: 'change' }],
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  company: [{ required: true, message: '请输入公司/学校', trigger: 'blur' }],
  startDate: [{ required: true, message: '请选择开始日期', trigger: 'change' }]
}

const load = () => {
  experienceStore.fetchList([1, 2, 3])
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增经历'
  formData.id = null
  formData.type = 1
  formData.title = ''
  formData.company = ''
  formData.description = ''
  formData.startDate = ''
  formData.endDate = ''
  formData.sort = 0
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑经历'
  formData.id = row.id
  formData.type = row.type
  formData.title = row.title
  formData.company = row.company
  formData.description = row.description
  formData.startDate = row.startDate
  formData.endDate = row.endDate
  formData.sort = row.sort || 0
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    if (isEdit.value) {
      await experienceStore.update(formData)
      ElMessage.success('更新成功')
    } else {
      await experienceStore.add(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    load()
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确认删除经历「${row.title}」？`, '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await experienceStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped lang="scss">
.experience-page {
  padding: 20px;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
