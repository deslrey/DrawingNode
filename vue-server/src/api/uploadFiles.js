import request from '@/utils/request'

export default {

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