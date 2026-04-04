<script setup>
import { ref, computed, onMounted } from 'vue'
import SidebarCard from '@/components/SidebarCard.vue'

const personalInfo = ref({
  name: 'FeiTwnd',
  position: 'Java 开发工程师',
  email: '822824739@qq.com',
  phone: '138****8888',
  city: '江西 赣州',
  introduction: '热爱编程，专注于 Java 后端开发，对 Spring Boot、微服务架构有深入研究。',
  avatar: 'https://picsum.photos/200/200?random=1',
  github: 'https://github.com/FeiTwnd',
  blog: 'https://feitwd.cn'
})

const skills = ref([
  { name: 'Java', level: 85 },
  { name: 'Spring Boot', level: 80 },
  { name: 'MySQL', level: 75 },
  { name: 'Redis', level: 70 },
  { name: 'Vue', level: 65 },
  { name: 'Docker', level: 60 }
])

const experiences = ref([
  {
    id: 1,
    type: 2,
    typeName: '工作经历',
    title: 'Java 开发工程师',
    company: '某某科技有限公司',
    description: '负责公司核心产品的后端开发，参与系统架构设计和技术选型。',
    startDate: '2023-07-01',
    endDate: '至今',
    sort: 1
  },
  {
    id: 2,
    type: 1,
    typeName: '教育经历',
    title: '计算机科学与技术',
    company: '江西理工大学',
    description: '本科专业，主修数据结构、算法、操作系统、计算机网络等课程。',
    startDate: '2020-09-01',
    endDate: '2024-06-30',
    sort: 2
  },
  {
    id: 3,
    type: 3,
    typeName: '项目经历',
    title: '个人博客系统',
    company: '独立开发',
    description: '基于 Spring Boot + Vue3 开发的全栈博客系统，包含前台展示和后台管理功能。',
    startDate: '2023-01-01',
    endDate: '2023-06-30',
    sort: 3
  }
])

const groupedExperiences = computed(() => {
  const groups = {
    work: [],
    education: [],
    project: []
  }
  experiences.value.forEach(exp => {
    if (exp.type === 1) groups.education.push(exp)
    else if (exp.type === 2) groups.work.push(exp)
    else if (exp.type === 3) groups.project.push(exp)
  })
  return groups
})

onMounted(() => {
  // TODO: 调用 API 获取个人信息、技能、经历
})
</script>

<template>
  <div class="cv-page">
    <div class="cv-content">
      <!-- 左侧：简历主体 -->
      <div class="cv-main">
        <!-- 个人信息卡片 -->
        <div class="cv-card profile-card">
          <div class="profile-header">
            <div class="avatar-wrap">
              <img :src="personalInfo.avatar" alt="头像" class="avatar" />
            </div>
            <div class="profile-info">
              <h1 class="name">{{ personalInfo.name }}</h1>
              <p class="position">{{ personalInfo.position }}</p>
              <div class="contact-list">
                <div class="contact-item">
                  <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z" />
                    <polyline points="22,6 12,13 2,6" />
                  </svg>
                  <span>{{ personalInfo.email }}</span>
                </div>
                <div class="contact-item">
                  <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M22 16.92v3a2 2 0 01-2.18 2 19.79 19.79 0 01-8.63-3.07 19.5 19.5 0 01-6-6 19.79 19.79 0 01-3.07-8.67A2 2 0 014.11 2h3a2 2 0 012 1.72 12.84 12.84 0 00.7 2.81 2 2 0 01-.45 2.11L8.09 9.91a16 16 0 006 6l1.27-1.27a2 2 0 012.11-.45 12.84 12.84 0 002.81.7A2 2 0 0122 16.92z" />
                  </svg>
                  <span>{{ personalInfo.phone }}</span>
                </div>
                <div class="contact-item">
                  <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0118 0z" />
                    <circle cx="12" cy="10" r="3" />
                  </svg>
                  <span>{{ personalInfo.city }}</span>
                </div>
              </div>
              <div class="social-links">
                <a v-if="personalInfo.github" :href="personalInfo.github" target="_blank" class="social-link">
                  <svg viewBox="0 0 24 24" width="18" height="18" fill="currentColor">
                    <path d="M12 .3a12 12 0 00-3.8 23.38c.6.12.83-.26.83-.57L9 21.07c-3.34.72-4.04-1.61-4.04-1.61-.55-1.39-1.34-1.76-1.34-1.76-1.08-.74.08-.73.08-.73 1.2.08 1.84 1.24 1.84 1.24 1.07 1.83 2.81 1.3 3.5 1 .1-.78.42-1.3.76-1.6-2.67-.3-5.47-1.33-5.47-5.93 0-1.31.47-2.38 1.24-3.22-.13-.3-.54-1.52.12-3.18 0 0 1-.33 3.3 1.23a11.5 11.5 0 016.02 0c2.28-1.56 3.29-1.23 3.29-1.23.66 1.66.25 2.88.12 3.18a4.65 4.65 0 011.23 3.22c0 4.61-2.81 5.63-5.48 5.93.43.37.81 1.1.81 2.22l-.01 3.29c0 .31.22.69.83.57A12 12 0 0012 .3" />
                  </svg>
                  GitHub
                </a>
                <a v-if="personalInfo.blog" :href="personalInfo.blog" target="_blank" class="social-link">
                  <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M18 13v6a2 2 0 01-2 2H5a2 2 0 01-2-2V8a2 2 0 012-2h6" />
                    <polyline points="15 3 21 3 21 9" />
                    <line x1="10" y1="14" x2="21" y2="3" />
                  </svg>
                  博客
                </a>
              </div>
            </div>
          </div>
          <div class="profile-intro">
            <p>{{ personalInfo.introduction }}</p>
          </div>
        </div>

        <!-- 工作经历 -->
        <div v-if="groupedExperiences.work.length" class="cv-card">
          <h2 class="cv-card-title">
            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="2" y="7" width="20" height="14" rx="2" ry="2" />
              <path d="M16 21V5a2 2 0 00-2-2h-4a2 2 0 00-2 2v16" />
            </svg>
            工作经历
          </h2>
          <div class="experience-list">
            <div v-for="exp in groupedExperiences.work" :key="exp.id" class="experience-item">
              <div class="experience-header">
                <h3 class="experience-title">{{ exp.title }}</h3>
                <span class="experience-period">{{ exp.startDate }} - {{ exp.endDate }}</span>
              </div>
              <div class="experience-company">{{ exp.company }}</div>
              <div class="experience-desc" v-html="exp.description" />
            </div>
          </div>
        </div>

        <!-- 教育经历 -->
        <div v-if="groupedExperiences.education.length" class="cv-card">
          <h2 class="cv-card-title">
            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M22 10v6M2 10l10-5 10 5-10 5z" />
              <path d="M6 12v5c3 3 9 3 12 0v-5" />
            </svg>
            教育经历
          </h2>
          <div class="experience-list">
            <div v-for="exp in groupedExperiences.education" :key="exp.id" class="experience-item">
              <div class="experience-header">
                <h3 class="experience-title">{{ exp.title }}</h3>
                <span class="experience-period">{{ exp.startDate }} - {{ exp.endDate }}</span>
              </div>
              <div class="experience-company">{{ exp.company }}</div>
              <div class="experience-desc" v-html="exp.description" />
            </div>
          </div>
        </div>

        <!-- 项目经历 -->
        <div v-if="groupedExperiences.project.length" class="cv-card">
          <h2 class="cv-card-title">
            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M12 2L2 7l10 5 10-5-10-5z" />
              <path d="M2 17l10 5 10-5" />
              <path d="M2 12l10 5 10-5" />
            </svg>
            项目经历
          </h2>
          <div class="experience-list">
            <div v-for="exp in groupedExperiences.project" :key="exp.id" class="experience-item">
              <div class="experience-header">
                <h3 class="experience-title">{{ exp.title }}</h3>
                <span class="experience-period">{{ exp.startDate }} - {{ exp.endDate }}</span>
              </div>
              <div class="experience-company">{{ exp.company }}</div>
              <div class="experience-desc" v-html="exp.description" />
            </div>
          </div>
        </div>

        <!-- 技能列表 -->
        <div class="cv-card">
          <h2 class="cv-card-title">
            <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
              <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2" />
            </svg>
            专业技能
          </h2>
          <div class="skills-list">
            <div v-for="skill in skills" :key="skill.name" class="skill-item">
              <div class="skill-info">
                <span class="skill-name">{{ skill.name }}</span>
                <span class="skill-level">{{ skill.level }}%</span>
              </div>
              <div class="skill-bar">
                <div class="skill-progress" :style="{ width: skill.level + '%' }" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧：侧边栏 -->
      <SidebarCard />
    </div>
  </div>
</template>

<style scoped lang="scss">
.cv-page {
  width: 100%;
}

.cv-content {
  display: flex;
  gap: 24px;
  align-items: flex-start;
}

.cv-main {
  flex: 1;
  min-width: 0;
}

.cv-card {
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
  border: 1px solid #ebeef5;
  padding: 24px;
  margin-bottom: 20px;
}

.cv-card-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid #ebeef5;
}

/* 个人信息卡片 */
.profile-card {
  .profile-header {
    display: flex;
    gap: 24px;
    margin-bottom: 20px;
  }

  .avatar-wrap {
    flex-shrink: 0;

    .avatar {
      width: 120px;
      height: 120px;
      border-radius: 12px;
      object-fit: cover;
      border: 3px solid #409eff;
    }
  }

  .profile-info {
    flex: 1;
    padding-top: 8px;

    .name {
      font-size: 28px;
      font-weight: 700;
      color: #303133;
      margin: 0 0 8px;
    }

    .position {
      font-size: 16px;
      color: #606266;
      margin: 0 0 16px;
    }
  }

  .contact-list {
    display: flex;
    flex-wrap: wrap;
    gap: 16px;
    margin-bottom: 16px;

    .contact-item {
      display: flex;
      align-items: center;
      gap: 6px;
      font-size: 14px;
      color: #606266;

      svg {
        color: #909399;
      }
    }
  }

  .social-links {
    display: flex;
    gap: 16px;

    .social-link {
      display: flex;
      align-items: center;
      gap: 6px;
      color: #606266;
      text-decoration: none;
      font-size: 14px;
      transition: color 0.2s;

      &:hover {
        color: #409eff;
      }
    }
  }

  .profile-intro {
    padding-top: 16px;
    border-top: 1px solid #ebeef5;

    p {
      font-size: 14px;
      color: #606266;
      line-height: 1.8;
      margin: 0;
    }
  }
}

/* 经历列表 */
.experience-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.experience-item {
  padding-bottom: 20px;
  border-bottom: 1px solid #f5f7fa;

  &:last-child {
    padding-bottom: 0;
    border-bottom: none;
  }

  .experience-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
  }

  .experience-title {
    font-size: 16px;
    font-weight: 600;
    color: #303133;
    margin: 0;
  }

  .experience-period {
    font-size: 13px;
    color: #909399;
    white-space: nowrap;
  }

  .experience-company {
    font-size: 14px;
    color: #606266;
    margin-bottom: 10px;

    &::before {
      content: '📍 ';
    }
  }

  .experience-desc {
    font-size: 14px;
    color: #606266;
    line-height: 1.7;

    :deep(p) {
      margin: 6px 0;
    }
  }
}

/* 技能列表 */
.skills-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.skill-item {
  .skill-info {
    display: flex;
    justify-content: space-between;
    margin-bottom: 8px;
  }

  .skill-name {
    font-size: 14px;
    color: #303133;
    font-weight: 500;
  }

  .skill-level {
    font-size: 13px;
    color: #909399;
  }

  .skill-bar {
    height: 8px;
    background: #ebeef5;
    border-radius: 4px;
    overflow: hidden;

    .skill-progress {
      height: 100%;
      background: linear-gradient(90deg, #409eff, #67c23a);
      border-radius: 4px;
      transition: width 0.5s ease;
    }
  }
}

@media (max-width: 960px) {
  .cv-content {
    flex-direction: column;
  }

  .profile-header {
    flex-direction: column;
    align-items: center;
    text-align: center;

    .avatar-wrap {
      margin-bottom: 16px;
    }

    .contact-list {
      justify-content: center;
    }

    .social-links {
      justify-content: center;
    }
  }
}

@media (max-width: 600px) {
  .cv-card {
    padding: 16px;
  }

  .profile-info .name {
    font-size: 24px;
  }

  .experience-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 4px;
  }

  .experience-period {
    font-size: 12px;
  }
}
</style>
