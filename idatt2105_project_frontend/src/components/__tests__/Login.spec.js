import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import {useRouter} from "vue-router";
import Login from '../Login.vue'
import { createPinia } from 'pinia'

const router = useRouter();
const pinia = createPinia();

describe('Login', () => {
  it('displays the email and password input boxes', () => {
    const wrapper = mount(Login, {
      global: {
        plugins: [router, pinia],
      }
    },)

    const emailInput = wrapper.find('#email')
    expect(emailInput.exists()).toBe(true)
    const passwordInput = wrapper.find('#password')
    expect(passwordInput.exists()).toBe(true)
  })

  it('displays login button', () => {
    const wrapper = mount(Login, {
      global: {
        plugins: [router, pinia],
      }
    },)

    const loginBtn = wrapper.find('#loginBtn')
    expect(loginBtn.exists()).toBe(true)
  })

  it('displays link to register page', () => {
    const wrapper = mount(Login, {
      global: {
        plugins: [router, pinia],
      }
    },)

    const registerLink = wrapper.find('#registerLink')
    expect(registerLink.exists()).toBe(true)
  })
})

