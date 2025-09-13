<template>
  <div>
    <form class="layui-form">
      <div class="login-container">
        <div class="layui-form-item">
          <div class="layui-input-wrap">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-username"></i>
            </div>
            <input
              type="text"
              name="loginname"
              value=""
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
              lay-reqtext="请填写密码"
              autocomplete="off"
              class="layui-input"
              lay-affix="eye"
            />
          </div>
        </div>
        <div class="layui-form-item">
          <input
            type="checkbox"
            name="remember"
            lay-skin="primary"
            title="记住密码"
          />
          <a
            href="javascript:;"
            style="float: right; margin-top: 7px"
            @click="$emit('toCPaw')"
            >忘记密码？</a
          >
        </div>
        <div class="layui-form-item">
          <button
            class="layui-btn layui-btn-fluid"
            lay-submit
            lay-filter="login"
          >
            登录
          </button>
        </div>
        <div class="layui-form-item">
          <a href="javascript:;" @click="$emit('toReg')">注册帐号</a>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import router from "@/router";
import axios from "axios";

export default {
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
              console.log(res);

              if (res.data.data.username == "管理员") {
                layer.msg(res.data.msg, { icon: 1, time: 1000 }, function () {
                  router.push("/admin");
                });
              } else {
                layer.msg(res.data.msg, { icon: 1 });
                router.push("/home");
              }
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

<style>
</style>