import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/view/Login/index.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/view/layout/index.vue'),
    redirect: '/dashboard',
    meta: { title: '管理后台' },
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/view/Dashboard/index.vue'),
        meta: { title: '数据概览', icon: 'icon-dashboard' }
      },
      {
        path: 'article',
        name: 'Article',
        component: () => import('@/view/Article/index.vue'),
        meta: { title: '文章管理', icon: 'icon-article' }
      },
      {
        path: 'article/edit/:id?',
        name: 'ArticleEdit',
        component: () => import('@/view/Article/ArticleEdit.vue'),
        meta: { title: '编辑文章', hidden: true }
      },
      {
        path: 'category',
        name: 'Category',
        component: () => import('@/view/Category/index.vue'),
        meta: { title: '分类管理', icon: 'icon-category' }
      },
      {
        path: 'tag',
        name: 'Tag',
        component: () => import('@/view/Tag/index.vue'),
        meta: { title: '标签管理', icon: 'icon-tag' }
      },
      {
        path: 'comment',
        name: 'Comment',
        component: () => import('@/view/Comment/index.vue'),
        meta: { title: '评论管理', icon: 'icon-comment' }
      },
      {
        path: 'message',
        name: 'Message',
        component: () => import('@/view/Message/index.vue'),
        meta: { title: '留言管理', icon: 'icon-message' }
      },
      {
        path: 'friendLink',
        name: 'FriendLink',
        component: () => import('@/view/FriendLink/index.vue'),
        meta: { title: '友链管理', icon: 'icon-friend' }
      },
      {
        path: 'skill',
        name: 'Skill',
        component: () => import('@/view/Skill/index.vue'),
        meta: { title: '技能管理', icon: 'icon-skill' }
      },
      {
        path: 'experience',
        name: 'Experience',
        component: () => import('@/view/Experience/index.vue'),
        meta: { title: '经历管理', icon: 'icon-experience' }
      },
      {
        path: 'personalInfo',
        name: 'PersonalInfo',
        component: () => import('@/view/PersonalInfo/index.vue'),
        meta: { title: '个人信息', icon: 'icon-user' }
      },
      {
        path: 'systemConfig',
        name: 'SystemConfig',
        component: () => import('@/view/SystemConfig/index.vue'),
        meta: { title: '系统配置', icon: 'icon-config' }
      },
      {
        path: 'socialMedia',
        name: 'SocialMedia',
        component: () => import('@/view/SocialMedia/index.vue'),
        meta: { title: '社交媒体', icon: 'icon-social' }
      },
      {
        path: 'music',
        name: 'Music',
        component: () => import('@/view/Music/index.vue'),
        meta: { title: '音乐管理', icon: 'icon-music' }
      },
      {
        path: 'rssSubscription',
        name: 'RssSubscription',
        component: () => import('@/view/RssSubscription/index.vue'),
        meta: { title: 'RSS 订阅', icon: 'icon-rss' }
      },
      {
        path: 'visitor',
        name: 'Visitor',
        component: () => import('@/view/Visitor/index.vue'),
        meta: { title: '访客管理', icon: 'icon-visitor' }
      },
      {
        path: 'viewRecord',
        name: 'ViewRecord',
        component: () => import('@/view/ViewRecord/index.vue'),
        meta: { title: '浏览记录', icon: 'icon-view' }
      },
      {
        path: 'operationLog',
        name: 'OperationLog',
        component: () => import('@/view/OperationLog/index.vue'),
        meta: { title: '操作日志', icon: 'icon-log' }
      },
      {
        path: 'report',
        name: 'Report',
        component: () => import('@/view/Report/index.vue'),
        meta: { title: '数据统计', icon: 'icon-report' }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/view/Profile/index.vue'),
        meta: { title: '个人设置', icon: 'icon-settings', hidden: true }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/view/NotFound/index.vue'),
    meta: { title: '404' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('admin_token')

  if (to.meta.title) {
    document.title = `${to.meta.title} - 个人博客管理后台`
  }

  if (to.path === '/login') {
    next()
  } else if (!token) {
    next('/login')
  } else {
    next()
  }
})

export default router
