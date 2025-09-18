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
                name="username"
                placeholder="请输入搜索用户名"
                class="layui-input search-input"
                lay-affix="clear"
                ref="searchInput"
              />
            </div>
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
      <table class="layui-hide" id="EmployeeInfoTable"></table>
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
          elem: "#EmployeeInfoTable",
          url: "http://192.168.192.232/employee/getAllEmployeeInfo",
          cols: [
            [
              { type: "checkbox", fixed: "left", width: "5%" },
              {
                field: "iD",
                title: "ID",
                sort: true,
                fixed: true,
                width: "4%",
              },
              { field: "deptName", title: "部门", width: "8%" },
              { field: "jobNam", title: "职位", width: "8%" },
              { field: "name", title: "姓名", width: "10%" },
              { field: "cARD_ID", title: "身份证号", width: "12%" },
              { field: "aDDRESS", title: "地址", width: "10%" },
              { field: "pOST_CODE", title: "邮政编码", width: "10%" },
              { field: "tEL", title: "座机", width: "12%" },
              { field: "pHONE", title: "电话", width: "12%" },
              { field: "qQ_NUM", title: "QQ号", width: "10%" },
              { field: "eMAIL", title: "邮箱", width: "12%" },
              {
                field: "sEX",
                title: "性别",
                width: "4%",
                templet: (d) => (d.SEX === 2 ? "女" : "男"),
              },
              { field: "pARTY", title: "政治面貌", width: "8%" },
              { field: "bIRTHDAY", title: "生日", width: "12%" },
              { field: "rACE", title: "民族", width: "8%" },
              { field: "eDUCATION", title: "学历", width: "8%" },
              { field: "sPECIALITY", title: "特长", width: "10%" },
              { field: "hOBBY", title: "爱好", width: "8%" },
              { field: "rEMARK", title: "备注", width: "10%" },
              { field: "cREATE_DATE", title: "创建时间", width: "15%" },
              {
                title: "操作",
                fixed: "right",
                width: "15%",
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
        table.on("tool(EmployeeInfoTable)", function (obj) {
          //删除事件
          if (obj.event == "del") {
            layer.confirm(
              "你确定要删除？",
              { btn: ["确定", "取消"] },
              function (index) {
                let userid = obj.data.iD;
                axios
                  .delete("http://192.168.192.232/employee/delEmployeeInfo", {
                    params: { delId: userid },
                  })
                  .then(function (res) {
                    if (res.data.code == 200) {
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
              area: ["750px", "600px"],
              content: `
                <div class="layui-form" lay-filter="editEmployeeForm" style="padding: 20px;">
                    
                <!-- 部门选择 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">部门</label>
                    <div class="layui-input-block">
                        <select name="DEPT_ID" lay-verify="required" lay-search id="deptSelect">
                        <option value="">请选择部门</option>
                        </select>
                    </div>
                </div>
                
                <!-- 职位选择 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">职位</label>
                    <div class="layui-input-block">
                        <select name="JOB_ID" lay-verify="required" lay-search id="jobSelect">
                        <option value="">请选择职位</option>
                        </select>
                    </div>
                </div>
                
                <!-- 姓名 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-block">
                    <input type="text" name="NAME" lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 身份证号 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">身份证号</label>
                    <div class="layui-input-block">
                    <input type="text" name="CARD_ID" lay-verify="required|idcard" placeholder="请输入身份证号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 地址 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">地址</label>
                    <div class="layui-input-block">
                    <input type="text" name="ADDRESS" lay-verify="required" placeholder="请输入地址" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 邮政编码 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">邮政编码</label>
                    <div class="layui-input-block">
                    <input type="text" name="POST_CODE" placeholder="请输入邮政编码" lay-verify="postcode" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 座机 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">座机</label>
                    <div class="layui-input-block">
                    <input type="text" name="TEL" placeholder="请输入座机号码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 电话 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">电话</label>
                    <div class="layui-input-block">
                    <input type="text" name="PHONE" lay-verify="required|phone" placeholder="请输入手机号码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- QQ号 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">QQ号</label>
                    <div class="layui-input-block">
                    <input type="text" name="QQ_NUM" placeholder="请输入QQ号码" lay-verify="qq" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 邮箱 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-block">
                    <input type="text" name="EMAIL" lay-verify="required|email" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 性别 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-block">
                    <input type="radio" name="SEX" value="1" title="男" checked>
                    <input type="radio" name="SEX" value="0" title="女">
                    </div>
                </div>
                
                <!-- 政治面貌 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">政治面貌</label>
                    <div class="layui-input-block">
                    <select name="PARTY">
                        <option value="">请选择</option>
                        <option value="群众">群众</option>
                        <option value="团员">团员</option>
                        <option value="党员">党员</option>
                    </select>
                    </div>
                </div>
                
                <!-- 生日 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">生日</label>
                    <div class="layui-input-block">
                    <input type="text" name="BIRTHDAY" class="layui-input" placeholder="yyyy-MM-dd" id="birthdayPicker">
                    </div>
                </div>
                
                <!-- 民族 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">民族</label>
                    <div class="layui-input-block">
                    <input type="text" name="RACE" placeholder="请输入民族" autocomplete="off" class="layui-input" value="汉">
                    </div>
                </div>
                
                <!-- 学历 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">学历</label>
                    <div class="layui-input-block">
                    <select name="EDUCATION">
                        <option value="">请选择</option>
                        <option value="小学">小学</option>
                        <option value="初中">初中</option>
                        <option value="高中">高中</option>
                        <option value="专科">专科</option>
                        <option value="本科">本科</option>
                        <option value="硕士">硕士</option>
                        <option value="博士">博士</option>
                    </select>
                    </div>
                </div>
                
                <!-- 特长 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">特长</label>
                    <div class="layui-input-block">
                    <input type="text" name="SPECIALITY" placeholder="请输入特长" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 爱好 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">爱好</label>
                    <div class="layui-input-block">
                    <input type="text" name="HOBBY" placeholder="请输入爱好" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 备注 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                    <textarea name="REMARK" placeholder="请输入备注信息" class="layui-textarea"></textarea>
                    </div>
                </div>
                
                <!-- 提交按钮 -->
                <div class="layui-form-item">
                    <div class="layui-input-block">
                    <button class="layui-btn layui-bg-blue" lay-filter="addSubmitBtn" lay-submit>提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
                </div>

              `,
              success: function () {
                // 表单渲染
                form.render(null, "editEmployeeForm");

                // 表单验证
                form.verify({
                  // 身份证号验证：18位，支持最后一位为X
                  idcard: function (value) {
                    if (value && !/(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value)) {
                      return "身份证号必须为18位数字，最后一位可含X";
                    }
                  },

                  // 邮政编码验证：6位数字
                  postcode: function (value) {
                    if (value && !/^\d{6}$/.test(value)) {
                      return "邮政编码必须为6位数字";
                    }
                  },

                  // QQ号验证：5-11位数字
                  qq: function (value) {
                    if (value && !/^\d{5,11}$/.test(value)) {
                      return "QQ号必须为5-11位数字";
                    }
                  },

                  // 邮箱验证：包含@符号
                  email: function (value) {
                    if (value && !/\@/.test(value)) {
                      return "邮箱地址必须包含@符号";
                    }
                  },
                });

                // 获取部门数据并渲染
                axios
                  .get("http://192.168.192.232/dept/get")
                  .then(function (deptRes) {
                    if (deptRes.data.code === "200") {
                      // 注意接口返回的是字符串"200"
                      const deptSelect = document.getElementById("deptSelect");
                      deptRes.data.data.forEach((dept) => {
                        const option = document.createElement("option");
                        option.value = dept.id; // 部门ID
                        option.textContent = dept.nAME; // 部门名称（使用nAME字段）

                        // 编辑时回显选中状态（匹配当前员工的部门）
                        if (dept.nAME === obj.data.deptName) {
                          option.selected = true;
                        }

                        deptSelect.appendChild(option);
                      });
                      // 重新渲染下拉框
                      form.render("select", "editEmployeeForm");
                    } else {
                      layer.msg("部门数据获取失败：" + deptRes.data.msg, {
                        icon: 2,
                      });
                    }
                  })
                  .catch(function (error) {
                    console.log("获取部门数据失败：", error);
                    layer.msg("获取部门数据失败", { icon: 2 });
                  });

                // 获取职位数据并渲染
                axios
                  .get("http://192.168.192.232/job/get")
                  .then(function (jobRes) {
                    if (jobRes.data.code === "200") {
                      // 注意接口返回的是字符串"200"
                      const jobSelect = document.getElementById("jobSelect");
                      jobRes.data.data.forEach((job) => {
                        const option = document.createElement("option");
                        option.value = job.id; // 职位ID
                        option.textContent = job.nAME; // 职位名称（使用nAME字段）

                        // 编辑时回显选中状态（匹配当前员工的职位）
                        if (job.nAME === obj.data.jobNam) {
                          option.selected = true;
                        }

                        jobSelect.appendChild(option);
                      });
                      // 重新渲染下拉框
                      form.render("select", "editEmployeeForm");
                    } else {
                      layer.msg("职位数据获取失败：" + jobRes.data.msg, {
                        icon: 2,
                      });
                    }
                  })
                  .catch(function (error) {
                    console.log("获取职位数据失败：", error);
                    layer.msg("获取职位数据失败", { icon: 2 });
                  });

                // 表单数据回显
                form.val("editEmployeeForm", {
                  NAME: obj.data.name,
                  CARD_ID: obj.data.cARD_ID,
                  ADDRESS: obj.data.aDDRESS,
                  POST_CODE: obj.data.pOST_CODE,
                  TEL: obj.data.tEL,
                  PHONE: obj.data.pHONE,
                  QQ_NUM: obj.data.qQ_NUM,
                  EMAIL: obj.data.eMAIL,
                  SEX: obj.data.sEX,
                  PARTY: obj.data.pARTY,
                  BIRTHDAY: obj.data.bIRTHDAY,
                  RACE: obj.data.rACE,
                  EDUCATION: obj.data.eDUCATION,
                  SPECIALITY: obj.data.sPECIALITY,
                  HOBBY: obj.data.hOBBY,
                  REMARK: obj.data.rEMARK,
                });

                // 表单提交事件
                form.on("submit(addSubmitBtn)", function (data) {
                  let editUserFormData = {
                    ID: obj.data.iD,
                    DEPT_ID: data.field.DEPT_ID,
                    JOB_ID: data.field.JOB_ID,
                    NAME: data.field.NAME,
                    CARD_ID: data.field.CARD_ID,
                    ADDRESS: data.field.ADDRESS,
                    POST_CODE: data.field.POST_CODE,
                    TEL: data.field.TEL,
                    PHONE: data.field.PHONE,
                    QQ_NUM: data.field.QQ_NUM,
                    EMAIL: data.field.EMAIL,
                    SEX: data.field.SEX,
                    PARTY: data.field.PARTY,
                    BIRTHDAY: data.field.BIRTHDAY,
                    RACE: data.field.RACE,
                    EDUCATION: data.field.EDUCATION,
                    SPECIALITY: data.field.SPECIALITY,
                    HOBBY: data.field.HOBBY,
                    REMARK: data.field.REMARK,
                  };

                  axios
                    .put(
                      "http://192.168.192.232/employee/updateEmployeeInfo",
                      editUserFormData
                    )
                    .then(function (res) {
                      if (res.data.code == 0 || res.data.code == "200") {
                        layer.close(editUserForm);
                        layer.msg(res.data.msg || "修改成功", { icon: 1 });
                        // 编辑成功后更新表格
                        const loginname = self.$refs.searchInput?.value || "";
                        const statusSelect = layui.$('select[name="status"]');
                        const status = statusSelect.val() || "";
                        self.doSearch(loginname, status);
                      } else {
                        layer.msg(res.data.msg || "修改失败", { icon: 2 });
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
      const self = this;
      layui.use(["table", "layer", "form"], function () {
        const table = layui.table;
        const layer = layui.layer;
        const form = layui.form;

        //表格顶部工具栏事件
        table.on("toolbar(EmployeeInfoTable)", function (obj) {
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
                function () {
                  for (let i = 0; i < list.length; i++) {
                    //TODO批量删除需要后端接口，替换循环
                    axios
                      .delete(
                        "http://192.168.192.232/employee/delEmployeeInfo",
                        {
                          params: {
                            delId: list[i].iD,
                          },
                        }
                      )
                      .then(function (res) {
                        table.reloadData("EmployeeInfoTable", {
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
            let addEmployeeForm = layer.open({
              type: 1,
              title: "新增员工",
              shadeClose: true,
              area: ["750px", "600px"],
              content: `
                <div class="layui-form" lay-filter="addEmployeeForm" style="padding: 20px;">
                    
                <!-- 部门选择 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">部门</label>
                    <div class="layui-input-block">
                        <select name="DEPT_ID" lay-verify="required" lay-search id="deptSelect">
                        <option value="">请选择部门</option>
                        </select>
                    </div>
                </div>
                
                <!-- 职位选择 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">职位</label>
                    <div class="layui-input-block">
                        <select name="JOB_ID" lay-verify="required" lay-search id="jobSelect">
                        <option value="">请选择职位</option>
                        </select>
                    </div>
                </div>
                
                <!-- 姓名 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-block">
                    <input type="text" name="NAME" lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 身份证号 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">身份证号</label>
                    <div class="layui-input-block">
                    <input type="text" name="CARD_ID" lay-verify="required|idcard" placeholder="请输入身份证号" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 地址 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">地址</label>
                    <div class="layui-input-block">
                    <input type="text" name="ADDRESS" lay-verify="required" placeholder="请输入地址" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 邮政编码 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">邮政编码</label>
                    <div class="layui-input-block">
                    <input type="text" name="POST_CODE" placeholder="请输入邮政编码" lay-verify="postcode" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 座机 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">座机</label>
                    <div class="layui-input-block">
                    <input type="text" name="TEL" placeholder="请输入座机号码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 电话 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">电话</label>
                    <div class="layui-input-block">
                    <input type="text" name="PHONE" lay-verify="required|phone" placeholder="请输入手机号码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- QQ号 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">QQ号</label>
                    <div class="layui-input-block">
                    <input type="text" name="QQ_NUM" placeholder="请输入QQ号码" lay-verify="qq" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 邮箱 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-block">
                    <input type="text" name="EMAIL" lay-verify="required|email" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 性别 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-block">
                    <input type="radio" name="SEX" value="1" title="男" checked>
                    <input type="radio" name="SEX" value="0" title="女">
                    </div>
                </div>
                
                <!-- 政治面貌 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">政治面貌</label>
                    <div class="layui-input-block">
                    <select name="PARTY">
                        <option value="">请选择</option>
                        <option value="群众">群众</option>
                        <option value="团员">团员</option>
                        <option value="党员">党员</option>
                    </select>
                    </div>
                </div>
                
                <!-- 生日 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">生日</label>
                    <div class="layui-input-block">
                    <input type="text" name="BIRTHDAY" class="layui-input" placeholder="yyyy-MM-dd" id="birthdayPicker">
                    </div>
                </div>
                
                <!-- 民族 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">民族</label>
                    <div class="layui-input-block">
                    <input type="text" name="RACE" placeholder="请输入民族" autocomplete="off" class="layui-input" value="汉">
                    </div>
                </div>
                
                <!-- 学历 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">学历</label>
                    <div class="layui-input-block">
                    <select name="EDUCATION">
                        <option value="">请选择</option>
                        <option value="小学">小学</option>
                        <option value="初中">初中</option>
                        <option value="高中">高中</option>
                        <option value="专科">专科</option>
                        <option value="本科">本科</option>
                        <option value="硕士">硕士</option>
                        <option value="博士">博士</option>
                    </select>
                    </div>
                </div>
                
                <!-- 特长 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">特长</label>
                    <div class="layui-input-block">
                    <input type="text" name="SPECIALITY" placeholder="请输入特长" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 爱好 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">爱好</label>
                    <div class="layui-input-block">
                    <input type="text" name="HOBBY" placeholder="请输入爱好" autocomplete="off" class="layui-input">
                    </div>
                </div>
                
                <!-- 备注 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                    <textarea name="REMARK" placeholder="请输入备注信息" class="layui-textarea"></textarea>
                    </div>
                </div>
                
                <!-- 提交按钮 -->
                <div class="layui-form-item">
                    <div class="layui-input-block">
                    <button class="layui-btn layui-bg-blue" lay-filter="addSubmitBtn" lay-submit>提交</button>
                    </div>
                </div>
                </div>

              `,
              success: function () {
                // 表单渲染
                form.render(null, "addEmployeeForm");

                // 表单验证
                form.verify({
                  // 身份证号验证：18位，支持最后一位为X
                  idcard: function (value) {
                    if (value && !/(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value)) {
                      return "身份证号必须为18位数字，最后一位可含X";
                    }
                  },

                  // 邮政编码验证：6位数字
                  postcode: function (value) {
                    if (value && !/^\d{6}$/.test(value)) {
                      return "邮政编码必须为6位数字";
                    }
                  },

                  // QQ号验证：5-11位数字
                  qq: function (value) {
                    if (value && !/^\d{5,11}$/.test(value)) {
                      return "QQ号必须为5-11位数字";
                    }
                  },

                  // 邮箱验证：包含@符号
                  email: function (value) {
                    if (value && !/\@/.test(value)) {
                      return "邮箱地址必须包含@符号";
                    }
                  },
                });

                // 获取部门数据并渲染
                axios
                  .get("http://192.168.192.232/dept/get")
                  .then(function (deptRes) {
                    if (deptRes.data.code === "200") {
                      // 注意接口返回的是字符串"200"
                      const deptSelect = document.getElementById("deptSelect");
                      deptRes.data.data.forEach((dept) => {
                        const option = document.createElement("option");
                        option.value = dept.id; // 部门ID
                        option.textContent = dept.nAME; // 部门名称（使用nAME字段）

                        deptSelect.appendChild(option);
                      });
                      // 重新渲染下拉框
                      form.render("select", "addEmployeeForm");
                    } else {
                      layer.msg("部门数据获取失败：" + deptRes.data.msg, {
                        icon: 2,
                      });
                    }
                  })
                  .catch(function (error) {
                    console.log("获取部门数据失败：", error);
                    layer.msg("获取部门数据失败", { icon: 2 });
                  });

                // 获取职位数据并渲染
                axios
                  .get("http://192.168.192.232/job/get")
                  .then(function (jobRes) {
                    if (jobRes.data.code === "200") {
                      // 注意接口返回的是字符串"200"
                      const jobSelect = document.getElementById("jobSelect");
                      jobRes.data.data.forEach((job) => {
                        const option = document.createElement("option");
                        option.value = job.id; // 职位ID
                        option.textContent = job.nAME; // 职位名称（使用nAME字段）

                        jobSelect.appendChild(option);
                      });
                      // 重新渲染下拉框
                      form.render("select", "addEmployeeForm");
                    } else {
                      layer.msg("职位数据获取失败：" + jobRes.data.msg, {
                        icon: 2,
                      });
                    }
                  })
                  .catch(function (error) {
                    console.log("获取职位数据失败：", error);
                    layer.msg("获取职位数据失败", { icon: 2 });
                  });

                // 表单提交事件
                form.on("submit(addSubmitBtn)", function (data) {
                  let addUserFormData = {
                    DEPT_ID: data.field.DEPT_ID,
                    JOB_ID: data.field.JOB_ID,
                    NAME: data.field.NAME,
                    CARD_ID: data.field.CARD_ID,
                    ADDRESS: data.field.ADDRESS,
                    POST_CODE: data.field.POST_CODE,
                    TEL: data.field.TEL,
                    PHONE: data.field.PHONE,
                    QQ_NUM: data.field.QQ_NUM,
                    EMAIL: data.field.EMAIL,
                    SEX: data.field.SEX,
                    PARTY: data.field.PARTY,
                    BIRTHDAY: data.field.BIRTHDAY,
                    RACE: data.field.RACE,
                    EDUCATION: data.field.EDUCATION,
                    SPECIALITY: data.field.SPECIALITY,
                    HOBBY: data.field.HOBBY,
                    REMARK: data.field.REMARK,
                  };

                  axios
                    .post(
                      "http://192.168.192.232/employee/addEmployeeInfo",
                      addUserFormData
                    )
                    .then(function (res) {
                      // 修复：兼容数字和字符串格式的code
                      if (res.data.code == 200 || res.data.code == "200") {
                        layer.close(addEmployeeForm);
                        layer.msg(res.data.msg || "新增成功", { icon: 1 });
                        // 修复：用self获取searchInput，调用doSearch刷新表格
                        const loginname = self.$refs.searchInput?.value || "";
                        self.doSearch(loginname);
                      } else {
                        layer.msg(res.data.msg || "新增失败", { icon: 2 });
                      }
                    })
                    .catch(function (error) {
                      console.log("新增员工失败：", error);
                      layer.msg("网络异常，请重试", { icon: 2 });
                    });
                  return false;
                });
              },
            });
          }
          return false;
        });
      });
    },

    // 搜索核心逻辑
    doSearch(username = "") {
      layui.use(["table", "layer"], function () {
        const table = layui.table;
        const layer = layui.layer;

        const isSearch = !!username;
        const url = isSearch
          ? "http://192.168.192.232/employee/searchEmployeeInfo"
          : "http://192.168.192.232/employee/getAllEmployeeInfo";

        axios
          .get("http://192.168.192.232/employee/searchEmployeeInfo", {
            params: {
              username: username,
            },
          })
          .then(function (res) {
            const { code, data: tableData, msg } = res.data;
            if (code === "0") {
              table.reload("EmployeeInfoTable", {
                data: tableData,
                page: {
                  prev: "上一页",
                  next: "下一页",
                  theme: "#1E9FFF",
                },
                url: url, // 动态切换接口
                where: {
                  // 传递参数（searchUserInfo 用，getAllUserInfo 可忽略多余参数）
                  username: username,
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
          const username = data.field.username;
          self.doSearch(username); // 使用缓存的self调用方法
          return false;
        });

        // 重置按钮事件
        form.on("submit(resetSearch)", function () {
          self.$nextTick(() => {
            form.val("userInfoSearchForm", {
              username: "",
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

css
/* 自定义搜索区域样式 */
.search-container {
  /* 控制搜索区整体宽度和居中 */
  max-width: 800px;
  margin: 30px auto;
  padding: 0 20px;
  /* 使用 Flex 布局，让内部元素水平居中且垂直居中 */
  display: flex;
  justify-content: center;
  align-items: center;
}
/* 输入框容器优化 */
.search-input-wrap {
  position: relative;
  /* 输入框与按钮对齐 */
  display: flex;
  align-items: center;
  flex: 1; /* 让输入框容器占满剩余空间 */
  max-width: 400px; /* 限制输入框最大宽度 */
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
  width: 100%; /* 让输入框填满容器 */
}
/* 输入框聚焦效果 */
.search-input:focus {
  box-shadow: 0 0 0 2px rgba(45, 183, 245, 0.2) inset !important;
  border-color: #2d93e0 !important;
}
/* 按钮组容器 */
.btn-group {
  display: flex;
  gap: 10px; /* 按钮之间的间距 */
}
/* 按钮样式优化 */
.search-btn {
  height: 42px !important;
  background-color: #2d93e0 !important;
  border-color: #2d93e0 !important;
  font-size: 14px !important;
  padding: 0 25px !important;
  transition: all 0.3s ease !important;
  border-radius: 0 4px 4px 0 !important; /* 右圆角，与输入框衔接 */
}
.search-btn:hover {
  background-color: #2580c5 !important;
  border-color: #2580c5 !important;
}
/* 重置按钮样式 */
.reset-btn {
  height: 42px !important;
  font-size: 14px !important;
  padding: 0 20px !important;
  border-radius: 4px !important; /* 重置按钮自身圆角 */
}
/* 响应式适配（小屏幕下垂直排列） */
@media screen and (max-width: 768px) {
  .search-container {
    flex-direction: column; /* 垂直排列 */
    align-items: stretch; /* 让子元素拉伸填充宽度 */
    gap: 15px; /* 垂直方向间距 */
  }
  .search-input-wrap {
    max-width: 100%; /* 输入框容器占满宽度 */
  }
  .btn-group {
    justify-content: flex-start; /* 按钮左对齐 */
  }
  .search-btn {
    border-radius: 4px !important; /* 搜索按钮自身圆角 */
  }
}
</style>