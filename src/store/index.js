import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    decks: [],
    cards:[],
    allCards:[],
    currentScore: 0,
    maxScore: 0,
    isRandomized: false,
    shuffledDeck: [],
    timerEnabled: true,
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_DECKS(state, data){
      state.decks = data;
    },
    SET_CARDS_BY_DECK_ID(state, data){
      state.cards = data;
    },
    SET_ALL_CARDS(state, data){
      state.allCards = data;
    },
    UPDATE_ALL_CARDS(state, card){
      state.allCards.push(card)
    },
    SET_SCORE(state, score){
      state.currentScore = score;
    },
    SET_MAX_SCORE(state,score){
      state.maxScore = score;
    },
    SET_RANDOMIZED(state){
      state.isRandomized = true;
    },
    SET_NOT_RANDOMIZED(state){
      state.isRandomized = false;
    },
    SHUFFLE_DECK(state, deck){
      state.shuffledDeck = deck
    },
    ENABLE_COUNTDOWN(state){
      state.timerEnabled = true;
    },
    DISABLE_COUNTDOWN(state){
      state.timerEnabled = false;
    }
  }
})
