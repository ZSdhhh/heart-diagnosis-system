import request from './request'

export function getAllPatients() {
  return request({
    url: '/patients',
    method: 'get'
  })
}

export function getPatientsByPage(pageNum = 1, pageSize = 10) {
  return request({
    url: '/patients/page',
    method: 'get',
    params: {
      pageNum,
      pageSize
    }
  })
}

export function getPatientById(id) {
  return request({
    url: `/patients/${id}`,
    method: 'get'
  })
}

export function addPatient(data) {
  return request({
    url: '/patients',
    method: 'post',
    data
  })
}

export function updatePatient(id, data) {
  return request({
    url: `/patients/${id}`,
    method: 'put',
    data
  })
}

export function deletePatient(id) {
  return request({
    url: `/patients/${id}`,
    method: 'delete'
  })
} 