<script setup>
import {computed, onMounted, onUnmounted, ref} from "vue";
import axios from 'axios'
import {useUserStore} from "@/stores/userStore.js";

const userStore = useUserStore();

const token = computed(() => userStore.getToken());

const image = ref();

//Code directly from ChatGPT for how to use props for getting listing from component that uses ListingCard
const { listing } = defineProps({
  listing: {
    type: Object,
    required: true
  }
});

//Method created with help from ChatGPT on using responsetype blob, as well as URL.createObjectURL for displaying the image. Equivalent method is used in multiple places in the code
const getImage = async () => {
  const response = await axios.get(`http://localhost:8080/image/get/${listing.thumbnailPath}`, {
    headers: {
      Authorization: `Bearer ${token.value}`
    },
    responseType: 'blob'
  });

  image.value = URL.createObjectURL(response.data)
}

onMounted(() => {
  getImage();
});

//Recommendation from ChatGPT to use URL.revokeObjectURL to prevent memory leaks because of createObjectURL. This code has been reused in multiple places in the code
onUnmounted(() => {
  if (image.value) {
    URL.revokeObjectURL(image.value)
  }
})
</script>

<template>
    <img :src="image" :alt="listing.shortDesc" class="listingImage">
    <h3 class="listingTitle">{{ listing.shortDesc }}</h3>
    <p class="listingPrice">{{ listing.price }} kr</p>
</template>

<style scoped>

.listingImage {
  min-width: 200px;
  max-width: 200px;
  min-height: 200px;
  max-height: 200px;
  padding-bottom: 10px;
  object-fit: contain;
}

.listingTitle {
  font-weight: bold;
}

</style>
