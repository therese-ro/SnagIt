import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import {useRouter} from "vue-router";
import Register from '../Register.vue'
import { createPinia } from 'pinia'

const router = useRouter();
const pinia = createPinia();

describe('Register', () => {
  it('displays the display name, email and password input boxes', () => {
    const wrapper = mount(Register, {
      global: {
        plugins: [router, pinia],
      }
    },)

    const displaynameInput = wrapper.find('#displayName')
    expect(displaynameInput.exists()).toBe(true)
    const emailInput = wrapper.find('#email')
    expect(emailInput.exists()).toBe(true)
    const passwordInput = wrapper.find('#password')
    expect(passwordInput.exists()).toBe(true)
  })

  it('displays register button', () => {
    const wrapper = mount(Register, {
      global: {
        plugins: [router, pinia],
      }
    },)

    const registerBtn = wrapper.find('#registerBtn')
    expect(registerBtn.exists()).toBe(true)
  })
})

