<template>
  <div class="category-page">
    <el-card>
      <template #header>
        <div class="header">
          <span>分类列表</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增分类
          </el-button>
        </div>
      </template>

      <el-table :data="categoryStore.list" v-loading="categoryStore.loading">
        <el-table-column prop="name" label="分类名称" />
        <el-table-column prop="slug" label="Slug" />
        <el-table-column prop="sort" label="排序" width="80" />
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
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="Slug" prop="slug">
          <el-input v-model="formData.slug" placeholder="自定义路径" />
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
import { useCategoryStore } from '@/stores'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const categoryStore = useCategoryStore()

const formRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('新增分类')
const isEdit = ref(false)

const formData = reactive({
  id: null,
  name: '',
  slug: '',
  sort: 0
})

const rules = {
  name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
}

const load = () => {
  categoryStore.fetchList()
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增分类'
  formData.id = null
  formData.name = ''
  formData.slug = ''
  formData.sort = 0
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑分类'
  formData.id = row.id
  formData.name = row.name
  formData.slug = row.slug
  formData.sort = row.sort || 0
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    if (isEdit.value) {
      await categoryStore.update(formData)
      ElMessage.success('更新成功')
    } else {
      await categoryStore.add(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    load()
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确认删除分类「${row.name}」？`, '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await categoryStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped lang="scss">
.category-page {
  padding: 20px;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
