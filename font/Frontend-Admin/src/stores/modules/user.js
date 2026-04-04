import { defineStore } from 'pinia'
import { login, logout, getAdminInfo } from '@/api/auth'
import { changePassword, changeNickname, changeEmail, sendCode, updateProfile } from '@/api/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('admin_token') || '',
    userInfo: null
  }),

  getters: {
    isLoggedIn: (state) => !!state.token,
    nickname: (state) => state.userInfo?.nickname || '管理员',
    username: (state) => state.userInfo?.username || ''
  },

  actions: {
    async login(params) {
      const res = await login(params)
      const { token, id } = res.data
      this.token = token
      localStorage.setItem('admin_token', token)
      // 获取用户信息
      await this.getUserInfo()
      return res
    },

    async getUserInfo() {
      const res = await getAdminInfo()
      this.userInfo = res.data
      return res
    },

    async changePassword(params) {
      return changePassword(params)
    },

    async changeNickname(params) {
      return changeNickname(params)
    },

    async changeEmail(params) {
      return changeEmail(params)
    },

    async sendCode(params) {
      return sendCode(params)
    },

    logout() {
      this.token = ''
      this.userInfo = null
      localStorage.removeItem('admin_token')
    },

    async logoutApi() {
      await logout()
      this.logout()
    },

    async updateProfile(data) {
      return updateProfile(data)
    },

    setNickname(nickname) {
      if (this.userInfo) {
        this.userInfo.nickname = nickname
      }
    }
  },

  persist: {
    key: 'user-store',
    paths: ['token']
  }
})
