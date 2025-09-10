<template>
  <div>
    <table class="layui-hide" id="userInfoTable"></table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  mounted() {
    this.initTable();
  },
  methods: {
    initTable() {
      //layui组件入口
      layui.use(["table", "form", "util"], function () {
        const table = layui.table;
        const form = layui.form;

        //单元格事件
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
              }
            );
            //单元格编辑按钮
          } else if (obj.event == "edit") {
            console.log(obj);

            let editUserForm = layer.open({
              type: 1,
              title: "编辑用户",
              area: ["560px", "400px"],
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
                      <input type="radio" name="STATUS" value="1" title="启用" checked>
                      <input type="radio" name="STATUS" value="2" title="禁用">
                    </div>
                  </div>
                
                  <!-- 身份 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">身份</label>
                    <div class="layui-input-block">
                      <input type="radio" name="username" value="普通用户" title="普通用户" checked>
                      <input type="radio" name="username" value="管理员" title="管理员">
                      <input type="radio" name="username" value="超级管理员" title="超级管理员">
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
                  // TODO 验证用户名
                  loginname: function (value, elem) {},
                  // TODO 验证密码
                  password: function (value, elem) {},
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
                        table.reloadData("userInfoTable", {
                          scrollPos: true,
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
              },
            });
          }
        });

        //顶部工具栏事件
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
            //添加用户按钮事件
          } else if (event == "addUserInfo") {
            let addUserInfoForm = layer.open({
              type: 1,
              title: "添加用户",
              area: ["560px", "400px"],
              shadeClose: true,
              content: `
                <div class="layui-form" lay-filter="addUserForm" style="padding: 20px;">
                  <!-- 用户名 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">用户名</label>
                    <div class="layui-input-block">
                      <input type="text" name="loginname" placeholder="请输入用户名" autocomplete="off" class="layui-input" lay-verify="required|username">
                    </div>
                  </div>
                
                  <!-- 密码 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                      <input type="password" name="PASSWORD" placeholder="请输入密码" autocomplete="off" class="layui-input" lay-verify="required|username">
                    </div>
                  </div>
                
                  <!-- 状态 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">状态</label>
                    <div class="layui-input-block">
                      <input type="radio" name="STATUS" value="1" title="启用" checked>
                      <input type="radio" name="STATUS" value="2" title="禁用">
                    </div>
                  </div>
                
                  <!-- 身份 -->
                  <div class="layui-form-item">
                    <label class="layui-form-label">身份</label>
                    <div class="layui-input-block">
                      <input type="radio" name="username" value="普通用户" title="普通用户" checked>
                      <input type="radio" name="username" value="管理员" title="管理员">
                      <input type="radio" name="username" value="超级管理员" title="超级管理员">
                    </div>
                  </div>
                
                  <!-- 按钮区域 -->
                  <div class="layui-form-item">
                    <div class="layui-input-block">
                      <button class="layui-btn layui-bg-blue" id="submitBtn" lay-submit>提交</button>
                      <button class="layui-btn layui-btn-primary" lay-submit id="resetBtn">重置</button>
                    </div>
                  </div>
                </div>
              `,
              success: function (layero) {
                //渲染表格
                form.render(null, "addUserForm");
                //验证表格内容是否合法
                form.verify({
                  // TODO 验证用户名
                  loginname: function (value, elem) {},
                  // TODO 验证密码
                  password: function (value, elem) {},
                });
                //提交事件
                form.on("submit", function (data) {
                  let choice = data.elem.id;
                  switch (choice) {
                    case "submitBtn": {
                      axios
                        .post("http://192.168.192.232/user/addUserInfo", {
                          PASSWORD: data.field.PASSWORD,
                          STATUS: data.field.STATUS,
                          loginname: data.field.loginname,
                          username: data.field.username,
                        })
                        .then(function (res) {
                          if (res.data.code == 0) {
                            layer.close(addUserInfoForm, function () {
                              layer.msg(res.data.msg, { icon: 1 });
                            });
                            table.reloadData("userInfoTable", {
                              scrollPos: true,
                            });
                          } else if (res.data.code == 1) {
                            layer.close(addUserInfoForm, function () {
                              layer.msg(res.data.msg, { icon: 2 });
                            });
                          }
                        })
                        .catch(function (error) {
                          console.log(error);
                        });
                      break;
                    }
                    case "resetBtn": {
                      // 获取表单元素
                      const formElem = layero.find(".layui-form")[0];
                      // 重置输入框
                      formElem.querySelector('input[name="loginname"]').value =
                        "";
                      formElem.querySelector('input[name="PASSWORD"]').value =
                        "";
                      // 重置单选按钮到默认状态
                      formElem.querySelector(
                        'input[name="STATUS"][value="1"]'
                      ).checked = true;
                      formElem.querySelector(
                        'input[name="username"][value="普通用户"]'
                      ).checked = true;
                      // 重新渲染表单使重置生效
                      form.render("radio", "addUserForm");
                      return false;
                    }
                  }
                });
              },
            });
          }
        });

        // 创建表格实例
        table.render({
          elem: "#userInfoTable",
          url: "http://192.168.192.232/user/getAllUserInfo",
          cols: [
            [
              { type: "checkbox", fixed: "left" },
              {
                field: "iD",
                title: "ID",
                width: 80,
                sort: true,
                fixed: true,
              },
              { field: "loginname", title: "用户名", width: 200 },
              { field: "pASSWORD", title: "密码", width: 200 },
              {
                field: "sTATUS",
                title: "状态",
                sort: true,
                width: 80,
                templet: function (d) {
                  return d.sTATUS === 1 ? "正常" : "禁用";
                },
              },
              { field: "username", title: "身份", sort: true, width: 100 },
              { field: "createdate", title: "创建时间", width: 170 },
              {
                title: "操作",
                fixed: "right",
                width: 120,
                templet: function () {
                  let s =
                    '<button type="button" class="layui-btn layui-btn-xs layui-bg-blue" lay-event="edit">编辑</button>' +
                    '<button type="button" class="layui-btn layui-btn-xs layui-bg-red" lay-event="del">删除</button>';
                  return s;
                },
              },
            ],
          ],
          toolbar: `
              <div>
                <button type="button" class="layui-btn layui-bg-red" lay-event="delMoreUser">批量删除</button>
                <button type="button" class="layui-btn layui-bg-blue" lay-event="addUserInfo">添加用户</button>
              </div> 
          `,
          page: {
            prev: "上一页",
            next: "下一页",
            theme: "#1E9FFF",
          },
          limits: [10, 20, 30],
          width: 1000,
        });
      });
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