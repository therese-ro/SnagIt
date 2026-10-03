<script setup>
import {computed, onMounted, onUnmounted, ref} from "vue";
import {useUserStore} from "@/stores/userStore.js";
import {useRoute, useRouter} from "vue-router";
import axios from 'axios'

const router = useRouter();
const route = useRoute();

const userStore = useUserStore();

const token = computed(() => userStore.getToken());
const id = ref(route.params.id);

const item = ref();
const image = ref();
const forSaleOrSold = ref();
const postcode = ref();
const place = ref();

const saleCondition = () => {
  if (item.value.isForSale === true) {
    forSaleOrSold.value = "For sale"
  } else {
    forSaleOrSold.value = "Sold";
  }
}

const loadItem = async () => {
  const response = await axios.get(`http://localhost:8080/listing/get/${id.value}`, {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });
  item.value = response.data;
  await getImage();
  await saleCondition();
  await getPostcodeAndCity();
}

//Method created with help from ChatGPT on using responsetype blob, as well as URL.createObjectURL for displaying the image. Equivalent method is used in multiple places in the code
const getImage = async () => {
  const response = await axios.get(`http://localhost:8080/image/get/${item.value.thumbnailPath}`, {
    headers: {
      Authorization: `Bearer ${token.value}`
    },
    responseType: 'blob'
  });

  image.value = URL.createObjectURL(response.data)
}

const getPostcodeAndCity = async () => {
  const response = await axios.get(`https://nominatim.openstreetmap.org/reverse?lat=${item.value.latitude}&lon=${item.value.longitude}&format=json`);

  postcode.value = response.data.address.postcode;
  if (!response.data.address.city && !response.data.address.village) {
    place.value = response.data.address.county;
  } else if (!response.data.address.city) {
    place.value = response.data.address.village;
  } else {
    place.value = response.data.address.city;
  }
}

const handleEditListing = (id) => {
  router.push(`/editlisting/${id}`);
}

onMounted(() => {
  loadItem();
});

//Recommendation from ChatGPT to use URL.revokeObjectURL to prevent memory leaks because of createObjectURL. This code has been reused in multiple places in the code
onUnmounted(() => {
  if (image.value) {
    URL.revokeObjectURL(image.value)
  }
})
</script>

<template>
  <div id="wrapper" v-if="item">
    <div id="itemPictureContainer">
      <img :src="image" alt="Picture of item for sale" id="itemPicture">
    </div>
    <div  id="itemInfoContainer">
      <h2>{{ item.shortDesc }}</h2>
      <h4>Price</h4>
      <h3>{{ item.price }} kr</h3>
      <div id="saleAndEditDiv">
        <h3 id="isForSaleDisplay">{{ forSaleOrSold }}</h3>
        <button v-if="token && item.isSeller === true" id="editListingBtn" @click="handleEditListing(item.itemID)">Edit</button>
      </div>
      <br>
      <hr>
      <br>
      <h4>Seller</h4>
      <h5>{{ item.sellerDisplayName }}</h5>
      <h5>Location: {{ postcode }} {{ place }}</h5>
      <h5>Contact: {{ item.sellerEmail }}</h5>
      <hr>
      <br>
      <h5>Category: {{ item.category.categoryName}}</h5>
      <h5>Date listed: {{ item.createdAt }}</h5>

    </div>
    <div id="itemDescContainer">
      <h5>Description</h5>
      <p>{{ item.longDesc }}</p>
    </div>
    <div >

    </div>
  </div>
</template>

<style scoped>

#wrapper {
  width: 100%;
  display: grid;
  grid-template-areas:
    'picture info'
    'description info';
  padding: 10px;
}

#itemPictureContainer {
  grid-area: picture;
  display: flex;
  margin: 10px;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  justify-content: center;
  align-items: center;
}

#itemPicture {
  padding: 10px;
  object-fit: contain;
}

#editListingBtn {
  padding: 5px 10px;
  font-size: 14px;
  font-weight: bold;
  color: white;
  border: 1px solid #46648e;
  border-radius: 3px;
  background-color: #5a769c;
}

#editListingBtn:hover {
  cursor: pointer;
  background-color: #456288;
}

#itemPictureContainer, #itemPicture {
  min-width: 500px;
  max-width: 500px;
  min-height: 500px;
  max-height: 500px;
}

#itemInfoContainer {
  grid-area: info;
  margin: 10px;
  padding: 20px;
  min-width: 300px;
  //background-color: #99c8dc;
  background-color: white;
  border-radius: 10px;
}

#itemDescContainer {
  grid-area: description;
  padding: 20px;
  margin: 10px;
  background-color: white;
  border-radius: 10px;
}

#saleAndEditDiv {
  display: flex;
  justify-content: space-between;
}

#isForSaleDisplay {
  padding: 5px;
  border: 1px solid #5097b1;
  color: #456288;
  border-radius: 3px;
  max-width: 100px;
}

#itemPictureContainer, #itemDescContainer {
  max-width: 500px;
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

@media (max-width: 900px) {
  #wrapper {
    grid-template-areas:
    'picture'
    'info'
    'description';
  }
}

</style>
