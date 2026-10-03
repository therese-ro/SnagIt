<script setup>
import {computed, onMounted, onUnmounted, ref} from "vue";
import {useUserStore} from "@/stores/userStore.js";
import {useRoute, useRouter} from "vue-router";
import axios from 'axios'

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

const token = computed(() => userStore.getToken());
const id = ref(route.params.id);

const categories = ref([]);

const fileInput = ref();
const item = ref();
const image = ref();
const isSold = ref();
const postcode = ref();
const longitude = ref();
const latitude = ref();

const loadItem = async () => {
  const response = await axios.get(`http://localhost:8080/listing/get/${id.value}`, {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });
  item.value = response.data;

  if (!item.value.isSeller) {
    alert("You are not the seller of this item and not allowed to edit, you will be returned to the home page.")
    await router.push("/");
  } else {
    await getImage();
    await saleCondition();
    await getPostcodeAndCity();
  }
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

const loadCategories = async () => {
  const response = await axios.get("http://localhost:8080/category/allCategories", {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });

  categories.value = response.data;
}

const getPostcodeAndCity = async () => {
  const response = await axios.get(`https://nominatim.openstreetmap.org/reverse?lat=${item.value.latitude}&lon=${item.value.longitude}&format=json`);

  postcode.value = response.data.address.postcode;
}

const setLatitudeAndLongitude = async () => {
  const response = await axios.get(`https://nominatim.openstreetmap.org/search?q=${postcode.value}&countrycodes=no&format=json&limit=1`);
  latitude.value = response.data[0].lat;
  longitude.value = response.data[0].lon;
}

const saleCondition = () => {
  if (!item.value.isForSale) {
    isSold.value = true;
  }
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

const handleUpdateListing = async () => {
  if (fileInput.value.files[0]) {
    item.value.thumbnailPath = await saveImage();
  }

  if (isSold.value === true) {
    item.value.isForSale = false;
  } else {
    item.value.isForSale = true;
  }

  await setLatitudeAndLongitude();
  const response = await axios.put("http://localhost:8080/listing/update", {
    itemID: item.value.itemID,
    shortDesc: item.value.shortDesc,
    longDesc: item.value.longDesc,
    price: item.value.price,
    longitude: longitude.value,
    latitude: latitude.value,
    isForSale: item.value.isForSale,
    categoryID: item.value.category.categoryID,
    thumbnailPath: item.value.thumbnailPath
  }, {
    headers: {
      Authorization: `Bearer ${token.value}`}
  });
  alert(response.data.status);
  await router.push(`/item/${item.value.itemID}`);
}

const handleCancel = () => {
  router.back();
}

//Usage of window.confirm recommended from ChatGPT to make the user delete or cancel based on their wishes.
const confirmDelete = async () => {
  if (window.confirm("Are you sure you want to delete this item?")) {
    const response = await axios.delete(`http://localhost:8080/listing/delete/${id.value}`, {
      headers: {
        Authorization: `Bearer ${token.value}`}
    });

    alert(response.data.status);
    router.push('/mypage/mylistings');
  }
};

onMounted(() => {
  loadItem();
  loadCategories();
});

//Recommendation from ChatGPT to use URL.revokeObjectURL to prevent memory leaks because of createObjectURL. This code has been reused in multiple places in the code
onUnmounted(() => {
  if (image.value) {
    URL.revokeObjectURL(image.value)
  }
});

</script>

<template>
  <form @submit.prevent="handleUpdateListing">
    <div id="wrapper" v-if="item">
      <h2 id="pageTitle">Edit listing</h2>
      <div id="itemPictureContainer">
        <h3>Current image</h3>
        <div id="savedImgPreview">
          <img :src="image" alt="Picture of item for sale" style="max-width: 300px; min-width: 300px;">
        </div>
        <h3>New image</h3>
        <input type="file" ref="fileInput" accept="image/*"/>
      </div>
      <div id="itemInfoContainer">
        <h3>Item info</h3>
        <label for="itemName">Item name </label>
        <input type="text" id="itemName" v-model="item.shortDesc" required>
        <br>
        <label for="price">Price </label>
        <input type="text" id="price" v-model="item.price" required>
        <br>
        <label for="isSold">Is item sold? </label>
        <input type="checkbox" v-model="isSold">
        <br>
        <hr>
        <label for="location">Postcode: </label>
        <input type="text" id="location" v-model="postcode" required>
        <br>
        <label for="category">Category </label>
        <select id="category" v-model="item.category.categoryID" required>
          <option v-for="category in categories" :key="category.categoryID" :value="category.categoryID">
            {{ category.categoryName }}
          </option>
        </select>
        <br>
        <label for="description">Description </label>
        <br>
        <textarea id="description" v-model="item.longDesc" required></textarea>
        <br>
        <button type="submit" id="updateBtn">Update listing</button>
        <button type="button" id="cancelBtn" @click="handleCancel">Cancel</button>
        <br>
        <button type="button" id="deleteBtn" @click="confirmDelete">Delete listing</button>
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
  padding: 10px;
}

#pageTitle {
  grid-area: title;
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

#itemPictureContainer {
  min-width: 400px;
  max-width: 400px;
  min-height: 400px;
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
  margin: 3px;
}

#updateBtn, #cancelBtn, #deleteBtn {
  padding: 5px;
  font-size: 12px;
  font-weight: bold;
  color: white;
  border: none;
  border-radius: 3px;
  background-color: #71b6cf;
  margin: 5px 5px 5px 0;
}

#updateBtn {
  background-color: #71b6cf;
}

#cancelBtn {
  background-color: #e4e4e4;
  color: #222222;
}

#deleteBtn {
  border: 1px groove #8e2a2a;
  background-color: #985656;
}

#updateBtn:hover {
  cursor: pointer;
  background-color: #5097b1;
}

#cancelBtn:hover {
  cursor: pointer;
  background-color: #b1b1b1;
  color: #222222;}

#deleteBtn:hover {
  cursor: pointer;
  background-color: #8e2a2a;
}

@media (max-width: 790px) {
  #wrapper {
    grid-template-areas:
    'title'
    'picture'
    'info';
  }

  #itemInfoContainer {
    max-width: 400px;
    min-width: 400px;
  }

  #description {
    max-width: 100%;
  }
}
</style>
