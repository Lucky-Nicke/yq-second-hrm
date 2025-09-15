<template>
  <div class="hello">
    <div class="layui-layout layui-layout-admin">
      <div class="layui-header">
        <div class="layui-logo layui-hide-xs layui-bg-black">人事管理系统</div>
        <ul class="layui-nav layui-layout-right">
          <!-- 头像显示 -->
          <li class="layui-nav-item layui-hide layui-show-sm-inline-block">
            <a href="javascript:;">
              <img
                src="//unpkg.com/outeres@0.0.10/img/layui/icon-v2.png"
                class="layui-nav-img"
              />
              tester
            </a>
            <!-- 头像点击下拉菜单 -->
            <dl class="layui-nav-child">
              <dd><a href="javascript:;">个人信息</a></dd>
              <dd><a href="javascript:;" @click="logout">退出登录</a></dd>
            </dl>
          </li>
        </ul>
      </div>

      <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
          <!-- 左侧导航区域 -->
          <ul class="layui-nav layui-nav-tree">
            <li class="layui-nav-item layui-nav-itemed">
              <a class="" href="javascript:;">公司管理</a>
              <dl class="layui-nav-child">
                <dd>
                  <a href="javascript:;" @click="showComponent('UserInfoTable')"
                    >用户管理</a
                  >
                </dd>
                <dd><a href="javascript:;">员工管理</a></dd>
                <dd><a href="javascript:;">职位管理</a></dd>
              </dl>
            </li>
            <li class="layui-nav-item">
              <a href="javascript:;">下载中心</a>
            </li>
          </ul>
        </div>
      </div>

      <div class="layui-body">
        <!-- 内容主体区域 -->
        <component :is="currentComponent"></component>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import UserInfoTable from "../components/UserInfoTable.vue";
import axios from "axios";

export default {
  name: "iFrameView",
  components: {
    UserInfoTable,
  },
  data() {
    return {
      currentComponent: null,
    };
  },
  methods: {
    showComponent(Component) {
      this.currentComponent = Component;
    },
    logout() {
      layui.use(function () {
        const layer = layui.layer;

        axios
          .post("http://192.168.192.232/logout?loginname=admin", {
            params: {},//TODO要替换为本地储存的用户信息
          })
          .then(function (res) {
            if (res.data.code == 200) {
              localStorage.removeItem("username");
              localStorage.removeItem("loginname");
              localStorage.removeItem("id");
              localStorage.removeItem("status");
              localStorage.removeItem("createdate");
              localStorage.removeItem("username");
              layer.msg("退出登录成功！", { icon: 1, time: 1000 }, function () {
                router.push("/");
              });
            }
          });
        //TODO退出登录异步请求
      });
    },
  },
  mounted() {
    layui.use(function () {
      const element = layui.element;

      element.render("nav");
    });
  },
};
</script>

<style scoped lang="less">
</style>
