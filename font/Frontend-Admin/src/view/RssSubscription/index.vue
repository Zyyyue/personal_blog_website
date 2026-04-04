<template>
  <div class="rss-page">
    <el-card>
      <template #header>
        <div class="header">
          <span>RSS 订阅列表</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增 RSS 订阅
          </el-button>
        </div>
      </template>

      <el-table :data="rssStore.list" v-loading="rssStore.loading">
        <el-table-column prop="name" label="名称" min-width="150" />
        <el-table-column prop="url" label="RSS 地址" min-width="400" show-overflow-tooltip />
        <el-table-column prop="isActive" label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-switch
              v-model="row.isActive"
              :active-value="1"
              :inactive-value="0"
              @change="handleToggleStatus(row)"
            />
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
      width="600px"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入订阅名称" />
        </el-form-item>
        <el-form-item label="RSS 地址" prop="url">
          <el-input v-model="formData.url" placeholder="请输入 RSS 订阅地址" />
        </el-form-item>
        <el-form-item label="状态" prop="isActive">
          <el-switch
            v-model="formData.isActive"
            :active-value="1"
            :inactive-value="0"
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
import { useRssSubscriptionStore } from '@/stores'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const rssStore = useRssSubscriptionStore()

const formRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('新增 RSS 订阅')
const isEdit = ref(false)

const formData = reactive({
  id: null,
  name: '',
  url: '',
  isActive: 1,
  sort: 0
})

const rules = {
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  url: [{ required: true, message: '请输入 RSS 地址', trigger: 'blur' }]
}

const load = () => {
  rssStore.fetchList()
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增 RSS 订阅'
  formData.id = null
  formData.name = ''
  formData.url = ''
  formData.isActive = 1
  formData.sort = 0
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑 RSS 订阅'
  formData.id = row.id
  formData.name = row.name
  formData.url = row.url
  formData.isActive = row.isActive
  formData.sort = row.sort || 0
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    if (isEdit.value) {
      await rssStore.update(formData)
      ElMessage.success('更新成功')
    } else {
      await rssStore.add(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    load()
  })
}

const handleToggleStatus = async (row) => {
  await rssStore.update({ id: row.id, isActive: row.isActive })
  ElMessage.success('状态更新成功')
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确认删除 RSS 订阅「${row.name}」？`, '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await rssStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped lang="scss">
.rss-page {
  padding: 20px;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
