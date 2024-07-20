import request from '@/utils/request'

export default {

    getAll(params) {
        return request({
            url: '/uploadFile/page',
            method: 'get',
            params: params
        })
    },

    uploadFile(formData) {
        return request({
            url: '/uploadFile/upload',
            method: 'post',
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
    },
    submitImportForm(formData) {
        return request({
            url: '/uploadFile/upload',
            method: 'post',
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
    },

    handleDelete(data) {
        return request({
            url: '/uploadFile/delete',
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            data: data, // 确保数据被序列化为 JSON
        })
    }


}