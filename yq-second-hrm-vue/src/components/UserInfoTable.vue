<template>
  <div class="full-screen-container">
    <!-- 搜索栏位置 -->
    <div class="search-container">
      <form class="layui-form search-form" lay-filter="userInfoSearchForm">
        <div
          class="layui-row search-row layui-col-space16"
          style="display: flex; align-items: center; gap: 10px"
        >
          <!-- 输入框列 -->
          <div class="layui-col-md5 layui-col-sm12">
            <div class="layui-input-wrap search-input-wrap">
              <input
                type="text"
                name="loginname"
                placeholder="请输入搜索用户名"
                class="layui-input search-input"
                lay-affix="clear"
                ref="searchInput"
              />
            </div>
          </div>

          <!-- 状态下拉筛选框 -->
          <div class="layui-col-md3 layui-col-sm12">
            <select
              name="status"
              lay-filter="statusFilter"
              class="layui-select"
              style="width: 100%; height: 42px"
            >
              <option value="">请选择</option>
              <option value="1">离线</option>
              <option value="2">在线</option>
            </select>
          </div>

          <!-- 按钮组列 -->
          <div class="layui-col-md4 layui-col-sm12 btn-group">
            <button
              class="layui-btn search-btn"
              lay-submit
              lay-filter="userInfoSearch"
            >
              <i
                class="layui-icon layui-icon-search"
                style="margin-right: 5px"
              ></i
              >搜索
            </button>
            <button
              class="layui-btn layui-btn-primary reset-btn"
              lay-submit
              lay-filter="resetSearch"
            >
              <i
                class="layui-icon layui-icon-refresh"
                style="margin-right: 5px"
              ></i
              >重置
            </button>
          </div>
        </div>
      </form>
    </div>

    <!-- 用户表格位置 -->
    <div class="table-wrapper">
      <table class="layui-hide" id="userInfoTable"></table>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  mounted() {
    this.initTable();
    this.tableEvent();
    this.topEvent();
    this.searchInfo();
    // 监听窗口 resize 事件，动态调整表格（解决窗口缩放后表格不适应问题）
    window.addEventListener("resize", this.handleResize);
  },
  methods: {
    //layui组件入口
    //创建表格实例
    initTable() {
      layui.use(["table"], function () {
        const table = layui.table;

        //表格渲染
        table.render({
          elem: "#userInfoTable",
          url: "http://192.168.192.232/user/getAllUserInfo",
          cols: [
            [
              { type: "checkbox", fixed: "left", width: "5%" }, // 百分比宽度
              {
                field: "iD",
                title: "ID",
                sort: true,
                fixed: true,
                width: "8%",
              },
              { field: "loginname", title: "用户名", width: "18%" },
              { field: "pASSWORD", title: "密码", width: "18%" },
              {
                field: "sTATUS",
                title: "状态",
                sort: true,
                width: "8%",
                templet: (d) =>
                  d.sTATUS === 2
                    ? '<span class="layui-badge-dot layui-bg-green"></span>在线'
                    : '<span class="layui-badge-dot layui-bg-red"></span>离线',
              },
              { field: "username", title: "身份", sort: true, width: "12%" },
              { field: "createdate", title: "创建时间", width: "15%" },
              {
                title: "操作",
                fixed: "right",
                width: "15%", // 固定右侧操作列宽度
                templet: () =>
                  '<button class="layui-btn layui-btn-xs layui-bg-blue" lay-event="edit">编辑</button>' +
                  '<button class="layui-btn layui-btn-xs layui-bg-red" lay-event="del">删除</button>',
              },
            ],
          ],
          toolbar: `
              <div class="layui-btn-container">
                <button type="button" class="layui-btn layui-bg-blue" lay-event="addUser">添加用户</button>
                <button type="button" class="layui-btn layui-bg-red" lay-event="delMoreUser">批量删除</button>
              </div> 
          `,
          page: {
            prev: "上一页",
            next: "下一页",
            theme: "#1E9FFF",
          },
          limits: [10, 20, 30],
          autoSort: true,
          initSort: { field: "iD", type: "asc" },
        });
      });
    },

    // 表格事件
    tableEvent() {
      const self = this; //获取DOM元素以调用vue的methods

      layui.use(["table", "form", "layer"], function () {
        const table = layui.table;
        const form = layui.form;
        const layer = layui.layer;

        //单元格点击事件
        table.on("tool(userInfoTable)", function (obj) {
          //删除事件
          if (obj.event == "del") {
            layer.confirm(
              "你确定要删除？",
              { btn: ["确定", "取消"] },
              function (index) {
                let userid = obj.data.iD;
                axios
                  .delete("http://192.168.192.232/user/delUserInfo", {
                    params: { delId: userid },
                  })
                  .then(function (res) {
                    if (res.data.code == 0) {
                      layer.close(index);
                      layer.msg(res.data.msg, { icon: 1 });
                      // 删除后调用搜索方法更新表格
                      const loginname = self.$refs.searchInput?.value || "";
                      const statusSelect = layui.$('select[name="status"]');
                      const status = statusSelect.val() || "";
                      self.doSearch(loginname, status);
                    } else {
                      layer.close(index);
                      layer.msg(res.data.msg, { icon: 2 });
                    }
                  })
                  .catch(function (error) {
                    console.log(error);
                    layer.msg("网络异常，请重试", { icon: 2 });
                  });
                return false;
              }
            );
          }
          //编辑事件
          else if (obj.event == "edit") {
            let editUserForm = layer.open({
              type: 1,
              title: "编辑用户",
              shadeClose: true,
              content: `
                <div class="layui-form" lay-filter="editUserForm" style="padding: 20px;">
                  <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                      <input type="text" name="loginname" lay-verify="required|username" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                    </div>
                  </div>
                  <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                      <input type="text" name="PASSWORD" lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                  </div>
                  <div class="layui-form-item">
                    <label class="layui-form-label">身份</label>
                    <div class="layui-input-block">
                      <input type="radio" name="username" value="普通用户" title="普通用户" checked>
                      <input type="radio" name="username" value="管理员" title="管理员">
                    </div>
                  </div>
                  <div class="layui-form-item">
                    <div class="layui-input-block">
                      <button class="layui-btn layui-bg-blue" lay-filter="editSubmitBtn" lay-submit >提交</button>
                    </div>
                  </div>
                </div>
              `,
              success: function () {
                //表单渲染
                form.render(null, "editUserForm");
                //表单验证是否合法
                form.verify({
                  username: function (value) {
                    if (!/^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$/.test(value)) {
                      return "用户名不能有特殊字符";
                    }
                    if (/(^_)|(__)|(_+$)/.test(value)) {
                      return "用户名首尾不能出现下划线";
                    }
                    if (/^\d+$/.test(value)) {
                      return "用户名不能全为数字";
                    }
                  },
                  password: function (value) {
                    if (!/^[\S]{6,16}$/.test(value)) {
                      return "密码必须为 6 到 16 位的非空字符";
                    }
                  },
                });
                //表单获取行数据赋值表格
                form.val("editUserForm", {
                  loginname: obj.data.loginname,
                  PASSWORD: obj.data.pASSWORD,
                  username: obj.data.username,
                });
                //表单提交事件
                form.on("submit(editSubmitBtn)", function (data) {
                  let editUserFormData = {
                    ID: obj.data.iD,
                    loginname: data.field.loginname,
                    PASSWORD: data.field.PASSWORD,
                    STATUS: obj.data.sTATUS,
                    username: data.field.username || obj.data.username,
                  };
                  axios
                    .put(
                      "http://192.168.192.232/user/updateUserInfo",
                      editUserFormData
                    )
                    .then(function (res) {
                      if (res.data.code == 0) {
                        layer.close(editUserForm);
                        layer.msg(res.data.msg, { icon: 1 });
                        // 编辑成功后调用搜索方法更新表格
                        const loginname = self.$refs.searchInput?.value || "";
                        const statusSelect = layui.$('select[name="status"]');
                        const status = statusSelect.val() || "";
                        self.doSearch(loginname, status);
                      } else {
                        layer.close(editUserForm);
                        layer.msg(res.data.msg, { icon: 2 });
                      }
                    })
                    .catch(function (error) {
                      console.log(error);
                      layer.msg("网络异常，请重试", { icon: 2 });
                    });
                  return false;
                });
              },
            });
          }
        });
      });
    },

    //顶部工具栏
    topEvent() {
      layui.use(["table", "layer", "form"], function () {
        const table = layui.table;
        const layer = layui.layer;
        const form = layui.form;

        //表格顶部工具栏事件
        table.on("toolbar(userInfoTable)", function (obj) {
          let event = obj.event;

          //批量删除事件
          if (event == "delMoreUser") {
            let id = obj.config.id;
            let checkStatus = table.checkStatus(id);
            let list = checkStatus.data;

            if (list && list.length == 0) {
              layer.msg("请选择要删除的数据！");
            } else {
              layer.confirm(
                "你确定要删除？",
                {
                  btn: ["确定", "取消"],
                },
                function (index) {
                  for (let i = 0; i < list.length; i++) {
                    //TODO批量删除需要后端接口，替换循环
                    axios
                      .delete("http://192.168.192.232/user/delUserInfo", {
                        params: {
                          delId: list[i].iD,
                        },
                      })
                      .then(function (res) {
                        table.reloadData("userInfoTable", {
                          scrollPos: true,
                        });
                        if (res.data.code == 0) {
                          //TODO后端要写一个批量删除的接口，才能使用这个msg弹窗！
                        } else if (res.data.code == 1) {
                          //TODO同上
                        }
                      })
                      .catch(function (error) {
                        console.log(error);
                        layer.msg("网络异常，请重试", { icon: 2 });
                      });
                  }
                  layer.msg("批量删除成功！", { icon: 1 });
                }
              );
            }
          }
          //添加用户事件
          else if (event == "addUser") {
            let addUser = layer.open({
              type: 1,
              title: "添加用户",
              content: `
                <div class="layui-form" lay-filter="addUserForm" style="padding: 20px;">
                  <!-- 用户名 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                      <input type="text" name="loginname" placeholder="请输入用户名" autocomplete="off" class="layui-input" lay-verify="required|username" lay-on="username-tips-top">
                    </div>
                  </div>
                
                  <!-- 密码 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                      <input type="password" name="PASSWORD" placeholder="请输入密码" autocomplete="off" class="layui-input" lay-verify="required|username" lay-on="password-tips-top">
                    </div>
                  </div>
                
                  <!-- 身份 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">身份</label>
                    <div class="layui-input-block">
                      <input type="radio" name="username" value="普通用户" title="普通用户" checked>
                      <input type="radio" name="username" value="管理员" title="管理员">
                    </div>
                  </div>
                
                  <!-- 按钮区域 -->
                  <div class="layui-form-item">
                    <div class="layui-input-block">
                      <button class="layui-btn layui-bg-blue" lay-filter="submitBtn" lay-submit>提交</button>
                    </div>
                  </div>
                </div>
              `,
              shadeClose: true,
              //打开弹层成功后的回调函数
              success: function () {
                form.render(); // 渲染全部表单
              },
            });
            //提交按钮提交表单
            form.on("submit(submitBtn)", function (data) {
              axios
                .post("http://192.168.192.232/user/addUserInfo", {
                  loginname: data.field.loginname,
                  PASSWORD: data.field.PASSWORD,
                  STATUS: "1",
                  username: data.field.username,
                })
                .then(function (res) {
                  if (res.data.code == 0) {
                    layer.close(addUser, function () {
                      layer.msg(res.data.msg, { icon: 1 });
                    });
                    table.reloadData("userInfoTable", {
                      scrollPos: true,
                    });
                  } else if (res.data.code == 1) {
                    layer.close(addUser, function () {
                      layer.msg(res.data.msg, { icon: 2 });
                    });
                  }
                })
                .catch(function (error) {
                  console.log(error);
                  layer.msg("网络异常，请重试", { icon: 2 });
                });
              return false; // 阻止默认 form 跳转
            });
          }
          return false;
        });
      });
    },

    // 搜索核心逻辑
    doSearch(loginname = "", status = "") {
      layui.use(["table", "layer"], function () {
        const table = layui.table;
        const layer = layui.layer;

        const isSearch = !!loginname || !!status;
        const url = isSearch
          ? "http://192.168.192.232/user/searchUserInfo"
          : "http://192.168.192.232/user/getAllUserInfo";

        axios
          .get("http://192.168.192.232/user/searchUserInfo", {
            params: {
              loginname: loginname,
              STATUS: status || "1",
            },
          })
          .then(function (res) {
            const { code, data: tableData, msg } = res.data;
            if (code === "0") {
              table.reload("userInfoTable", {
                data: tableData,
                page: {
                  prev: "上一页",
                  next: "下一页",
                  theme: "#1E9FFF",
                },
                url: url, // 动态切换接口
                where: {
                  // 传递参数（searchUserInfo 用，getAllUserInfo 可忽略多余参数）
                  loginname: loginname,
                  STATUS: status,
                },
              });
            } else {
              layer.msg(msg || "查询失败", { icon: 2 });
            }
          })
          .catch(function (error) {
            console.log(error);
            layer.msg("网络异常，请重试", { icon: 2 });
          });
      });
    },

    // 搜索事件
    searchInfo() {
      const self = this; //获取DOM元素以调用vue的methods
      layui.use(["form"], function () {
        const form = layui.form;

        form.render();
        form.on("submit(userInfoSearch)", function (data) {
          const loginname = data.field.loginname;
          const status = data.field.status;
          self.doSearch(loginname, status); // 使用缓存的self调用方法
          return false;
        });

        // 重置按钮事件
        form.on("submit(resetSearch)", function () {
          self.$nextTick(() => {
            form.val("userInfoSearchForm", {
              loginname: "",
              status: "",
            });
            self.initTable();
          });
          return false;
        });
      });
    },
  },
};
</script>

<style>
.table-container {
  margin: 0 auto; /* 水平居中 */
  max-width: 1200px; /* 根据需要调整最大宽度 */
  padding: 0 15px; /* 左右内边距，防止内容贴边 */
  box-sizing: border-box;
}

@media screen and (max-width: 992px) {
  .table-container {
    max-width: 100%;
  }
}

/* 自定义搜索区域样式 */
.search-container {
  /* 控制搜索区整体宽度和居中 */
  max-width: 800px;
  margin: 30px auto;
  padding: 0 20px;
}
/* 输入框容器优化 */
.search-input-wrap {
  position: relative;
  /* 输入框与按钮对齐 */
  display: flex;
  align-items: center;
}
/* 输入框美化 */
.search-input {
  height: 42px !important; /* 增加输入框高度，提升点击区域 */
  border-radius: 4px 0 0 4px !important; /* 左圆角，与按钮衔接 */
  border-right: none !important; /* 取消输入框右边框，避免与按钮重叠 */
  font-size: 14px !important;
  padding-left: 15px !important;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05) inset !important;
  transition: all 0.3s ease !important;
}
/* 输入框聚焦效果 */
.search-input:focus {
  box-shadow: 0 0 0 2px rgba(45, 183, 245, 0.2) inset !important;
  border-color: #2d93e0 !important;
}
/* 按钮样式优化 */
.search-btn {
  height: 42px !important;
  background-color: #2d93e0 !important;
  border-color: #2d93e0 !important;
  font-size: 14px !important;
  padding: 0 25px !important;
  transition: all 0.3s ease !important;
}
.search-btn:hover {
  background-color: #2580c5 !important;
  border-color: #2580c5 !important;
}
/* 重置按钮样式 */
.reset-btn {
  height: 42px !important;
  margin-left: 12px !important; /* 与搜索按钮保持间距 */
  font-size: 14px !important;
  padding: 0 20px !important;
}
/* 响应式适配（小屏幕下垂直排列） */
@media screen and (max-width: 768px) {
  .search-row {
    flex-direction: column !important;
    gap: 15px !important;
  }
  .layui-col-md5,
  .layui-col-md3 {
    width: 100% !important;
  }
  .search-input-wrap {
    width: 100% !important;
  }
  .btn-group {
    width: 100% !important;
    display: flex !important;
    justify-content: flex-start !important;
  }
  .search-btn {
    flex: 1 !important;
    max-width: 120px !important;
  }
  .reset-btn {
    flex: 1 !important;
    max-width: 120px !important;
  }
}
</style>