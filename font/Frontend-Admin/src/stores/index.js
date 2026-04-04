import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

export * from './modules/user'
export * from './modules/article'
export * from './modules/category'
export * from './modules/tag'
export * from './modules/comment'
export * from './modules/message'
export * from './modules/friendLink'
export * from './modules/skill'
export * from './modules/experience'
export * from './modules/personalInfo'
export * from './modules/settings'
export * from './modules/socialMedia'
export * from './modules/music'
export * from './modules/rssSubscription'
export * from './modules/visitor'
export * from './modules/viewRecord'
export * from './modules/operationLog'
export * from './modules/report'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

export default pinia
