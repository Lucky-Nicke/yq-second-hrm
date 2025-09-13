const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    open: {
      app: {
        name: 'chrome',
      }
    },
    port: 8080,
    proxy: {
      // 配置代理
      '/api': {
        target: 'http://192.168.192.232',
        changeOrigin: true,
        // 支持跨域Cookie
        cookieDomainRewrite: {
          '*': '' // 不修改Cookie的domain
        }
      }
    }
  },

})
