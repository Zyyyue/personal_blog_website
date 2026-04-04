<template>
  <div class="friend-link-page">
    <el-card>
      <template #header>
        <div class="header">
          <span>友链列表</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增友链
          </el-button>
        </div>
      </template>

      <el-table :data="friendLinkStore.list" v-loading="friendLinkStore.loading">
        <el-table-column prop="name" label="网站名称" width="150" />
        <el-table-column prop="url" label="网站地址" min-width="200" show-overflow-tooltip />
        <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
        <el-table-column prop="avatar" label="头像" width="80">
          <template #default="{ row }">
            <el-image
              v-if="row.avatar"
              :src="row.avatar"
              :preview-src-list="[row.avatar]"
              style="width: 40px; height: 40px; border-radius: 50%"
              fit="cover"
            />
          </template>
        </el-table-column>
        <el-table-column label="状态" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="row.isVisible ? 'success' : 'info'">
              {{ row.isVisible ? '显示' : '隐藏' }}
            </el-tag>
          </template>
        </el-table-column>
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
        <el-form-item label="网站名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入网站名称" />
        </el-form-item>
        <el-form-item label="网站地址" prop="url">
          <el-input v-model="formData.url" placeholder="请输入网站地址" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="formData.description"
            type="textarea"
            :rows="3"
            placeholder="请输入网站描述"
          />
        </el-form-item>
        <el-form-item label="头像地址" prop="avatar">
          <el-input v-model="formData.avatar" placeholder="请输入头像地址" />
        </el-form-item>
        <el-form-item label="是否显示" prop="isVisible">
          <el-switch v-model="formData.isVisible" :active-value="1" :inactive-value="0" />
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
import { useFriendLinkStore } from '@/stores'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const friendLinkStore = useFriendLinkStore()

const formRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('新增友链')
const isEdit = ref(false)

const formData = reactive({
  id: null,
  name: '',
  url: '',
  description: '',
  avatar: '',
  isVisible: 1
})

const rules = {
  name: [{ required: true, message: '请输入网站名称', trigger: 'blur' }],
  url: [
    { required: true, message: '请输入网站地址', trigger: 'blur' },
    { type: 'url', message: '请输入正确的 URL 地址', trigger: 'blur' }
  ]
}

const load = () => {
  friendLinkStore.fetchList()
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增友链'
  formData.id = null
  formData.name = ''
  formData.url = ''
  formData.description = ''
  formData.avatar = ''
  formData.isVisible = 1
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑友链'
  formData.id = row.id
  formData.name = row.name
  formData.url = row.url
  formData.description = row.description
  formData.avatar = row.avatar
  formData.isVisible = row.isVisible
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    if (isEdit.value) {
      await friendLinkStore.update(formData)
      ElMessage.success('更新成功')
    } else {
      await friendLinkStore.add(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    load()
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确认删除友链「${row.name}」？`, '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await friendLinkStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped lang="scss">
.friend-link-page {
  padding: 20px;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
