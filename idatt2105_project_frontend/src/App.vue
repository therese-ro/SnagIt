<script setup>
import { RouterView } from 'vue-router'
import Header from './components/Header.vue'
</script>

<template>
  <div class="wrapper">
    <Header></Header>

    <RouterView />
  </div>
</template>

<style scoped>
.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

RouterView {
  width: 100%;
  margin: 20px;
}

@media (min-width: 1024px) {

}
</style>
