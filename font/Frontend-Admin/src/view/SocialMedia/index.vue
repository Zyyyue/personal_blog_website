<template>
  <div class="social-media-page">
    <el-card>
      <template #header>
        <div class="header">
          <span>社交媒体列表</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增社交媒体
          </el-button>
        </div>
      </template>

      <el-table :data="socialMediaStore.list" v-loading="socialMediaStore.loading">
        <el-table-column prop="name" label="名称" min-width="150" />
        <el-table-column prop="icon" label="图标" width="100" align="center">
          <template #default="{ row }">
            <el-icon :size="24"><component :is="row.icon" /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="url" label="链接" min-width="300" show-overflow-tooltip />
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
      width="600px"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="formData.icon" placeholder="请输入图标类名 (如：Github)" />
        </el-form-item>
        <el-form-item label="链接" prop="url">
          <el-input v-model="formData.url" placeholder="请输入链接地址" />
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
import { useSocialMediaStore } from '@/stores'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const socialMediaStore = useSocialMediaStore()

const formRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('新增社交媒体')
const isEdit = ref(false)

const formData = reactive({
  id: null,
  name: '',
  icon: '',
  url: '',
  sort: 0
})

const rules = {
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  url: [{ required: true, message: '请输入链接', trigger: 'blur' }]
}

const load = () => {
  socialMediaStore.fetchList()
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增社交媒体'
  formData.id = null
  formData.name = ''
  formData.icon = ''
  formData.url = ''
  formData.sort = 0
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑社交媒体'
  formData.id = row.id
  formData.name = row.name
  formData.icon = row.icon
  formData.url = row.url
  formData.sort = row.sort || 0
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    if (isEdit.value) {
      await socialMediaStore.update(formData)
      ElMessage.success('更新成功')
    } else {
      await socialMediaStore.add(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    load()
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确认删除社交媒体「${row.name}」？`, '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await socialMediaStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped lang="scss">
.social-media-page {
  padding: 20px;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
