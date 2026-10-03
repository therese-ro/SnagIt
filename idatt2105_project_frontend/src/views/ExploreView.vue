<script setup>
import {computed, onMounted, ref, watch} from "vue";
import { useRouter, useRoute } from "vue-router";
import { useUserStore } from "@/stores/userStore.js";
import axios from 'axios'
import ListingCard from "@/components/ListingCard.vue";

const userStore = useUserStore();
const router = useRouter();
const route = useRoute();

const token = computed(() => userStore.getToken());
const filter = ref(route.query.filter);

const listings = ref([]);
const categories = ref([]);
const pageSize = 12;
const currentPage = ref(1);
const isLastPage = ref();


const loadListings = async () => {
  const response = await axios.get("http://localhost:8080/listing/get", {
    headers: {
      Authorization: `Bearer ${token.value}`
    },
    params: {
      pagesize: pageSize,
      page: currentPage.value
    }
  });

  listings.value = response.data.listings;
  isLastPage.value = response.data.includesLastListing;
}

const loadFilteredListings = async () => {
  const response = await axios.get(`http://localhost:8080/listing/category/${filter.value}`, {
    headers: {
      Authorization: `Bearer ${token.value}`
    },
    params: {
      pagesize: pageSize,
      page: currentPage.value
    }
  });

  listings.value = response.data.listings;
  isLastPage.value = response.data.includesLastListing;
}

const loadCategories = async () => {
  const response = await axios.get("http://localhost:8080/category/allCategories", {
    headers: {
      Authorization: `Bearer ${token.value}`
    }
  });

  categories.value = response.data;
}


const handleClick = (id) => {
  router.push(`/item/${id}`);
}

const handleFilter = () => {
  router.push(`/explore?filter=${filter.value}`);
}

const clearFilter = () => {
  router.push("/explore");
}

//Made with help from ChatGPT
watch(() => route.query.filter, async (newFilter) => {
  if (newFilter) {
    await loadFilteredListings();
  } else {
    await loadListings();
  }
});

const handlePageChange = (newPage) => {
  currentPage.value = newPage;
  loadListings();
}

onMounted(() => {
  if (!filter.value) {
    loadListings();
  } else {
    loadFilteredListings();
  }
  loadCategories();
});

</script>

<template>
  <div id="wrapper">
    <h2 id="listingTitle">Explore active listings</h2>
    <div id="filterContainer">
      <h3>Filter by category</h3>
      <select id="category" v-model="filter" required>
        <option v-for="category in categories" :key="category.categoryID" :value="category.categoryID">
          {{ category.categoryName }}
        </option>
      </select>
      <br>
      <button class="filterBtn" @click="handleFilter">Use filter</button>
      <br>
      <button class="filterBtn" id="clearFilterBtn" @click="clearFilter">Clear filter</button>

    </div>
    <div id="listingGridContainer">
      <button v-for="listing in listings" :key="listing.itemID" class="listingCard" @click="handleClick(listing.itemID)">
        <ListingCard :listing="listing" />
      </button>
    </div>
    <div id="pageButtonContainer">
      <button class="pageBtn" :disabled="currentPage === 1" @click="handlePageChange(currentPage - 1)">Previous</button>
      <button class="pageBtn" :disabled="isLastPage === true" @click="handlePageChange(currentPage + 1)">Next</button>
    </div>
  </div>
</template>

<style scoped>
#wrapper {
  display: grid;
  grid-template-areas:
      'listTitle listTitle'
      'filter listings'
      'filter pageBtn';
  gap: 20px;
  padding-bottom: 40px;
}

#listingTitle {
  grid-area: listTitle;
}

#pageButtonContainer {
  grid-area: pageBtn;
  display: flex;
  justify-content: space-between;
}

#filterContainer {
  grid-area: filter;
  padding: 10px;
  border-radius: 5px;
  background-color: white;
  max-height: 500px;
}

#listingGridContainer {
  grid-area: listings;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.listingCard {
  padding: 10px;
  background-color: white;
  border: none;
  border-radius: 5px;
  min-width: 250px;
  max-width: 250px;
  min-height: 275px;
  max-height: 275px;
  overflow: hidden;
}

.listingCard:hover {
  cursor: pointer;
  background-color: #cee5ed;
}

.filterBtn, .pageBtn {
  padding: 5px;
  margin-bottom: 5px;
  font-size: 12px;
  font-weight: bold;
  color: white;
  border: none;
  border-radius: 3px;
  background-color: #71b6cf;
}

.filterBtn:hover, .pageBtn:hover {
  cursor: pointer;
  background-color: #5097b1;
}

.pageBtn:disabled {
  cursor: default;
  background-color: #818181;
}
.pageBtn:disabled:hover {
   cursor: default;
   background-color: #818181;
 }

#clearFilterBtn {
  background-color: #d3d3d3;
  color: #222222;
}

#clearFilterBtn:hover {
  cursor: pointer;
  background-color: #9a9a9a;
  color: #222222;}


@media (max-width: 1000px) {
  #listingGridContainer {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 750px) {
  #listingGridContainer {
    grid-template-columns: repeat(1, 1fr);
  }
}
</style>
