<template>
  <div class="tag-page">
    <el-card>
      <template #header>
        <div class="header">
          <span>标签列表</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增标签
          </el-button>
        </div>
      </template>

      <el-table :data="tagStore.list" v-loading="tagStore.loading">
        <el-table-column prop="name" label="标签名称" />
        <el-table-column prop="slug" label="Slug" />
        <el-table-column prop="articleCount" label="文章数" width="80" />
        <el-table-column label="操作" width="200" fixed="right">
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
        <el-form-item label="标签名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入标签名称" />
        </el-form-item>
        <el-form-item label="Slug" prop="slug">
          <el-input v-model="formData.slug" placeholder="自定义路径" />
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
import { useTagStore } from '@/stores'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const tagStore = useTagStore()

const formRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('新增标签')
const isEdit = ref(false)

const formData = reactive({
  id: null,
  name: '',
  slug: ''
})

const rules = {
  name: [{ required: true, message: '请输入标签名称', trigger: 'blur' }]
}

const load = () => {
  tagStore.fetchList()
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增标签'
  formData.id = null
  formData.name = ''
  formData.slug = ''
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑标签'
  formData.id = row.id
  formData.name = row.name
  formData.slug = row.slug
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    if (isEdit.value) {
      await tagStore.update(formData)
      ElMessage.success('更新成功')
    } else {
      await tagStore.add(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    load()
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确认删除标签「${row.name}」？`, '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await tagStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped lang="scss">
.tag-page {
  padding: 20px;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
