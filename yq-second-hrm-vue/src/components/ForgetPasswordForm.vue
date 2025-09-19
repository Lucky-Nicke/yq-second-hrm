<template>
  <div class="forgot-password-page">
    <form class="layui-form" lay-filter="forgotPwdForm">
      <div class="demo-reg-container">
        <!-- 忘记密码标题 -->
        <div class="register-title">
          <h2>修改密码</h2>
          <p class="title-desc">验证旧密码后可设置新密码</p>
        </div>

        <!-- 用户名输入框（保留与注册页一致的样式和验证） -->
        <div class="layui-form-item">
          <div class="layui-input-wrap input-item">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-username"></i>
            </div>
            <input
              type="text"
              name="loginname"
              lay-verify="required"
              placeholder="请输入用户名"
              autocomplete="off"
              class="layui-input"
              lay-affix="clear"
            />
          </div>
        </div>

        <!-- 旧密码输入框（核心新增：用于身份验证） -->
        <div class="layui-form-item">
          <div class="layui-input-wrap input-item">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-password"></i>
            </div>
            <input
              type="password"
              name="oldPassword"
              lay-verify="required|pass"
              placeholder="请输入旧密码"
              autocomplete="off"
              class="layui-input"
              id="old-password"
              lay-affix="eye"
            />
          </div>
        </div>

        <!-- 新密码输入框 -->
        <div class="layui-form-item">
          <div class="layui-input-wrap input-item">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-password"></i>
            </div>
            <input
              type="password"
              name="newPassword"
              lay-verify="required|pass|notSameAsOld"
              placeholder="请输入新密码（6-12位，无空格）"
              autocomplete="off"
              class="layui-input"
              id="new-password"
              lay-affix="eye"
            />
          </div>
        </div>

        <!-- 确认新密码输入框 -->
        <div class="layui-form-item">
          <div class="layui-input-wrap input-item">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-password"></i>
            </div>
            <input
              type="password"
              name="confirmNewPassword"
              lay-verify="required|confirmPass"
              placeholder="请再次输入新密码"
              autocomplete="off"
              class="layui-input"
              lay-affix="eye"
            />
          </div>
        </div>

        <!-- 提交按钮（修改密码） -->
        <div class="layui-form-item">
          <button
            class="layui-btn layui-btn-fluid register-btn"
            lay-submit
            lay-filter="demo-forgotPwd"
          >
            确认修改
          </button>
        </div>

        <!-- 返回登录链接 -->
        <div class="layui-form-item demo-reg-other">
          <a href="javascript:;" @click="$emit('toLogin')">返回登录页面</a>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ForgotPassword",
  mounted() {
    const vueInstance = this;

    layui.use(["form", "layer"], function () {
      const form = layui.form;
      const layer = layui.layer;

      // 1. 自定义验证规则（适配旧密码验证流程）
      form.verify({
        // 密码规则：6-12位无空格（与注册页完全一致）
        pass: [/^[\S]{6,12}$/, "密码必须6到12位，且不能出现空格"],
        // 新增：新密码不能与旧密码相同（提升安全性）
        notSameAsOld: function (value) {
          const oldPassword = document.getElementById("old-password").value;
          if (value === oldPassword) {
            return "新密码不能与旧密码相同";
          }
        },
        // 确认新密码：与新密码一致性验证
        confirmPass: function (value) {
          const newPassword = document.getElementById("new-password").value;
          if (value !== newPassword) {
            return "两次输入的新密码不一致";
          }
        },
      });

      // 2. 监听表单提交（核心：先验证旧密码，再修改新密码）
      form.on("submit(demo-forgotPwd)", function (data) {
        // 1. 先发送旧密码验证请求（确认身份）
        axios
          .post("http://192.168.192.232/changePwd", {
            loginname: data.field.loginname, // 用户名
            newPassword: data.field.newPassword, // 验证通过后提交新密码
            oldPassword: data.field.oldPassword, // 旧密码（核心验证项）
          })
          .then(function (response) {
            if (response.data.code == 200) {
              layer.msg(
                response.data.msg,
                { icon: 1, time: 1500 },
                function () {
                  vueInstance.$emit("toLogin");
                }
              );
            } else if (response.data.code == 401) {
              layer.msg(response.data.msg, { icon: 2, time: 1500 });
            }
          })
          .catch(() => {
            layer.msg("网络错误，旧密码验证失败", { icon: 2, time: 1500 });
          });

        // 阻止默认提交行为（保留原有逻辑）
        return false;
      });

      // 初始化表单渲染（保留原有代码）
      form.render();
    });
  },
};
</script>

<style scoped>
/* 页面整体样式：完全复用注册页渐变背景，保证视觉统一 */
.forgot-password-page {
  width: 100vw;
  height: 100vh;
  background: linear-gradient(135deg, #4361ee 0%, #3a0ca3 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
}

/* 容器样式：与注册页完全一致，确保布局统一 */
.demo-reg-container {
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

/* 标题样式：调整标题文本，新增描述，保留原有布局 */
.register-title {
  text-align: center;
  margin-bottom: 30px;
}

.register-title h2 {
  font-size: 24px;
  color: #2d3748;
  margin-bottom: 8px;
  font-weight: 600;
}

.register-title .title-desc {
  font-size: 14px;
  color: #718096;
  margin: 0;
}

/* 输入框样式：完全复用注册页输入框样式，包括前缀图标、高度、背景 */
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

/* 按钮样式：复用注册页按钮的渐变、 hover 效果，仅修改按钮文本 */
.register-btn {
  height: 48px;
  border-radius: 8px;
  background: linear-gradient(135deg, #4361ee 0%, #3a0ca3 100%);
  border: none;
  font-size: 16px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.register-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(67, 97, 238, 0.3);
  background: linear-gradient(135deg, #3a56d4 0%, #320997 100%);
}

/* 返回登录链接：完全复用注册页样式，保证交互统一 */
.demo-reg-other {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
  color: #4a5568;
}

.demo-reg-other a {
  color: #4361ee;
  margin-left: 5px;
  font-weight: 500;
  transition: color 0.3s ease;
}

.demo-reg-other a:hover {
  color: #3a0ca3;
  text-decoration: underline;
}

/* 移动端适配：与注册页完全一致，确保多端体验统一 */
@media (max-width: 480px) {
  .demo-reg-container {
    width: 90%;
    padding: 30px 25px;
  }

  .register-title h2 {
    font-size: 22px;
  }

  .input-item .layui-input {
    height: 44px;
  }

  .register-btn {
    height: 44px;
  }
}
</style>