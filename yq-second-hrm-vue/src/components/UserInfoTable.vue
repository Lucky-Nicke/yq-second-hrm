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
      layui.use(
        function () {
          var table = layui.table;

          // 创建表格实例
          table.render({
            elem: "#userInfoTable",
            url: "http://localhost/user/getAllUserInfo",
            cols: [
              [
                {
                  field: "iD",
                  title: "ID",
                  width: 80,
                  sort: true,
                  fixed: true,
                },
                { field: "loginname", title: "用户名", width: 80 },
                { field: "pASSWORD", title: "密码", width: 80, sort: true },
                {
                  field: "sTATUS",
                  title: "状态",
                  width: 80,
                  templet: function (d) {
                    return d.sTATUS === 2 ? "正常" : "禁用";
                  },
                },
                { field: "username", title: "身份" },
                { field: "createdate", title: "创建时间" },
              ],
            ],
            page: true,
            limits: [10, 20, 30],
            height: 310,
          });
        }.bind(this)
      );
    },
  },

  activated() {
    this.initTable();
  },
};
</script>

<style>
</style>