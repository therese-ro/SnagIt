<script setup>
import {computed, onMounted, ref} from "vue";
import {useUserStore} from "@/stores/userStore.js";
import {useRouter} from "vue-router";
import axios from 'axios';

const userStore = useUserStore();
const router = useRouter();

const token = computed(() => userStore.getToken());

const loadUser = async () => {
  const response = await axios.get("http://localhost:8080/user/getInfo", {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });

  user.value = response.data;
  displayName.value = response.data.displayName;
  email.value = response.data.email;
}

const user = ref();
const displayName = ref();
const email = ref();
const oldPassword = ref();
const newPassword = ref();
const newPasswordAgain = ref();

const handleNameChange = async () => {
  const response = await axios.put("http://localhost:8080/user/updateDisplayName", {
    displayName: displayName.value
  }, {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });

  alert(response.data.status);
  router.go(0);
}

const handleEmailChange = async () => {
  const response = await axios.put("http://localhost:8080/user/updateEmail", {
    email: email.value
  }, {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });

  alert(response.data.status);
  router.go(0);
}

const handlePasswordChange = async () => {
  const response = await axios.put("http://localhost:8080/user/updatePassword", {
    oldPassword: oldPassword.value,
    newPassword: newPassword.value,
    newPasswordAgain: newPasswordAgain.value
  }, {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });

  alert(response.data.status);
  router.go(0);
}

onMounted(() => {
  loadUser();
})

</script>

<template>
<h3 class="headingLabel">Edit profile</h3>
<div id="wrapper" v-if="user">
  <form @submit.prevent="handleNameChange">
    <label for="displayname" class="headingLabel">Change display name</label>
    <br>
    <input type="text" id="displayname" v-model="displayName" required >
    <button type="submit" class="submitBtn" id="nameBtn">Change display name</button>
  </form>
  <hr>
  <form @submit.prevent="handleEmailChange">
    <label for="email" class="headingLabel">Change e-mail</label>
    <br>
    <input type="email" id="email" v-model="email" required />
    <button type="submit" class="submitBtn" id="mailBtn">Change e-mail</button>
  </form>
  <hr>
  <form @submit.prevent="handlePasswordChange">
    <label class="headingLabel">Change password </label><br>
    <label for="oldPassword">Old password </label>
    <input type="password" id="oldPassword" v-model="oldPassword" required />
    <br>
    <label for="newPassword">New password </label>
    <input type="password" id="newPassword" v-model="newPassword" required />
    <br>
    <label for="newPasswordCheck">Repeat new password </label>
    <input type="password" id="newPasswordAgain" v-model="newPasswordAgain" required />
    <br>
    <button type="submit" class="submitBtn">Change password</button>
  </form>
</div>
</template>

<style scoped>
#wrapper {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: 10px;
}

.headingLabel {
  font-weight: bold;
}

.submitBtn {
  padding: 5px;
  margin-bottom: 5px;
  font-weight: bold;
  color: white;
  border: none;
  border-radius: 3px;
  background-color: #71b6cf;
}

#nameBtn, #mailBtn {
  margin-left: 10px;
}

.submitBtn:hover {
  cursor: pointer;
  background-color: #5097b1;
}
</style>
