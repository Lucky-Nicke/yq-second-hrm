<template>
  <div>
    <table class="layui-hide" id="userInfoTable"></table>
  </div>
</template>

<script>
export default {
  mounted() {
    this.initTable();
  },
  methods: {
    initTable() {
      layui.use(function () {
        const table = layui.table;

        table.on("tool(userInfoTable)", function (obj) {
          if (obj.event == "del") {
            layer.confirm(
              "你确定要删除？",
              {
                btn: ["确定", "取消"], //按钮
              },
              function () {
                let userid = obj.data.iD;
                //TODO axiox请求删除行数据
              }
            );
          } else if (obj.event == "edit") {
            //TODO 显示表格，表格显示行数据内容，确认和取消按钮，确认要再弹出来二次确认
          }
        });
        // 创建表格实例
        table.render({
          elem: "#userInfoTable",
          url: "http://192.168.192.232/user/getAllUserInfo",
          cols: [
            [
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
                <button type="button" class="layui-btn layui-bg-blue">添加用户</button>
              </div> 
          `,
          page: {
            prev: "上一页",
            next: "下一页",
            theme: "#1E9FFF",
          },
          limits: [10, 20, 30],
          width:1000
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