import request from './request'

/**
 * 获取所有算法
 */
export function getAllAlgorithms() {
  return request({
    url: '/algorithms',
    method: 'get'
  })
}

/**
 * 获取所有启用的算法
 */
export function getEnabledAlgorithms() {
  return request({
    url: '/algorithms/enabled',
    method: 'get'
  })
}

/**
 * 添加新算法
 */
export function addAlgorithm(data) {
  return request({
    url: '/algorithms',
    method: 'post',
    data
  })
}

/**
 * 更新算法状态
 */
export function updateAlgorithmStatus(id, status) {
  return request({
    url: `/algorithms/${id}/status`,
    method: 'put',
    data: { status }
  })
} 