<template>
  <div>
    <form class="layui-form">
      <div class="demo-reg-container">
        <div class="layui-form-item">
          <div class="layui-input-wrap">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-username"></i>
            </div>
            <input
              type="text"
              name="nickname"
              value=""
              lay-verify="required"
              placeholder="用户名"
              autocomplete="off"
              class="layui-input"
              lay-affix="clear"
            />
          </div>
        </div>
        <div class="layui-form-item">
          <div class="layui-input-wrap">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-password"></i>
            </div>
            <input
              type="password"
              name="password"
              value=""
              lay-verify="required"
              placeholder="旧密码"
              autocomplete="off"
              class="layui-input"
              id="reg-password"
              lay-affix="eye"
            />
          </div>
        </div>
        <div class="layui-form-item">
          <div class="layui-input-wrap">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-password"></i>
            </div>
            <input
              type="password"
              name="password"
              value=""
              lay-verify="required"
              placeholder="密码"
              autocomplete="off"
              class="layui-input"
              id="reg-password"
              lay-affix="eye"
            />
          </div>
        </div>
        <div class="layui-form-item">
          <div class="layui-input-wrap">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-password"></i>
            </div>
            <input
              type="password"
              name="confirmPassword"
              value=""
              lay-verify="required|confirmPassword"
              placeholder="确认密码"
              autocomplete="off"
              class="layui-input"
              lay-affix="eye"
            />
          </div>
        </div>
        <div class="layui-form-item">
          <button
            class="layui-btn layui-btn-fluid"
            lay-submit
            lay-filter="demo-reg"
          >
            注册
          </button>
        </div>
        <div class="layui-form-item demo-reg-other">
          <a href="javascript:;" @click="$emit('toLogin')">返回登录</a>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "ChangePwd",
  mounted() {
    layui.use(["form", "layer"], function () {
      const form = layui.form;
      const layer = layui.layer;

      form.render();
      //TODO提交忘记密码表单
    });
  },
};
</script>

<style>
</style>