<script setup>
import { RouterLink } from 'vue-router'
import {useUserStore} from "@/stores/userStore.js";
import {computed} from "vue";

const userStore = useUserStore();

//Method created with help from ChatGPT on using computed to keep tabs on the token. Variations of this code, especially for token checks, might be reused and/or edited to fit in other parts of the program as well.
const isLoggedIn = computed(() => userStore.getToken());

</script>

<template>
  <header>
    <RouterLink to="/" class="logoLink">SnagIt</RouterLink>
    <nav>
      <RouterLink to="/newlisting" class="navLink">List an item</RouterLink>
      <RouterLink to="/explore" class="navLink">Explore</RouterLink>
      <!--Using v-if and v-else was implemented as a reccomendation from ChatGPT. Using v-if has then been used in other parts of code as well.-->
      <RouterLink v-if="!isLoggedIn" to="/login" class="navLink" >Log in</RouterLink>
      <RouterLink v-else to="/mypage" class="navLink">My Page</RouterLink>
    </nav>
  </header>
</template>

<style scoped>
header {
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid lightgray;
  background-color: #71b6cf;
  padding: 5px;
}

.logoLink {
  color: white;
  text-decoration: none;
  font-weight: bolder;
  font-size: 24px;
  letter-spacing: 1px;
  padding-left: 10px;
}

.navLink {
  color: white;
  text-decoration: none;
  border-radius: 3px;
  padding: 7px;
  margin: 10px
}

.navLink:hover {
  background-color: #5097b1;
}
</style>
