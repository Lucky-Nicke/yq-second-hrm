<template>
  <header class="user-info-header">
    <div class="avatar-container">
      <div class="avatar">
        <span>{{ username.charAt(0) }}</span>
      </div>
    </div>
    <div class="user-details">
      <h2>欢迎回来，{{ loginName }}！</h2>
      <p>账号：{{ loginName }}</p>
      <p>账号创建时间：{{ formattedCreateDate }}</p>
    </div>
  </header>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      loginName: '',
      createDate: ''
    };
  },
  computed: {
    // 格式化日期显示
    formattedCreateDate() {
      if (!this.createDate) return '未知时间';
      
      // 尝试解析日期
      const date = new Date(this.createDate);
      if (isNaN(date.getTime())) return this.createDate;
      
      // 格式化日期为 yyyy-mm-dd hh:MM
      return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
    }
  },
  mounted() {
    // 从本地存储读取用户信息
    this.username = localStorage.getItem('username') || '亲爱的用户';
    this.loginName = localStorage.getItem('loginName') || 'user';
    this.createDate = localStorage.getItem('createdate') || '';
  }
};
</script>

<style scoped>
.user-info-header {
  display: flex;
  align-items: center;
  background-color: #42b983;
  color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.avatar-container {
  margin-right: 20px;
}

.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  font-weight: bold;
}

.user-details h2 {
  margin: 0 0 10px 0;
  font-size: 22px;
}

.user-details p {
  margin: 5px 0;
  opacity: 0.9;
}
</style>
