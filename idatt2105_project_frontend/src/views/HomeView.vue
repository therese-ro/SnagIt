<script setup>
import {computed, onMounted, ref} from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore.js";
import axios from 'axios'
import ListingCard from "@/components/ListingCard.vue";

const userStore = useUserStore();
const router = useRouter();

const token = computed(() => userStore.getToken());

const listings = ref([]);
const categories = ref([]);
const pageSize = 6;
const currentPage = 1;

const loadListings = async () => {
  const response = await axios.get("http://localhost:8080/listing/get", {
    headers: {
      Authorization: `Bearer ${token.value}`
    },
    params: {
      pagesize: pageSize,
      page: currentPage
    }
  });

  listings.value = response.data.listings;
}

const handleClick = (id) => {
  router.push(`/item/${id}`);
}

const loadCategories = async () => {
  const response = await axios.get("http://localhost:8080/category/allCategories", {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });

  categories.value = response.data;
}

const handleCategFilter = (categoryID) => {
  router.push(`/explore?filter=${categoryID}`);
}

onMounted(() => {
  loadListings();
  loadCategories();
});
</script>

<template>
  <div id="wrapper">
    <h2 id="categoryTitle">Explore categories</h2>
    <div id="categoryContainer">
      <button v-for="category in categories" :key="category.categoryID" class="categoryBtn"
              @click="handleCategFilter(category.categoryID)">{{ category.categoryName }}</button>
    </div>

    <h2 id="listingTitle">Explore new listings</h2>
    <div id="listingGridContainer">
      <button v-for="listing in listings" :key="listing.itemID" class="listingCard" @click="handleClick(listing.itemID)">
        <ListingCard :listing="listing" />
      </button>
    </div>
  </div>
</template>

<style scoped>
  #wrapper {
    display: grid;
    grid-template-areas:
      'catTitle'
      'categories'
      'listTitle'
      'listings';
  }

  #categoryTitle {
    grid-area: catTitle;
    padding-bottom: 7px;
  }

  #listingTitle {
    grid-area: listTitle;
    padding-bottom: 7px;
  }

  #categoryContainer {
    grid-area: categories;
    display: grid;
    gap: 12px;
    grid-template-columns: repeat(6, 1fr);
    padding-bottom: 15px;
  }

  #listingGridContainer {
    grid-area: listings;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
    padding-bottom: 15px;

  }

  .listingCard {
    background-color: white;
    border: none;
    border-radius: 3px;
    padding: 10px;
  }

  .listingCard:hover {
    cursor: pointer;
    background-color: #cee5ed;
  }

  .categoryBtn {
    padding: 5px 10px;
    width: 100%;
    font-size: 14px;
    font-weight: bold;
    color: white;
    border: none;
    border-radius: 3px;
    background-color: #71b6cf;
  }

  .categoryBtn:hover {
    cursor: pointer;
    background-color: #5097b1;
  }

  @media (max-width: 750px) {
    #categoryContainer {
      grid-template-columns: repeat(2, 1fr);
    }

    #listingGridContainer {
      grid-template-columns: repeat(2, 1fr);
    }
  }
</style>
