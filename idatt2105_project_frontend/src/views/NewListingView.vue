<script setup>

import NewListing from "@/components/NewListing.vue";
</script>

<template>
  <div id="wrapper">
    <h2 id="pageTitle">Create new listing</h2>
    <NewListing />
  </div>
</template>

<style scoped>

#wrapper {
  width: 100%;
  display: grid;
  grid-template-areas:
    'title title'
    'picture info';
  padding: 10px;
}

#pageTitle {
  grid-area: title;
}

h2 {
  color: #233750;
  font-weight: bold;
}

</style>
