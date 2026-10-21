const http = uni.$u.http
// 如果没有通过拦截器配置域名的话，可以在这里写上完整的URL(加上域名部分)


// post请求
export const doLogin = (params, config = {}) => http.post('/login/account', params, config)
export const doRegister = (params, config = {}) => http.post('/public/reg', params, config)

export const doFileUpload = (params, config = {}) => http.post('/file/upload', params, config)

// get请求
// TODO: backend has no user profile endpoint yet; do not invoke this route from the UI.
export const getUserInfo = (data) => http.get('/user/all', data)
export const doLogout = (data) => http.get('/login/logout', data)
