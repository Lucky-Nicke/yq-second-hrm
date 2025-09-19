<template>
  <div class="welcome-content">
    <div class="welcome-message">
      <h3>很高兴再次见到您！</h3>
      <p>今天是 {{ todayDate }}, 希望您有美好的一天</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      actions: [
        { name: "个人中心", icon: "fa-user" },
        { name: "消息通知", icon: "fa-bell" },
        { name: "系统设置", icon: "fa-cog" },
        { name: "帮助中心", icon: "fa-question-circle" },
      ],
    };
  },
  computed: {
    todayDate() {
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        weekday: "long",
      };
      return new Date().toLocaleDateString("zh-CN", options);
    },
  },
};
</script>

<style scoped>
.welcome-content {
  margin-top: 20px;
  background-color: white;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.welcome-message {
  margin-bottom: 30px;
  padding-bottom: 15px;
  border-bottom: 1px solid #f0f0f0;
}

.welcome-message h3 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 18px;
}

.welcome-message p {
  margin: 0;
  color: #666;
  font-size: 14px;
}

.quick-actions h4 {
  margin: 0 0 15px 0;
  color: #333;
  font-size: 16px;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 15px;
}

.action-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 15px 10px;
  background-color: #f9f9f9;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s ease;
  width: 100%;
}

.action-btn:hover {
  background-color: #f0f0f0;
  transform: translateY(-2px);
}

.action-btn i {
  font-size: 24px;
  color: #42b983;
  margin-bottom: 8px;
}

.action-btn span {
  font-size: 14px;
  color: #333;
}
</style>
