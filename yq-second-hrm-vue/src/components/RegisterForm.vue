<template>
  <div class="register-page">
    <form class="layui-form" lay-filter="registerForm">
      <div class="demo-reg-container">
        <!-- 注册标题 -->
        <div class="register-title">
          <h2>用户注册</h2>
        </div>

        <div class="layui-form-item">
          <div class="layui-input-wrap input-item">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-username"></i>
            </div>
            <input
              type="text"
              name="nickname"
              lay-verify="required"
              placeholder="用户名"
              autocomplete="off"
              class="layui-input"
              lay-affix="clear"
            />
          </div>
        </div>

        <div class="layui-form-item">
          <div class="layui-input-wrap input-item">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-password"></i>
            </div>
            <input
              type="password"
              name="password"
              lay-verify="required|pass"
              placeholder="密码"
              autocomplete="off"
              class="layui-input"
              id="reg-password"
              lay-affix="eye"
            />
          </div>
        </div>

        <div class="layui-form-item">
          <div class="layui-input-wrap input-item">
            <div class="layui-input-prefix">
              <i class="layui-icon layui-icon-password"></i>
            </div>
            <input
              type="password"
              name="confirmPassword"
              lay-verify="required|confirmPass"
              placeholder="确认密码"
              autocomplete="off"
              class="layui-input"
              lay-affix="eye"
            />
          </div>
        </div>

        <div class="layui-form-item agreement-item">
          <input
            type="checkbox"
            name="agreement"
            lay-skin="primary"
            title="同意"
            lay-verify="required"
          />
          <a
            href="javascript:;"
            class="agreement-link"
            lay-on="test-page-title"
          >
            用户协议
          </a>
        </div>

        <div class="layui-form-item">
          <button
            class="layui-btn layui-btn-fluid register-btn"
            lay-submit
            lay-filter="demo-reg"
          >
            注册
          </button>
        </div>

        <div class="layui-form-item demo-reg-other">
          <a href="javascript:;" @click="$emit('toLogin')">登录已有帐号</a>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "Register",
  mounted() {
    layui.use(["form", "layer"], function () {
      const form = layui.form;
      const layer = layui.layer;
      const util = layui.util;

      util.on("lay-on", {
        "test-page-title": function () {
          layer.open({
            type: 1,
            area: ["800px", "420px"], // 宽高
            title: false, // 不显示标题栏
            shadeClose: true, // 点击遮罩关闭层
            content: `
              <body class="layui-bg-gray-50 min-h-screen">
                <!-- 主要内容区 -->
                <main class="layui-container layui-center py-6">
                    <div class="layui-card layui-shadow-md layui-bg-white rounded-lg mb-6" style="max-width: 700px;">
                        <!-- 标题区域 -->
                        <div class="layui-card-header text-center border-b pb-4">
                            <h1 class="layui-h1 font-bold text-gray-800 mb-2">用户服务协议</h1>
                            <p class="layui-text-gray-500 text-sm">最后更新日期：2025年9月15日</p>
                            <div class="layui-progress layui-progress-horizontal mt-3" style="width: 100px; margin: 0 auto;">
                                <div class="layui-progress-bar layui-bg-primary" lay-percent="100"></div>
                            </div>
                        </div>

                        <!-- 协议内容 -->
                        <div class="layui-card-body layui-text" style="line-height: 1.8; padding: 20px;">
                            <!-- 1.协议接受 -->
                            <section class="mb-6">
                                <h2 class="layui-h2 font-semibold mb-3 text-gray-800 layui-flex layui-items-center">
                                    <i class="layui-icon layui-icon-info-circle text-primary mr-2"></i>
                                    1. 协议接受
                                </h2>
                                <p class="mb-3">欢迎使用我们的服务。本用户服务协议（以下简称"协议"）是您与我们之间关于使用我们提供的各项服务所订立的协议。请您在注册或使用我们的服务前仔细阅读本协议的全部内容。</p>
                                <p>您一旦注册或使用我们的服务，即视为您已阅读、理解并同意本协议的全部内容，包括我们对本协议随时所做的任何修改。如果您不同意本协议的任何内容，您应立即停止注册或使用我们的服务。</p>
                            </section>

                            <!-- 2.服务内容 -->
                            <section class="mb-6">
                                <h2 class="layui-h2 font-semibold mb-3 text-gray-800 layui-flex layui-items-center">
                                    <i class="layui-icon layui-icon-user-circle text-primary mr-2"></i>
                                    2. 服务内容
                                </h2>
                                <p class="mb-3">我们提供的服务包括但不限于：账户注册、信息发布、数据存储、在线交流等功能。我们保留随时变更、中断或终止部分或全部服务的权利，且无需提前通知用户。</p>
                                <p>我们在提供服务时，可能会对部分服务的用户收取一定的费用。如相关服务收费，我们会在您使用前向您明示，您可自主选择是否使用该等收费服务。</p>
                            </section>

                            <!-- 3.用户账户 -->
                            <section class="mb-6">
                                <h2 class="layui-h2 font-semibold mb-3 text-gray-800 layui-flex layui-items-center">
                                    <i class="layui-icon layui-icon-identity text-primary mr-2"></i>
                                    3. 用户账户
                                </h2>
                                <p class="mb-3">在使用我们的部分服务时，您需要注册一个账户。您应保证注册信息的真实性、准确性和完整性。您应对您的账户和密码的安全负责，且应对通过您的账户和密码所进行的所有活动承担责任。</p>
                                <p>如发现任何未经授权使用您账户的情况，您应立即通知我们。您同意，若您提供的注册信息不真实、不准确或不完整，我们有权暂停或终止您的账户，并拒绝您使用我们的服务。</p>
                            </section>

                            <!-- 4.使用规则 -->
                            <section class="mb-6">
                                <h2 class="layui-h2 font-semibold mb-3 text-gray-800 layui-flex layui-items-center">
                                    <i class="layui-icon layui-icon-close text-primary mr-2"></i>
                                    4. 使用规则
                                </h2>
                                <p class="mb-3">您在使用我们的服务时，不得从事以下行为：</p>
                                <ul class="layui-list layui-list-disc pl-6 space-y-2 mb-3">
                                    <li>违反国家法律法规的规定；</li>
                                    <li>侵犯他人的知识产权、名誉权、隐私权等合法权益；</li>
                                    <li>传播或发布淫秽、色情、暴力、恐怖、赌博等有害信息；</li>
                                    <li>从事任何危害计算机网络安全的行为，包括但不限于：病毒、木马、恶意程序等；</li>
                                    <li>其他我们认为不适当的行为。</li>
                                </ul>
                                <p>您同意，如您违反上述使用规则，我们有权采取包括但不限于暂停或终止您的账户、删除您发布的内容等措施，且无需承担任何责任。</p>
                            </section>

                            <!-- 5.知识产权 -->
                            <section class="mb-6">
                                <h2 class="layui-h2 font-semibold mb-3 text-gray-800 layui-flex layui-items-center">
                                    <i class="layui-icon layui-icon-copyright text-primary mr-2"></i>
                                    5. 知识产权
                                </h2>
                                <p class="mb-3">我们提供的服务中包含的所有内容（包括但不限于文字、图片、音频、视频、软件、技术等）的知识产权均归我们或我们的 licensors 所有。您在使用我们的服务时，仅获得有限的使用权，不得擅自复制、传播、修改、展示或用于其他商业目的。</p>
                                <p>您在使用我们的服务过程中上传、发布的内容，其知识产权归您所有，但您授予我们全球范围内、免费、非独家的使用权，包括但不限于复制、传播、展示、修改等权利。</p>
                            </section>

                            <!-- 6.隐私保护 -->
                            <section class="mb-6">
                                <h2 class="layui-h2 font-semibold mb-3 text-gray-800 layui-flex layui-items-center">
                                    <i class="layui-icon layui-icon-shield text-primary mr-2"></i>
                                    6. 隐私保护
                                </h2>
                                <p>我们尊重并保护您的隐私。关于您的个人信息的收集、使用、存储和保护，请参阅我们的《隐私政策》。您同意，我们可按照《隐私政策》的规定处理您的个人信息。</p>
                            </section>

                            <!-- 7.免责声明 -->
                            <section class="mb-6">
                                <h2 class="layui-h2 font-semibold mb-3 text-gray-800 layui-flex layui-items-center">
                                    <i class="layui-icon layui-icon-exclamation-triangle text-primary mr-2"></i>
                                    7. 免责声明
                                </h2>
                                <p class="mb-3">我们提供的服务按"现状"提供，我们不对服务的可用性、可靠性、及时性作出任何明示或暗示的保证。</p>
                                <p>在法律允许的范围内，我们不对因使用或无法使用我们的服务所造成的任何直接的、间接的、偶然的、特殊的或后果性的损失（包括但不限于利润损失、数据丢失等）承担责任。</p>
                            </section>

                            <!-- 8.协议的修改和终止 -->
                            <section class="mb-6">
                                <h2 class="layui-h2 font-semibold mb-3 text-gray-800 layui-flex layui-items-center">
                                    <i class="layui-icon layui-icon-refresh text-primary mr-2"></i>
                                    8. 协议的修改和终止
                                </h2>
                                <p class="mb-3">我们有权随时修改本协议。修改后的协议将在我们的网站上公布，并自公布之日起生效。您应定期查阅本协议的最新版本。如您继续使用我们的服务，即视为您已接受修改后的协议。</p>
                                <p>您可随时停止使用我们的服务，终止本协议。我们也有权根据实际情况，随时终止本协议，暂停或终止您的账户，且无需提前通知您，但会在合理范围内尽可能通知您。</p>
                            </section>

                            <!-- 9.法律适用和争议解决 -->
                            <section class="mb-6">
                                <h2 class="layui-h2 font-semibold mb-3 text-gray-800 layui-flex layui-items-center">
                                    <i class="layui-icon layui-icon-gavel text-primary mr-2"></i>
                                    9. 法律适用和争议解决
                                </h2>
                                <p class="mb-3">本协议的订立、效力、解释、履行及争议的解决均适用中华人民共和国法律。</p>
                                <p>因本协议引起的或与本协议有关的任何争议，双方应首先通过友好协商解决；协商不成的，任何一方均有权向我们所在地有管辖权的人民法院提起诉讼。</p>
                            </section>

                            <!-- 10.其他 -->
                            <section class="mb-6">
                                <h2 class="layui-h2 font-semibold mb-3 text-gray-800 layui-flex layui-items-center">
                                    <i class="layui-icon layui-icon-about text-primary mr-2"></i>
                                    10. 其他
                                </h2>
                                <p class="mb-3">本协议构成您与我们之间关于使用我们服务的完整协议，取代先前的任何口头或书面协议。</p>
                                <p class="mb-3">如本协议的任何条款被认定为无效或不可执行，不影响其他条款的效力。</p>
                                <p>如您对本协议有任何疑问，请联系我们的客服团队。</p>
                            </section>
                        </div>

                    </div>
                </main>

            </body>
              `,
          });
        },
      });
      form.render();

      // 监听表单提交
      form.on("submit(demo-reg)", function (data) {
        // 表单验证通过，可以提交数据
        console.log("表单数据：", data.field);

        // 自定义密码验证规则
        form.verify({
          pass: [/^[\S]{6,12}$/, "密码必须6到12位，且不能出现空格"],
          // 验证两次密码是否一致
          confirmPass: function (value) {
            const password = document.getElementById("reg-password").value;
            if (value !== password) {
              return "两次输入的密码不一致";
            }
          },
        });
        //TODO提交前检测完密码还要检测名字以及是否勾选了用户协议
        
        // 阻止默认提交行为
        return false;
      });
      //TODO提交注册表单
    });
  },
};
</script>

<style scoped>
/* 注册页面整体样式 */
.register-page {
  width: 100vw;
  height: 100vh;
  background: linear-gradient(135deg, #4361ee 0%, #3a0ca3 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
}

/* 注册容器样式 */
.demo-reg-container {
  width: 380px;
  padding: 40px 35px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(31, 38, 135, 0.15);
  backdrop-filter: blur(8px);
  border: 1px solid rgba(255, 255, 255, 0.18);
  position: relative;
  z-index: 1;
}

/* 注册标题样式 */
.register-title {
  text-align: center;
  margin-bottom: 30px;
}

.register-title h2 {
  font-size: 24px;
  color: #2d3748;
  margin-bottom: 8px;
  font-weight: 600;
}

/* 输入框样式优化 */
.input-item {
  border-radius: 8px !important;
  overflow: hidden;
  transition: all 0.3s ease;
  position: relative;
}

.input-item .layui-input {
  height: 48px;
  border: none;
  background-color: #f7fafc;
  font-size: 15px;
  padding-left: 50px;
  width: 100%;
  box-sizing: border-box;
}

.input-item .layui-input-prefix {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  display: flex;
  align-items: center;
}

.input-item .layui-icon {
  font-size: 20px;
  color: #a0aec0;
}

/* 协议勾选样式 */
.agreement-item {
  margin-top: 15px;
  margin-bottom: 25px;
  display: flex;
  align-items: center;
}

.agreement-item .layui-form-checkbox {
  margin-top: 0;
  margin-right: 8px;
}

.agreement-link {
  margin: 9px 0 0 -10px;
  font-size: 14px;
  color: #4361ee;
  transition: color 0.3s ease;
}

.agreement-link:hover {
  color: #3a0ca3;
  text-decoration: underline;
}

/* 注册按钮样式 */
.register-btn {
  height: 48px;
  border-radius: 8px;
  background: linear-gradient(135deg, #4361ee 0%, #3a0ca3 100%);
  border: none;
  font-size: 16px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.register-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(67, 97, 238, 0.3);
  background: linear-gradient(135deg, #3a56d4 0%, #320997 100%);
}

/* 已有账号登录链接样式 */
.demo-reg-other {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
  color: #4a5568;
}

.demo-reg-other a {
  color: #4361ee;
  margin-left: 5px;
  font-weight: 500;
  transition: color 0.3s ease;
}

.demo-reg-other a:hover {
  color: #3a0ca3;
  text-decoration: underline;
}

/* 适配移动端 */
@media (max-width: 480px) {
  .demo-reg-container {
    width: 90%;
    padding: 30px 25px;
  }

  .register-title h2 {
    font-size: 22px;
  }

  .input-item .layui-input {
    height: 44px;
  }

  .register-btn {
    height: 44px;
  }
}
</style>