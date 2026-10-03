<script setup>
import {computed, onMounted, ref} from "vue";
import { useRouter } from "vue-router";
import axios from 'axios'
import ListingCard from "@/components/ListingCard.vue";
import {useUserStore} from "@/stores/userStore.js";

const router = useRouter();
const userStore = useUserStore();

const token = computed(() => userStore.getToken());

const listings = ref([]);
const pageSize = 12;
const currentPage = ref(1);
const isLastPage = ref();

const loadListings = async () => {
  const response = await axios.get("http://localhost:8080/listing/user", {
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

const handleClick = (id) => {
  router.push(`/item/${id}`);
}

const handlePageChange = (newPage) => {
  currentPage.value = newPage;
  loadListings();
}

onMounted(() => {
  loadListings();
});

</script>

<template>
  <h3>My listings</h3>
  <div id="listingGridContainer">
    <button v-for="listing in listings" :key="listing.itemID" class="listingCard" @click="handleClick(listing.itemID)">
      <ListingCard :listing="listing" />
    </button>
  </div>
  <div id="pageButtonContainer">
    <button class="pageBtn" :disabled="currentPage === 1" @click="handlePageChange(currentPage - 1)">Previous</button>
    <button class="pageBtn" :disabled="isLastPage === true" @click="handlePageChange(currentPage + 1)">Next</button>
  </div>

</template>

<style scoped>
#listingGridContainer {
  grid-area: listings;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

#pageButtonContainer {
  display: flex;
  justify-content: space-between;
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

h3 {
  font-weight: bold;
}

.pageBtn {
  padding: 5px;
  margin-bottom: 5px;
  font-size: 12px;
  font-weight: bold;
  color: white;
  border: none;
  border-radius: 3px;
  background-color: #71b6cf;
}

.pageBtn:hover {
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

@media (max-width: 850px) {
  #listingGridContainer {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
