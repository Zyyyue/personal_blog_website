<template>
  <div class="music-page">
    <el-card>
      <template #header>
        <div class="header">
          <span>音乐列表</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增音乐
          </el-button>
        </div>
      </template>

      <el-table :data="musicStore.list" v-loading="musicStore.loading">
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column prop="artist" label="艺术家" min-width="150" />
        <el-table-column prop="musicUrl" label="音乐 URL" min-width="300" show-overflow-tooltip />
        <el-table-column prop="coverImage" label="封面" width="100" align="center">
          <template #default="{ row }">
            <el-image
              v-if="row.coverImage"
              :src="row.coverImage"
              :preview-src-list="[row.coverImage]"
              fit="cover"
              style="width: 50px; height: 50px; border-radius: 4px"
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
      width="700px"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入音乐标题" />
        </el-form-item>
        <el-form-item label="艺术家" prop="artist">
          <el-input v-model="formData.artist" placeholder="请输入艺术家名称" />
        </el-form-item>
        <el-form-item label="音乐 URL" prop="musicUrl">
          <el-input v-model="formData.musicUrl" placeholder="请输入音乐文件地址" />
        </el-form-item>
        <el-form-item label="封面地址" prop="coverImage">
          <el-input v-model="formData.coverImage" placeholder="请输入封面图片地址" />
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
import { useMusicStore } from '@/stores'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const musicStore = useMusicStore()

const formRef = ref(null)
const dialogVisible = ref(false)
const dialogTitle = ref('新增音乐')
const isEdit = ref(false)

const formData = reactive({
  id: null,
  title: '',
  artist: '',
  musicUrl: '',
  coverImage: '',
  sort: 0
})

const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  musicUrl: [{ required: true, message: '请输入音乐 URL', trigger: 'blur' }]
}

const load = () => {
  musicStore.fetchList()
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增音乐'
  formData.id = null
  formData.title = ''
  formData.artist = ''
  formData.musicUrl = ''
  formData.coverImage = ''
  formData.sort = 0
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑音乐'
  formData.id = row.id
  formData.title = row.title
  formData.artist = row.artist
  formData.musicUrl = row.musicUrl
  formData.coverImage = row.coverImage
  formData.sort = row.sort || 0
  dialogVisible.value = true
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    if (isEdit.value) {
      await musicStore.update(formData)
      ElMessage.success('更新成功')
    } else {
      await musicStore.add(formData)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    load()
  })
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm(`确认删除音乐「${row.title}」？`, '警告', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await musicStore.remove([row.id])
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>

<style scoped lang="scss">
.music-page {
  padding: 20px;

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
