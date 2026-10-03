<script setup>
import {ref} from "vue";
import router from "@/router/index.js";
import {useUserStore} from "@/stores/userStore.js";

const userStore = useUserStore();

const displayName = ref('');
const email = ref('');
const password = ref('');

const handleRegister = async () => {
  await userStore.register(displayName.value, email.value, password.value);
  if (userStore.jwtToken) {
    router.push('/');
  }
}
</script>

<template>
  <div id="registerContainer">
    <h2>New user</h2>
    <form @submit.prevent="handleRegister" id="registerForm">
      <label for="displayName">Display name</label>
      <br>
      <input type="text" id="displayName" v-model="displayName" required />
      <br>
      <label for="email">E-mail</label>
      <br>
      <input type="email" id="email" v-model="email" required />
      <br>
      <label for="password">Password</label>
      <br>
      <input type="password" id="password" v-model="password" required />
      <br>
      <button id="registerBtn" type="submit">Register</button>
      <p>Already have an account? <a href="/login" id="loginLink">Log in</a></p>
    </form>
  </div>
</template>

<style scoped>
#registerContainer {
  width: 100%;
  display: grid;
  padding: 20px;
  //border: 1px solid black;
  background-color: white;
  border-radius: 10px;
}

#registerBtn {
  padding: 5px;
  margin: 5px 0;
  font-size: 12px;
  font-weight: bold;
  color: white;
  border: none;
  border-radius: 3px;
  background-color: #71b6cf;
}

#registerBtn:hover {
  cursor: pointer;
  background-color: #5097b1;
}
</style>
