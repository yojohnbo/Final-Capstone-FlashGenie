<template>
  <div id="login" class="text-center">
    <img id="logo" src="/flash-genie-logo.png" alt="" />

      <form class="form-signin" @submit.prevent="login">
        <h1 id="sign-in">Please Sign In</h1>
        
        <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
          Invalid username and password!
        </div>

        <div
          class="alert alert-success"
          role="alert"
          v-if="this.$route.query.registration">Thank you for registering, please sign in.
        </div>

        <label for="username" class="sr-only"></label>
          <input
            type="text"
            id="username"
            class="form-control"
            placeholder="Username"
            v-model="user.username"
            required
            autofocus/>

        <label for="password" class="sr-only"></label>
          <input
            type="password"
            id="password"
            class="form-control"
            placeholder="Password"
            v-model="user.password"
            required/>

        <div id="login-buttons">
          <router-link :to="{ name: 'register' }" id="router-account" class='button'>Register</router-link>
          <button id="sign-in" type="submit">Sign in</button>
        </div>
      </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";


export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },

      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push('/');
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>

div#login {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0px 50px 50px 50px;
  padding-top: 70px;
}

img#logo {
  width: 350px;
  height: auto;
  margin: 40px 0px 5px 0px;
  padding: 0px;
}

.form-signin {
  display: flex;
  flex-direction: column;
  width: 375px;

}

h1#sign-in {
  font-family: monospace;
  text-align: left;
  margin-left: 3px;
  margin-top: 50px;
  font-size: large;
  margin-bottom: 15px;
}

input#username,
input#password {
  font-family: monospace;
  margin-bottom: 20px;
  padding: 12px;
  border-radius: 8px;
  font-size: larger;
  border-color: #05445e;
  background-color: rgba(241, 241, 241, 0.959);
}

input#username:hover,
input#password:hover {
  background-color: white;
}

/* original css 041722 EOD */
div#login-buttons {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  column-gap: 25px;
}

button#sign-in {
  background-color: #f8f9d2;
  background-image: linear-gradient(315deg, 
  #d2e6f9 0%, #6b8fa7 74%);
  border: solid #747474 1px; 
  width: 150px;
  height: 50px;
  margin: 10px 10px 20px 10px;
  display: inline-block;
  padding: 8px;
  text-decoration: none;
  letter-spacing: 1.75px;
  text-align: center;
  font-size: 14px;
  font-family: monospace;
  border-radius: 12px;
  font-weight: bold;
  border: none;
  color: white;
  margin-bottom: 100px;
  box-shadow: 5px 5px 18px 0px rgba(0,0,0,0.7);
  -webkit-box-shadow: 5px 5px 18px 0px rgba(0,0,0,0.7);
  -moz-box-shadow: 5px 5px 18px 0px rgba(0,0,0,0.7);
}

a#router-account.button {
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-decoration: none;
  color: white;
  padding-top: 0px; 
}

a#router-account.button {
  width: 150px;
  height: 50px;
  margin: 10px 10px 20px 10px;
  text-decoration: none;
  letter-spacing: 1.75px;
  text-align: center;
  font-size: 14px;
  font-family: monospace;
  border-radius: 12px;
  font-weight: bold;
  background-color: #f8f9d2;
  background-image: linear-gradient(315deg, 
  #d2e6f9 0%, #6b8fa7 74%);
  text-decoration: none;
  color: white;
  margin-bottom: 100px;
  box-shadow: 5px 5px 18px 0px rgba(0,0,0,0.7);
  -webkit-box-shadow: 5px 5px 18px 0px rgba(0,0,0,0.7);
  -moz-box-shadow: 5px 5px 18px 0px rgba(0,0,0,0.7);
}

button#sign-in:hover, button#sign-in:focus {
  text-decoration: none;
  background-image: linear-gradient(315deg, 
  #dbec8e 0%, #d8b30e 85%);
  box-shadow: inset 0 0 0 2em var(--hover);
  color:#09203f;
  font-weight: bold;
  cursor: pointer; 
}

a#router-account.button:hover, a#router-account.button:focus {
  text-decoration: none;
  background-image: linear-gradient(315deg, 
  #dbec8e 0%, #d8b30e 85%);
  box-shadow: inset 0 0 0 2em var(--hover);
  color:#09203f;
  font-weight: bold;
  cursor: pointer; 
}
</style>
