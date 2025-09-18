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
              {{ username }}
            </a>
            <!-- 头像点击下拉菜单 -->
            <dl class="layui-nav-child">
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
                <dd>
                  <a
                    href="javascript:;"
                    @click="showComponent('EmployeenInfoTable')"
                    >员工管理</a
                  >
                </dd>
              </dl>
            </li>
            <li class="layui-nav-item">
              <a href="javascript:;">下载中心</a>
            </li>
          </ul>
        </div>
      </div>

      <div class="layui-body">
        <!-- 内容主体区域：右侧框架内显示主页内容 + 切换组件 -->
        <!-- 1. 默认显示主页内容（UserInfoHeader + WelcomeContent） -->
        <div v-if="!currentComponent" class="home-content">
          <UserInfoHeader />
          <WelcomeContent />
        </div>
        <!-- 2. 点击左侧导航时，显示对应组件（覆盖主页内容） -->
        <component v-else :is="currentComponent"></component>
      </div>
    </div>
    <!-- 已将主页组件移动到右侧 .layui-body 内，此处删除外层组件引用 -->
  </div>
</template>

<script>
import router from "@/router";
import UserInfoTable from "../components/UserInfoTable.vue";
import axios from "axios";
import EmployeenInfoTable from "@/components/EmployeenInfoTable.vue";
import UserInfoHeader from "@/components/UserInfoHeader.vue";
import WelcomeContent from "@/components/WelcomeContent.vue";

export default {
  name: "iFrameView",
  components: {
    UserInfoTable,
    EmployeenInfoTable,
    UserInfoHeader,
    WelcomeContent,
  },
  data() {
    return {
      currentComponent: null, // 初始为null，默认显示主页内容
      username: localStorage.getItem("loginname"),
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
          .post("http://192.168.192.232/logout", {
            params: {
              loginname: localStorage.getItem("loginname"),
            },
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
.home-content {
  padding: 20px;
}
</style>