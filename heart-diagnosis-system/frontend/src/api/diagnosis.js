import request from './request'

export function diagnose(data) {
  return request({
    url: '/diagnosis',
    method: 'post',
    data
  })
}

export function getDiagnosisByPatientId(patientId) {
  return request({
    url: `/diagnosis/patient/${patientId}`,
    method: 'get'
  })
}

export function getDiagnosisByPatientIdWithPaging(patientId, pageNum = 1, pageSize = 10) {
  return request({
    url: `/diagnosis/patient/${patientId}/page`,
    method: 'get',
    params: {
      pageNum,
      pageSize
    }
  })
}

export function getStatistics() {
  return request({
    url: '/statistics',
    method: 'get'
  })
} 