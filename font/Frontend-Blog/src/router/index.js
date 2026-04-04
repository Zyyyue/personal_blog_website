import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('@/view/Layout/index.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import('@/view/Home/index.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'blog',
        name: 'Blog',
        component: () => import('@/view/Blog/index.vue'),
        meta: { title: '博客' }
      },
      {
        path: 'cv',
        name: 'Cv',
        component: () => import('@/view/Cv/index.vue'),
        meta: { title: '简历' }
      },
      {
        path: 'article/:id',
        name: 'Article',
        component: () => import('@/view/Article/index.vue'),
        meta: { title: '文章详情' }
      },
      {
        path: 'about',
        name: 'About',
        component: () => import('@/view/About/index.vue'),
        meta: { title: '关于' }
      },
      {
        path: 'message',
        name: 'Message',
        component: () => import('@/view/Message/index.vue'),
        meta: { title: '留言板' }
      },
      {
        path: 'archive',
        name: 'Archive',
        component: () => import('@/view/Archive/index.vue'),
        meta: { title: '归档' }
      },
      {
        path: 'category',
        name: 'Category',
        component: () => import('@/view/Category/index.vue'),
        meta: { title: '分类' }
      },
      {
        path: 'tag/:id',
        name: 'Tag',
        component: () => import('@/view/Tag/index.vue'),
        meta: { title: '标签' }
      },
      {
        path: 'links',
        name: 'Links',
        component: () => import('@/view/Links/index.vue'),
        meta: { title: '友情链接' }
      }
    ]
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('@/view/Admin/index.vue'),
    meta: { title: '管理后台' }
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
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = `${to.meta.title} - 个人博客`
  }
  next()
})

export default router
