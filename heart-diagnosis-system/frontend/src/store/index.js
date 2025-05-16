import { createStore } from 'vuex'

export default createStore({
  state: {
    user: JSON.parse(localStorage.getItem('user')) || null,
    token: localStorage.getItem('token') || '',
    patients: [],
    activePatient: null,
    algorithms: []
  },
  getters: {
    isLoggedIn: state => !!state.token,
    currentUser: state => state.user,
    enabledAlgorithms: state => state.algorithms.filter(a => a.status === 1)
  },
  mutations: {
    setToken(state, token) {
      state.token = token
      localStorage.setItem('token', token)
    },
    setUser(state, user) {
      state.user = user
      localStorage.setItem('user', JSON.stringify(user))
    },
    clearAuth(state) {
      state.token = ''
      state.user = null
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    },
    setPatients(state, patients) {
      state.patients = patients
    },
    setActivePatient(state, patient) {
      state.activePatient = patient
    },
    addPatient(state, patient) {
      state.patients.push(patient)
    },
    updatePatient(state, updatedPatient) {
      const index = state.patients.findIndex(p => p.id === updatedPatient.id)
      if (index !== -1) {
        state.patients.splice(index, 1, updatedPatient)
      }
    },
    deletePatient(state, id) {
      state.patients = state.patients.filter(p => p.id !== id)
    },
    setAlgorithms(state, algorithms) {
      state.algorithms = algorithms
    },
    addAlgorithm(state, algorithm) {
      state.algorithms.push(algorithm)
    },
    updateAlgorithmStatus(state, { id, status }) {
      const index = state.algorithms.findIndex(a => a.id === id)
      if (index !== -1) {
        state.algorithms[index].status = status
      }
    }
  },
  actions: {
    login({ commit }, user) {
      if (!user) {
        console.error('登录失败: 用户数据为空')
        return
      }
      console.log('登录用户数据:', user)
      commit('setUser', user)
      commit('setToken', user.id.toString())
    },
    logout({ commit }) {
      commit('clearAuth')
    },
    fetchAlgorithms({ commit }, algorithms) {
      commit('setAlgorithms', algorithms)
    }
  },
  modules: {
  }
}) 