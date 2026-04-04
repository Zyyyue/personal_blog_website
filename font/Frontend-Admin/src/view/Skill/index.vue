<template>
  <div class="skill-page">
    <el-card>
      <template #header>
        <div class="header">
          <span>技能列表</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增技能
          </el-button>
        </div>
      </template>

      <el-table :data="skillStore.list" v-loading="skillStore.loading">
        <el-table-column prop="name" label="技能名称" />
        <el-table-column prop="level" label="熟练度" width="150">
          <template #default="{ row }">
            <el-progress :percentage="row.level" :stroke-width="18" :text-inside="true" />
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="80" />
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
      width="500px"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="技能名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入技能名称" />
        </el-form-item>
        <el-form-item label="熟练度" prop="level">
          <el-slider v-model="formData.level" :min="0" :max="100" />
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
import { useSkillStore } from '@/stores'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const skillStore = useSkillStore()

const formRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('新增技能')
const isEdit = ref(false)

const formData = reactive({
  id: null,
  name: '',
  level: 50,
  sort: 0
})

const rules = {
  name: [{ required: true, message: '请输入技能名称', trigger: 'blur' }]
}

const load = () => {
  skillStore.fetchList()
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增技能'
  formData.id = null
  formData.name = ''
  formData.level = 50
  formData.sort = 0
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑技能'
  formData.id = row.id
  formData.name = row.name
  formData.level = row.level
  formData.sort = row.sort || 0
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    if (isEdit.value) {
      await skillStore.update(formData)
      ElMessage.success('更新成功')
    } else {
      await skillStore.add(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    load()
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确认删除技能「${row.name}」？`, '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await skillStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped lang="scss">
.skill-page {
  padding: 20px;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
