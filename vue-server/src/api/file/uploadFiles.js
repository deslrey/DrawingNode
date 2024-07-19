import request from '@/utils/request'

export default {

    addFile(data) {
        return request({
            url: '/uploadFile/upload',
            method: 'post',
            data: data
        })
    },

    importData(data) {
        return request({
            url: '/uploadFile/importData',
            method: 'get',
            data: data
        })
    },

    getPictureList(data) {
        return request({
            url: '/uploadFile/page',
            method: 'post',
            data: data
        })
    },

    clkBtnSave(data) {
        return request({
            url: '/uploadFile/save',
            method: 'post',
            data: data
        })
    },

    clkBtnDelete(data) {
        return request({
            url: '/uploadFile/delete',
            method: 'post',
            data: data
        })
    }

}