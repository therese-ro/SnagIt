import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from "@/stores/userStore.js";
import HomeView from '../views/HomeView.vue'
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import ItemView from "@/views/ItemView.vue";
import ExploreView from "@/views/ExploreView.vue";
import MyPageView from "@/views/MyPageView.vue";
import MyListings from "@/components/MyListings.vue";
import UserEditProfile from "@/components/UserEditProfile.vue";
import NewListingView from "@/views/NewListingView.vue";
import EditListingView from "@/views/EditListingView.vue";

//Got help from ChatGPT on how to implement children for a route, so they appear after /mypage in the url.

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView,
    },
    {
      path: '/item/:id',
      name: 'item',
      component: ItemView,
    },
    {
      path: '/explore',
      name: 'explore',
      component: ExploreView,
    },
    {
      path: '/newlisting',
      name: 'newlisting',
      component: NewListingView,
      meta: { requiresAuth: true },
    },
    {
      path: '/editlisting/:id',
      name: 'editlisting',
      component: EditListingView,
      meta: { requiresAuth: true },
    },
    {
      path: '/mypage',
      redirect: '/mypage/mylistings',
      name: 'mypage',
      component: MyPageView,
      meta: { requiresAuth: true },
      children: [
        {
          path: 'mylistings',
          name: 'mylistings',
          component: MyListings,
          meta: { requiresAuth: true },
        },
        {
          path: 'editprofile',
          name: 'editprofile',
          component: UserEditProfile,
          meta: { requiresAuth: true },
        },
      ],
    },
  ],
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore();

  if(to.meta.requiresAuth && userStore.isTokenExpired()) {
    alert('You need to be logged in to access this page! Please log in!')
    next('/login')
  } else {
    next()
  }
})

export default router
