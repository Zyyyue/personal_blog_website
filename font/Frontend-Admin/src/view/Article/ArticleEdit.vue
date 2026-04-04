<template>
  <div class="article-edit">
    <el-card>
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="文章标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入文章标题" />
        </el-form-item>

        <el-form-item label="文章摘要" prop="summary">
          <el-input
            v-model="form.summary"
            type="textarea"
            :rows="3"
            placeholder="请输入文章摘要"
          />
        </el-form-item>

        <el-form-item label="文章分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择文章分类">
            <el-option
              v-for="c in categoryStore.list"
              :key="c.id"
              :label="c.name"
              :value="c.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="文章标签" prop="tagIds">
          <el-select
            v-model="form.tagIds"
            multiple
            placeholder="请选择文章标签"
          >
            <el-option
              v-for="t in tagStore.list"
              :key="t.id"
              :label="t.name"
              :value="t.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="封面图片" prop="coverImage">
          <el-upload
            class="cover-uploader"
            :action="uploadUrl"
            :headers="uploadHeaders"
            :show-file-list="false"
            :on-success="handleCoverSuccess"
            :before-upload="beforeCoverUpload"
          >
            <img v-if="form.coverImage" :src="form.coverImage" class="cover" />
            <el-icon v-else class="uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>

        <el-form-item label="Slug" prop="slug">
          <el-input v-model="form.slug" placeholder="自定义文章路径" />
        </el-form-item>

        <el-form-item label="字数统计" prop="wordCount">
          <el-input-number v-model="form.wordCount" :min="0" />
        </el-form-item>

        <el-form-item label="阅读时间" prop="readingTime">
          <el-input-number v-model="form.readingTime" :min="0" />
        </el-form-item>

        <el-form-item label="文章内容" prop="content">
          <MdEditor
            v-model="form.content"
            style="height: 500px"
            language="zh-CN"
            @on-upload-img="onUploadImg"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="submitLoading" @click="handleSubmit">
            保存
          </el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useArticleStore, useCategoryStore, useTagStore } from '@/stores'
import { MdEditor } from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const articleStore = useArticleStore()
const categoryStore = useCategoryStore()
const tagStore = useTagStore()

const formRef = ref(null)
const submitLoading = ref(false)

const form = reactive({
  id: null,
  title: '',
  summary: '',
  categoryId: null,
  tagIds: [],
  coverImage: '',
  slug: '',
  wordCount: 0,
  readingTime: 0,
  content: '',
  isPublished: 0
})

const rules = {
  title: [{ required: true, message: '请输入文章标题', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择文章分类', trigger: 'change' }]
}

const uploadUrl = '/api/admin/upload/upload'
const uploadHeaders = computed(() => ({
  Authorization: localStorage.getItem('admin_token')
}))

const handleCoverSuccess = (res) => {
  if (res.code === 200 || res.code === 1) {
    form.coverImage = res.data
    ElMessage.success('上传成功')
  }
}

const beforeCoverUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2
  if (!isImage) {
    ElMessage.error('只能上传图片文件')
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过 2MB')
  }
  return isImage && isLt2M
}

const onUploadImg = async (files, callback) => {
  const formData = new FormData()
  formData.append('file', files[0])
  try {
    const res = await fetch(uploadUrl, {
      method: 'POST',
      headers: {
        Authorization: localStorage.getItem('admin_token')
      },
      body: formData
    })
    const data = await res.json()
    callback([data.data])
  } catch (error) {
    ElMessage.error('图片上传失败')
  }
}

const loadDetail = async () => {
  const id = route.params.id
  if (id) {
    form.id = id
    await articleStore.fetchDetail(id)
    const detail = articleStore.current
    if (detail) {
      Object.assign(form, detail)
      if (detail.tagIds) {
        form.tagIds = detail.tagIds
      }
    }
  }
}

const handleSubmit = async () => {
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    submitLoading.value = true
    try {
      if (form.id) {
        await articleStore.update(form)
        ElMessage.success('更新成功')
      } else {
        await articleStore.create(form)
        ElMessage.success('创建成功')
      }
      router.push('/article')
    } catch (error) {
      console.error(error)
    } finally {
      submitLoading.value = false
    }
  })
}

const handleCancel = () => {
  router.push('/article')
}

onMounted(() => {
  categoryStore.fetchList()
  tagStore.fetchList()
  loadDetail()
})
</script>

<style scoped lang="scss">
.article-edit {
  padding: 20px;

  .cover-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: all 0.3s;

    &:hover {
      border-color: #409eff;
    }

    .cover {
      width: 178px;
      height: 178px;
      object-fit: cover;
      display: block;
    }

    .uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      display: flex;
      align-items: center;
      justify-content: center;
      text-align: center;
    }
  }
}
</style>
