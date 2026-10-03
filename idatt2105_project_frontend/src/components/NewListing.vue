<script setup>
import {computed, onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import {useUserStore} from "@/stores/userStore.js";
import axios from 'axios'

const router = useRouter();
const userStore = useUserStore();

const token = computed(() => userStore.getToken());
const categories = ref([]);

const fileInput = ref();
const itemName = ref();
const price = ref();
const postcode = ref();
const category = ref();
const description = ref();

const handleCreateListing = async () => {
  const location = await getLatitudeAndLongitude(postcode.value)
  const latitude = location.lat;
  const longitude = location.lon;

  const imagePath = await saveImage();

  const response = await axios.post("http://localhost:8080/listing/addNew", {
      shortDesc: itemName.value,
      longDesc: description.value,
      price: price.value,
      longitude: longitude,
      latitude: latitude,
      thumbnailPath: imagePath,
      categoryID: category.value
  }, {
    headers: {
      Authorization: `Bearer ${token.value}`}
  });

  alert(response.data.status);
  await router.push(`/mypage/mylistings`);
}

const getLatitudeAndLongitude = async (postcode) => {
  const response = await axios.get(`https://nominatim.openstreetmap.org/search?q=${postcode}&countrycodes=no&format=json&limit=1`);
  return response.data[0];
}

const saveImage = async () => {
  const formData = new FormData();
  const file = fileInput.value.files[0];
  formData.append("file", file);

  try {
    const response = await axios.post("http://localhost:8080/image/save", formData, {
      headers: {
        Authorization: `Bearer ${token.value}`,
        'Content-Type': 'multipart/form-data'
      }
    });
    return response.data.filename;
  } catch (error) {
    console.log(error);
  }
}

const handleCancel = () => {
  router.back();
}

const loadCategories = async () => {
  const response = await axios.get("http://localhost:8080/category/allCategories", {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });

  categories.value = response.data;
}

onMounted(() => {
  loadCategories();
})

</script>

<template>
  <form @submit.prevent="handleCreateListing">
    <div id="wrapper">
      <div id="itemPictureContainer">
        <h3>Upload image</h3>
        <input type="file" ref="fileInput" @change="previewImage" accept="image/*" required/>
      </div>
      <div id="itemInfoContainer">
        <h3>Item info</h3>
        <label for="itemName">Item name </label>
        <input type="text" id="itemName" v-model="itemName" required>
        <br>
        <label for="price">Price </label>
        <input type="text" id="price" v-model="price" required>
        <br>
        <label for="postcode">Postcode: </label>
        <input type="text" id="postcode" v-model="postcode" required>
        <br>
        <label for="category">Category </label>
        <select id="category" v-model="category" required>
          <option v-for="category in categories" :key="category.categoryID" :value="category.categoryID">
            {{ category.categoryName }}
          </option>
        </select>
        <br>
        <label for="description">Description </label>
        <br>
        <textarea id="description" v-model="description" required/>
        <br>
        <button type="submit" id="createBtn">Create listing</button>
        <button type="button" id="cancelBtn" @click="handleCancel">Cancel</button>
      </div>
    </div>
  </form>
</template>

<style scoped>

#wrapper {
  width: 100%;
  display: grid;
  grid-template-areas:
    'title title'
    'picture info';
}

#itemPictureContainer {
  grid-area: picture;
  margin: 10px;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  justify-content: center;
  align-items: center;
}

#itemPictureContainer{
  min-width: 300px;
  max-width: 300px;
}

#itemPictureContainer, #itemInfoContainer {
  min-height: 200px;
  max-height: 400px;
}

#itemInfoContainer {
  grid-area: info;
  margin: 10px;
  padding: 20px;
  min-width: 300px;
  max-width: 300px;
  background-color: white;
  border-radius: 10px;
}

#description {
  max-height: 150px;
  min-height: 50px;
  max-width: 250px;
  min-width: 250px;
}


h2, h3, h4, h5 {
  color: #233750;
  font-weight: bold;
  padding-bottom: 10px;
}

hr {
  border: none;
  height: 1px;
  background-color: #b1b1b1;
}

#createBtn, #cancelBtn {
  padding: 5px;
  font-size: 12px;
  font-weight: bold;
  color: white;
  border: none;
  border-radius: 3px;
  background-color: #71b6cf;
  margin: 5px 5px 5px 0;
}

#createBtn {
  background-color: #71b6cf;
}

#cancelBtn {
  background-color: #e4e4e4;
  color: #222222;
}

#createBtn:hover {
  cursor: pointer;
  background-color: #5097b1;
}

#cancelBtn:hover {
  cursor: pointer;
  background-color: #b1b1b1;
  color: #222222;
}


@media (max-width: 720px) {
  #wrapper {
    grid-template-areas:
    'title'
    'picture'
    'info';
  }
}
</style>
