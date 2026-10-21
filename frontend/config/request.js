// Set VUE_APP_API_BASE_URL (e.g. https://api.example.com) in your build environment.
// Production builds deliberately fail without a configured API endpoint.
module.exports = () => {
    const configuredUrl = (process.env.VUE_APP_API_BASE_URL || '').trim();
    const REQUEST_URL = configuredUrl || (process.env.NODE_ENV === 'development' ? 'http://localhost:8088' : '');
    if (!REQUEST_URL) {
        throw new Error('VUE_APP_API_BASE_URL must be set for production builds');
    }

    uni.$u.http.setConfig((config) => {
        config.baseURL = REQUEST_URL.replace(/\/$/, '');
        return config;
    });

    uni.$u.http.interceptors.request.use((config) => {
        config.data = config.data || {};
        config.header = config.header || {};
        config.header['Content-Type'] = 'application/json';
        // Add a session token only if one was issued by the backend on login.
        const userToken = getApp().globalData.authToken;
        if (userToken) config.header.Authorization = `Bearer ${userToken}`;
        return config;
    }, (error) => Promise.reject(error));

    uni.$u.http.interceptors.response.use((response) => {
        const data = response.data;
        if (!data || data.code !== 200) {
            const custom = response.config?.custom || {};
            if (custom.toast !== false) uni.$u.toast(data?.message || data?.msg || 'Request failed');
            return Promise.reject(data || new Error('Invalid server response'));
        }
        return data.data === undefined ? {} : data.data;
    }, (error) => {
        uni.$u.toast('Network request failed');
        return Promise.reject(error);
    });
};
