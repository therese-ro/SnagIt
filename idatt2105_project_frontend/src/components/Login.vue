<script setup>
import {ref} from "vue";
import {useUserStore} from "@/stores/userStore.js";
import router from "@/router/index.js";

const userStore = useUserStore();

const email = ref('');
const password = ref('');

const handleLogin = async () => {
  await userStore.login(email.value, password.value);
  if (userStore.jwtToken) {
    router.push('/');
  }
}
</script>

<template>
  <div id="loginContainer">
    <h2>Login page</h2>
    <form @submit.prevent="handleLogin" id="loginForm">
      <label for="email">E-mail</label>
      <br>
      <input type="email" id="email" v-model="email" required />
      <br>
      <label for="password">Password</label>
      <br>
      <input type="password" id="password" v-model="password" required />
      <br>
      <button id="loginBtn" type="submit">Log in</button>
    </form>
    <p>Don't have an account yet? <a href="/register" id="registerLink">Sign up</a></p>
  </div>
</template>

<style scoped>
#loginContainer {
  width: 100%;
  display: grid;
  padding: 20px;
  //border: 1px solid black;
  background-color: white;
  border-radius: 10px;
}

#loginBtn {
  padding: 5px;
  margin: 5px 0;
  font-size: 12px;
  font-weight: bold;
  color: white;
  border: none;
  border-radius: 3px;
  background-color: #71b6cf;
}

#loginBtn:hover {
  cursor: pointer;
  background-color: #5097b1;
}
</style>
