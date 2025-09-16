<template>
  <div class="login-page">
    <form class="layui-form">
      <!-- 登录卡片容器 -->
      <div class="login-container">
        <!-- 登录标题 -->
        <div class="login-title">
          <h2>系统登录</h2>
        </div>

        <div class="layui-form-item">
          <div class="layui-input-wrap input-item">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-username"></i>
            </div>
            <input
              type="text"
              name="loginname"
              value=""
              v-model="loginName"
              lay-verify="required"
              placeholder="用户名"
              lay-reqtext="请填写用户名"
              autocomplete="off"
              class="layui-input"
              lay-affix="clear"
            />
          </div>
        </div>

        <div class="layui-form-item">
          <div class="layui-input-wrap input-item">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-password"></i>
            </div>
            <input
              type="password"
              name="password"
              value=""
              v-model="password"
              lay-verify="required"
              placeholder="密码"
              lay-reqtext="请填写密码"
              autocomplete="off"
              class="layui-input"
              lay-affix="eye"
            />
          </div>
        </div>

        <div class="layui-form-item remember-item">
          <input
            type="checkbox"
            name="remember"
            v-model="rememberAcc"
            lay-skin="primary"
            title="记住密码"
          />
        </div>

        <div class="layui-form-item">
          <button
            class="layui-btn layui-btn-fluid login-btn"
            lay-submit
            lay-filter="login"
          >
            登录
          </button>
        </div>

        <!-- 放置在登录按钮下方，居中显示 -->
        <div class="extra-links">
          <a href="javascript:;" class="forgot-pwd" @click="$emit('toCPaw')"
            >忘记密码？</a
          >
          <span class="separator">|</span>
          <span>还没有账号？</span>
          <a href="javascript:;" @click="$emit('toReg')">立即注册</a>
        </div>
      </div>
    </form>

    <!-- 背景装饰元素 -->
    <div class="bg-decoration bg-circle1"></div>
    <div class="bg-decoration bg-circle2"></div>
    <div class="bg-decoration bg-circle3"></div>
  </div>
</template>

<script>
import router from "@/router";
import axios from "axios";

export default {
  data() {
    return {
      loginName: localStorage.getItem("loginName"),
      password: localStorage.getItem("password"),
      rememberAcc: localStorage.getItem("rememberAcc"),
    };
  },
  mounted() {
    layui.use(["form", "layer"], function () {
      const form = layui.form;
      const layer = layui.layer;

      form.render();
      form.on("submit(login)", function (data) {
        const field = data.field;

        axios
          .post("http://192.168.192.232/login", {
            loginname: field.loginname,
            password: field.password,
          })
          .then(function (res) {
            if (res.data.code == 200) {
              if (field.remember == "on") {
                console.log(res);
                
                localStorage.setItem("loginName", field.loginname);
                localStorage.setItem("password", field.password);
                localStorage.setItem("rememberAcc", "1");
              } else {
                localStorage.removeItem("loginName");
                localStorage.removeItem("password");
                localStorage.removeItem("rememberAcc");
              }

              if (res.data.data.username == "管理员") {
                layer.msg(res.data.msg, { icon: 1, time: 1000 }, function () {
                  localStorage.setItem("loginname", res.data.data.loginname);
                  localStorage.setItem("id", res.data.data.iD);
                  localStorage.setItem("status", "2");
                  localStorage.setItem("createdate", res.data.data.createdate);
                  localStorage.setItem("username", res.data.data.username);
                  router.push("/admin");
                });
              } else {
                layer.msg(res.data.msg, { icon: 1 }, function () {
                  router.push("/home");
                });
              }
            } else {
              layer.msg(res.data.msg, { icon: 2, time: 2000 }, function () {
                window.location.reload();
              });
            }
          })
          .catch(function (error) {});
        return false; // 阻止默认 form 跳转
      });
    });
  },
  methods: {},
};
</script>

<style scoped>
/* 页面整体样式 */
.login-page {
  width: 100vw;
  height: 100vh;
  /* 渐变背景 */
  background: linear-gradient(135deg, #4361ee 0%, #3a0ca3 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
}

/* 背景装饰圆形 */
.bg-decoration {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  filter: blur(80px);
  z-index: 0;
}

.bg-circle1 {
  width: 600px;
  height: 600px;
  top: -200px;
  left: -200px;
}

.bg-circle2 {
  width: 500px;
  height: 500px;
  bottom: -150px;
  right: -150px;
}

.bg-circle3 {
  width: 400px;
  height: 400px;
  top: 50%;
  right: 10%;
  transform: translateY(-50%);
}

/* 登录容器样式 */
.login-container {
  width: 380px;
  padding: 40px 35px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(31, 38, 135, 0.15);
  backdrop-filter: blur(8px);
  border: 1px solid rgba(255, 255, 255, 0.18);
  position: relative;
  z-index: 1;
}

/* 登录标题样式 */
.login-title {
  text-align: center;
  margin-bottom: 30px;
}

.login-title h2 {
  font-size: 24px;
  color: #2d3748;
  margin-bottom: 8px;
  font-weight: 600;
}

.login-desc {
  font-size: 14px;
  color: #718096;
}

/* 输入框样式优化 */
.input-item {
  border-radius: 8px !important;
  overflow: hidden;
  transition: all 0.3s ease;
  position: relative;
}

.input-item .layui-input {
  height: 48px;
  border: none;
  background-color: #f7fafc;
  font-size: 15px;
  padding-left: 50px;
  width: 100%;
  box-sizing: border-box;
}

.input-item .layui-input-prefix {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  display: flex;
  align-items: center;
}

.input-item .layui-icon {
  font-size: 20px;
  color: #a0aec0;
}

/* 记住密码样式 */
.remember-item {
  margin-top: 15px;
  margin-bottom: 25px;
}

.remember-item .layui-form-checkbox span {
  color: #4a5568;
  font-size: 14px;
}

/* 额外链接样式（忘记密码、注册）- 放在登录按钮下方并居中 */
.extra-links {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 20px;
  font-size: 14px;
}

.forgot-pwd {
  color: #4361ee;
  transition: color 0.3s ease;
}

.forgot-pwd:hover {
  color: #3a0ca3;
  text-decoration: underline;
}

.extra-links .separator {
  color: #cbd5e0;
}

.extra-links span {
  color: #4a5568;
}

.extra-links a {
  color: #4361ee;
  font-weight: 500;
  transition: color 0.3s ease;
}

.extra-links a:hover {
  color: #3a0ca3;
  text-decoration: underline;
}

/* 登录按钮样式 */
.login-btn {
  height: 48px;
  border-radius: 8px;
  background: linear-gradient(135deg, #4361ee 0%, #3a0ca3 100%);
  border: none;
  font-size: 16px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(67, 97, 238, 0.3);
  background: linear-gradient(135deg, #3a56d4 0%, #320997 100%);
}

/* 适配移动端 */
@media (max-width: 480px) {
  .login-container {
    width: 90%;
    padding: 30px 25px;
  }

  .login-title h2 {
    font-size: 22px;
  }

  .input-item .layui-input {
    height: 44px;
  }

  .login-btn {
    height: 44px;
  }

  .extra-links {
    flex-wrap: wrap;
  }
}
</style>
