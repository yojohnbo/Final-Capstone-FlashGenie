<template>
  <div class="container">
    <div id="site-nav" v-if="$store.state.token != ''">
      <nav id="site-nav">
        <ul id="site-nav">
          <li>
            <router-link :to="{ name: 'home' }"
              ><img id="logo" src="/flash-genie-logo.png"
            /></router-link>
          </li>

          <div id="search-home">
            <li id="search-bar">
              <label for="search"></label>
              <input
                id="search"
                name="search"
                placeholder="SEARCH"
                type="text"
                v-model="searchInput"
                v-on:keyup.enter="submitSearch(searchInput)"
              />
            </li>
            <li id="home-logout">
              <router-link v-bind:to="{ name: 'home' }">Home</router-link
              ><img id="pipe-bolt" src="/bolt.png" alt="" /><router-link
                id="logout"
                v-bind:to="{ name: 'logout' }"
              >
                Logout</router-link
              >
            </li>
          </div>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchInput: "",
    };
  },
  methods: {
    //sends user to search results page
    submitSearch(searchInput) {
      if (searchInput !== "") {
        this.searchInput = "";
        this.$router.push(`/search/${searchInput}`);
      }
    },
  },
};
</script>

<style scoped>
nav ul {
  display: flex;
  flex-direction: row;
  list-style: none;
  justify-content: space-between;
  margin: 0px;
  align-items: center;
  padding-left: 0px;
  color: yellow;
  border: double 6px transparent;
  border-radius: 15px;
  background-origin: border-box;
  background-clip: content-box, border-box;
  background-color: #537895;
  background-image: linear-gradient(315deg, #09203f 0%, #bed1eb 74%);
}

div#search-home {
  display: flex;
  column-gap: 10px;
  justify-content: center;
  align-items: center;
  text-transform: uppercase;
  font-family: monospace;
  letter-spacing: 1px;
  font-size: 15px;
  font-weight: bold;
}

a {
  padding-top: 5px;
  text-decoration: none;
  color: #ebeb85;
}

a:hover {
  color: yellow;
}

a#logout {
  padding-right: 20px;
  padding-left: 10px;
}

#search {
  width: 260px;
  border: 3px solid #ccc;
  border-radius: 5px;
  background-color: rgba(241, 241, 241, 0.959);
  padding: 5px;
  box-sizing: border-box;
  resize: vertical;
  font-family: monospace;
  letter-spacing: 1px;
  margin-right: 12px;
}

#search:hover {
  background-color: white;
}

a:hover {
  font-weight: bold;
}

img#logo {
  width: 90px;
  height: auto;
  padding: 10px 0px 10px 20px;
}

img#pipe-bolt {
  width: 10px;
  padding: 0px 0px 0px 10px;
  margin: 0px;
}

li#home-logout {
  display: flex;
  justify-content: center;
}
</style>