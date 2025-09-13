<template>
  <div>
    <component
      :is="currentComponent"
      v-if="currentComponent"
      @toReg="showRegFrom"
      @toLogin="showLoginFrom"
      @toCPaw="showChangePwdFrom"
    ></component>
  </div>
</template>

<script>
import ForgetPasswordForm from "@/components/ForgetPasswordForm.vue";
import LoginForm from "@/components/LoginForm.vue";
import RegisterForm from "@/components/RegisterForm.vue";

export default {
  name: "LoginView",
  data() {
    return {
      currentComponent: LoginForm,
    };
  },
  components: {
    LoginForm,
    RegisterForm,
    ForgetPasswordForm,
  },
  methods: {
    showRegFrom() {
      this.currentComponent = RegisterForm;
    },
    showLoginFrom() {
      this.currentComponent = LoginForm;
    },
    showChangePwdFrom() {
      this.currentComponent = ForgetPasswordForm;
    },
  },
};
</script>

<style>
</style>