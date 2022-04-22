<template>
  <div id="register" class="text-center">
    <img id="logo" src="/flash-genie-logo.png" alt=""/>
    
      <form class="form-register" @submit.prevent="register">
        <h1 id="h1-register">Create Account</h1>

      <label for="email" class="sr-only"></label>
        <input
          type="email"
          id="email"
          class="form-control"
          placeholder="Email"
          v-model="userDetails.email"
          required
          autofocus/>

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
        
        <input
          type="password"
          id="confirmPassword"
          class="form-control"
          placeholder="Confirm Password"
          v-model="user.confirmPassword"
          required/>

        <div class="alert alert-danger" id="alert" role="alert" v-if="registrationErrors">
          {{ registrationErrorMsg }}
        </div>

        <div id="register-buttons">
          <router-link :to="{ name: 'login' }" id="router-account" class='button'>Have an account?</router-link>
          <button id="sign-in" type="submit">Create Account</button>
        </div>
      </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';
import userDetailsService from '../services/UserDetailsService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      userDetails: {
        email: ''
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      }else if (!this.validate()){
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Please enter a valid email address.'
      }
      
      else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              userDetailsService.create(this.userDetails, this.user.username);
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Username already exists. Please select another one.';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
    validate(){
      return (this.userDetails.email.includes('@') && this.userDetails.email.includes('.'));
    }
  },
};
</script>

<style scoped> 

div#register {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0px 50px 50px 50px;
}

img#logo {
  width: 350px;
  height: auto;
  margin: 40px 0px 5px 0px;
  padding: 0px;
}

form.form-register {
  display: flex;
  flex-direction: column;
  width: 375px;
}

h1#h1-register {
  font-family: monospace;
  text-align: left;
  margin-left: 3px;
  margin-top: 50px;
  font-size: large;
  margin-bottom: 15px;
}

input#username,
input#email, 
input#password, 
input#confirmPassword {
  font-family: monospace;
  margin-bottom: 20px;
  padding: 12px;
  border-radius: 8px;
  font-size: larger;
  border-color: #05445e;
  background-color: rgba(241, 241, 241, 0.959);
}

input#username:hover,
input#email:hover, 
input#password:hover, 
input#confirmPassword:hover {
  background-color: white;
}

div#alert {
  font-size: x-small;
  text-align: center;
  margin-bottom: 7px;
}

div#register-buttons {
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
  padding: 8px 12px 8px 12px;
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
  color: white;
  padding-top: 0px; 
  width: 150px;
  height: 50px;
  margin: 10px 10px 20px 10px;
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
