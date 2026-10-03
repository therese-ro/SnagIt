import axios from 'axios'

export async function getJwtTokenFromLogin(email, password) {
  try {
    const response = await axios.post("http://localhost:8080/auth/login", {
      email: email,
      password: password
    });

    const data = await response.data;
    return data.token;
  } catch (error) {
    console.log('Error: ' + error);
    return null
  }
}

export async function getJwtTokenAfterRegistration(displayName, email, password) {
  try {
    const response = await axios.post("http://localhost:8080/auth/register", {
      displayName: displayName,
      email: email,
      password: password
    });
    const data = await response.data;
    return data.token;
  } catch (error) {
    console.log('Error: ' + error);
    return null
  }
}
