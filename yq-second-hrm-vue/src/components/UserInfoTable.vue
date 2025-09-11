<template>
  <div class="full-screen-container">
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
    this.SearchDropdownEvent();
    this.initSearchDropdown();
    // 监听窗口 resize 事件，动态调整表格（解决窗口缩放后表格不适应问题）
    window.addEventListener("resize", this.handleResize);
  },
  methods: {
    //layui组件入口
    //创建表格实例
    initTable() {
      layui.use(["table", "form", "layer", "util"], function () {
        const table = layui.table;

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

    //单元格事件
    tableEvent() {
      layui.use(["table", "form", "layer", "util"], function () {
        const table = layui.table;
        const form = layui.form;
        const layer = layui.layer;

        table.on("tool(userInfoTable)", function (obj) {
          //单元格删除按钮
          if (obj.event == "del") {
            layer.confirm(
              "你确定要删除？",
              {
                btn: ["确定", "取消"],
              },
              function (index) {
                let userid = obj.data.iD;
                axios
                  .delete("http://192.168.192.232/user/delUserInfo", {
                    params: {
                      delId: userid,
                    },
                  })
                  .then(function (res) {
                    if (res.data.code == 0) {
                      layer.close(index, function () {
                        layer.msg(res.data.msg, { icon: 1 });
                      });
                      table.reloadData("userInfoTable", {
                        scrollPos: true,
                      });
                    } else if (res.data.code == 1) {
                      layer.close(index, function () {
                        layer.msg(res.data.msg, { icon: 2 });
                      });
                    }
                  })
                  .catch(function (error) {
                    console.log(error);
                  });
                return false;
              }
            );
            //单元格编辑按钮
          } else if (obj.event == "edit") {
            let editUserForm = layer.open({
              type: 1,
              title: "编辑用户",
              area: ["560px", "350px"],
              shadeClose: true,
              content: `
                <div class="layui-form" lay-filter="editUserForm" style="padding: 20px;">
                  <!-- 用户名 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                      <input type="text" name="loginname" lay-verify="required|username"  placeholder="请输入用户名" autocomplete="off" class="layui-input">
                    </div>
                  </div>
                
                  <!-- 密码 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                      <input type="text" name="PASSWORD" lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                  </div>

                  <!-- 状态 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">状态</label>
                    <div class="layui-input-block">
                      <input type="radio" name="STATUS" value="2" title="在线" checked>
                      <input type="radio" name="STATUS" value="1" title="离线">
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
                      <button class="layui-btn layui-bg-blue" id="submitBtn" lay-submit >提交</button>
                    </div>
                  </div>
                </div>
              `,
              success: function () {
                //渲染表格
                form.render(null, "editUserForm");
                //验证表格内容是否合法
                form.verify({
                  // 验证用户名
                  username: function (value, elem) {
                    if (
                      !new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(
                        value
                      )
                    ) {
                      return "用户名不能有特殊字符";
                    }
                    if (/(^_)|(__)|(_+$)/.test(value)) {
                      return "用户名首尾不能出现下划线";
                    }
                    if (/^\d+$/.test(value)) {
                      return "用户名不能全为数字";
                    }
                  },
                  // 验证密码
                  password: function (value, elem) {
                    if (!/^[\S]{6,16}$/.test(value)) {
                      return "密码必须为 6 到 16 位的非空字符";
                    }
                  },
                });
                //表格取值
                form.val("editUserForm", {
                  loginname: obj.data.loginname,
                  PASSWORD: obj.data.pASSWORD,
                  STATUS: String(obj.data.sTATUS),
                  username: obj.data.username,
                });
                //提交事件
                form.on("submit", function (data) {
                  let editUserFormData = {
                    ID: obj.data.iD,
                    loginname: data.field.loginname,
                    PASSWORD: data.field.PASSWORD,
                    STATUS: data.field.STATUS,
                    username: data.field.username,
                  };
                  //put请求
                  axios
                    .put(
                      "http://192.168.192.232/user/updateUserInfo",
                      editUserFormData
                    )
                    .then(function (res) {
                      if (res.data.code == 0) {
                        layer.close(editUserForm, function () {
                          layer.msg(res.data.msg, { icon: 1 });
                        });
                        table.reload("userInfoTable", {
                          scrollPos: false,
                        });
                      } else if (res.data.code == 1) {
                        layer.close(editUserForm, function () {
                          layer.msg(res.data.msg, { icon: 2 });
                        });
                      }
                    })
                    .catch(function (error) {
                      console.log(error);
                    });
                });
                return false;
              },
            });
          }
        });
      });
    },

    //顶部工具栏事件
    SearchDropdownEvent() {
      layui.use(["table", "form", "layer", "util"], function () {
        const table = layui.table;
        const layer = layui.layer;

        table.on("toolbar(userInfoTable)", function (obj) {
          let event = obj.event;

          //批量删除按钮事件
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
                      });
                  }
                  layer.msg("批量删除成功！", { icon: 1 });
                }
              );
            }
          } else if (event == "addUser") {
          }
        });
      });
      return false;
    },
  },

  activated() {
    this.initTable();
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
</style>