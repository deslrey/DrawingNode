import request from '@/utils/request'

export default {
    // 查出所有用户
    getAllUser() {
        return request({
            url: '/user/getAllUser',
            method: 'get'
        })
    }

}