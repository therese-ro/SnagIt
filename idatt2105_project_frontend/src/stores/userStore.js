import { defineStore } from 'pinia'

import { getJwtTokenFromLogin, getJwtTokenAfterRegistration } from '../utils/authService'

export const useUserStore = defineStore('token', {
  state: () => ({
    jwtToken: '',
  }),

  persist: {
    storage: sessionStorage,
  },

  actions: {
    async register(displayName, email, password){
      try {
        await getJwtTokenAfterRegistration(displayName, email, password).then((token) => {
          if (token) {
            this.jwtToken = token;
          } else {
            console.log('Failed to retrieve token');
          }

        })
      } catch(error) {
        console.log('Error: ' + error);
      }
    },

    async login(email, password) {
      try {
        await getJwtTokenFromLogin(email, password).then((token) => {
          if (token) {
            this.jwtToken = token;
          } else {
            console.log('Failed to retrieve token');
          }
        })
      } catch(error) {
        console.log('Error: ' + error);
      }
    },

    getToken() {

      let isExpired = this.isTokenExpired();
      if(isExpired) {
        this.logout()
      }

      return isExpired ? '' : this.jwtToken;
    },

    logout() {
      this.jwtToken = '';
    },
    isTokenExpired() {
      if (!this.jwtToken) {
        return true;
      }

      const payload = JSON.parse(atob(this.jwtToken.split('.')[1]));
      //console.log('Token expiration date: ' + payload.exp);
      //console.log('Time left of token ' + (payload.exp - Date.now() / 1000));
      return payload.exp < Date.now() / 1000;
    }
  }
})
