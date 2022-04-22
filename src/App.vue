<template>

  <div id="app">
  <nav-bar></nav-bar>
    <router-view />
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'

export default {
  components: {
    NavBar,
  },
}
</script>


<style scoped>
div#app {
  background-color: #b8c6db;
  background-image: linear-gradient(315deg, #b8c6db 0%, #f5f7fa 74%);
  font-family: monospace;
  background-size: cover, contain;
  padding: 0;
  margin: -8px !important;
    min-height: 100vh;
}

</style>
