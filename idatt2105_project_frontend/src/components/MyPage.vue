<script setup>
import {computed, onMounted, ref} from "vue";
import router from "@/router/index.js";
import {useUserStore} from "@/stores/userStore.js";
import axios from 'axios';

const userStore = useUserStore();
const token = computed(() => userStore.getToken());

const user = ref();

const loadUser = async () => {
  const response = await axios.get("http://localhost:8080/user/getInfo", {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });

  user.value = response.data;
}

const handleLogOut = () => {
  userStore.logout();
  router.push('/')
}

onMounted(() => {
  loadUser();
});

</script>

<template>
  <div id="wrapper" v-if="user">
    <div id="titleContainer">
      <h2>My Page</h2>
    </div>
    <div id="userInfoContainer">
      <h3>{{ user.displayName }}</h3>
      <h3>{{ user.email }}</h3>
      <button id="logoutBtn" @click="handleLogOut">Log out</button>
    </div>
    <div id="menuContainer">
      <router-link to="/mypage/mylistings"><button class="menuBtn">My listings</button></router-link>
      <br>
      <router-link to="/mypage/editprofile"><button class="menuBtn">Edit profile</button></router-link>
    </div>
    <div id="menuChoiceContainer">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped>

#wrapper {
  width: 100%;
  display: grid;
  grid-template-areas:
    'logo userInfo'
    'menu menuChoice';
  padding: 10px;
}

#titleContainer, #menuContainer {
  min-width: 250px;
  max-width: 250px;
}

#titleContainer {
  grid-area: logo;
  display: flex;
  margin: 10px;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  justify-content: center;
  align-items: center;
}

#userInfoContainer {
  grid-area: userInfo;
  margin: 10px;
  padding: 20px;
  min-width: 700px;
  //background-color: #99c8dc;
  background-color: white;
  border-radius: 10px;
}

#menuContainer {
  grid-area: menu;
  margin: 10px;
  padding: 20px;
  //background-color: #99c8dc;
  background-color: white;
  border-radius: 10px;
}

#menuChoiceContainer {
  grid-area: menuChoice;
  margin: 10px;
  padding: 20px;
  min-width: 300px;
  //background-color: #99c8dc;
  background-color: white;
  border-radius: 10px;
}

h2, h3, h4, h5 {
  color: #233750;
  font-weight: bold;
  padding-bottom: 10px;
}

hr {
  border: none;
  height: 1px;
  background-color: gray;
}

.menuBtn, #logoutBtn {
  padding: 5px 10px;
  width: 100%;
  margin-bottom: 5px;
  font-size: 14px;
  font-weight: bold;
  color: white;
  border: none;
  border-radius: 3px;
  background-color: #71b6cf;
}

#logoutBtn {
  width: 100px;
}

.menuBtn:hover, #logoutBtn:hover {
  cursor: pointer;
  background-color: #5097b1;
}

@media (max-width: 1120px) {
  #wrapper {
    grid-template-areas:
    'logo menu'
    'userInfo userInfo'
    'menuChoice menuChoice';
    margin-right: 20px;
    margin-left: -5px;
  }

  #userInfoContainer, #menuChoiceContainer {
    min-width: 200px;
    max-width: 800px;
  }
}

</style>
