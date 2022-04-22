<template>
  <div class="cardsPage">
    <div class="cards-container" v-if="!isLoading">
      <div id="title-links">
        <div class="header">
          <div class="my-cards">
            <img id="bolt" src="/bolt.png" alt="" />
            <h3>cards about {{ currentDeck.name }}</h3>
            <img id="bolt" src="/bolt.png" alt="" />
          </div>

          <div class="subtitle">
            <h4>subject: {{ currentDeck.subject }}</h4>
          </div>

          <div class="study-session">
            <router-link
              v-if="cards.length > 0"
              class="study"
              :to="{ name: 'study-session', params: { id: currentDeckId } }"
              @click.native="regularSession"
              >Begin Study Session</router-link
            >

            <router-link
              v-if="cards.length > 0"
              class="random-study"
              :to="{ name: 'study-session', params: { id: currentDeckId } }"
              @click.native="randomizedSession"
              >Begin Randomized Study Session</router-link
            >

            <router-link
              v-if="cards.length === 0"
              class="study"
              :to="{ name: 'new-card', params: { id: this.currentDeckId } }"
              >You need cards for a study session.</router-link
            >
          </div>
        </div>
      </div>

      <div class="cards">
        <div
          class="card"
          v-for="card in cards.slice(0, displayLength)"
          v-bind:key="card.cardId"
        >
          <div id="flex-text">
            <div class="flip-card">
              <div class="flip-card-inner">
                <div class="flip-card-front">
                  <div class="row-gap">
                    <div class="front-card-text">
                      {{ card.frontText }}
                    </div>

                    <div id="edit">
                      <router-link
                        class="edit-card"
                        :to="{
                          name: 'edit-card',
                          params: { deckId: card.deckId, cardId: card.cardId },
                        }"
                        >Details/Edit</router-link
                      >
                    </div>
                  </div>
                </div>
                <div class="flip-card-back">
                  <div class="back-card-text">
                    {{ card.backText }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <router-link
          :to="{ name: 'new-card', params: { id: currentDeckId } }"
          class="addCard addCardDetails"
          >+</router-link
        >
      </div>

      <div id="button">
        <button
          id="show-all"
          v-if="deckSize > minDisplayLength"
          @click="partialDisplay = !partialDisplay"
        >
          {{ partialDisplay ? "Show All" : "Show Less" }}
        </button>
      </div>

      <div class="back-edit">
        <router-link class="back-to-decks" :to="{ name: 'home' }"
          >Back to Decks</router-link
        >

        <router-link
          class="edit-deck"
          :to="{ name: 'edit-deck', params: { id: currentDeckId } }"
          >Edit Deck</router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
import cardService from "@/services/CardService";
import deckService from "@/services/DeckService";
export default {
  name: "cardsPage",
  data() {
    return {
      getCount: 0,
      minDisplayLength: 11,
      partialDisplay: true,
      deckId: 0,
      active: false,
    };
  },
  computed: {
    decks() {
      return this.$store.state.decks;
    },
    deckSize() {
      return this.$store.state.cards.length;
    },
    displayLength() {
      if (this.partialDisplay) {
        return 11;
      } else {
        return this.$store.state.cards.length;
      }
    },
    cards() {
      return this.$store.state.cards;
    },
    currentDeckId() {
      return Number(this.$route.params.id);
    },
    currentDeck() {
      return this.decks.find((deck) => deck.deckId == this.currentDeckId);
    },
    isLoading() {
      return this.getCount !== 3;
    },
  },
  methods: {
    getCards() {
      cardService.getAllCardsByDeckId(this.currentDeckId).then((response) => {
        this.$store.commit("SET_CARDS_BY_DECK_ID", response.data);
        this.getCount++;
      });
    },
    randomizedSession() {
      this.$store.commit("SET_RANDOMIZED");
    },
    regularSession() {
      this.$store.commit("SET_NOT_RANDOMIZED");
    },
    getDecks() {
      deckService.getAllDecks().then((response) => {
        this.$store.commit("SET_DECKS", response.data);
        this.getCount++;
      });
    },
    getAllCards() {
      cardService.getAllCards().then((response) => {
        this.$store.commit("SET_ALL_CARDS", response.data);
        this.getCount++;
      });
    },
  },
  created() {
    this.getDecks();
    this.getAllCards();
    this.getCards();
  },
};
</script>

<style scoped>
.cardsPage {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0;
  margin: 0;
  width: 100%;
  min-height: 100vh;
  user-select: none;
  -moz-user-select: none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  -o-user-select: none;
}

div#title-links {
  display: flex;
  flex-direction: column;
}

.cards-container {
  background-color: #537895;
  background-image: linear-gradient(315deg, #537895 0%, #09203f 74%);
  justify-content: center;
  min-width: 600px;
  max-width: 980px;
  border-radius: 30px;
  margin: 30px 0px 0px 0px;
  padding: 40px 30px 20px 30px;
  width: 75%;
}
div.my-cards {
  display: flex;
  text-align: center;
  justify-content: center;
  align-items: center;
  color: white;
  vertical-align: middle;
  margin-top: 10px;
}

img#bolt {
  width: 30px;
  opacity: 0.85;
  padding: 0px;
  margin: 0px;
}

h3 {
  color: white;
  font-size: 45px;
  text-align: center;
  text-transform: uppercase;
  letter-spacing: 1.75px;
  margin: 0px 15px 0px 15px;
  line-height: 0.85;
}

h4 {
  margin-top: auto;
}

hr {
  width: 150px;
  color: #ebeb85;
}

.cards {
  display: flex;
  flex-wrap: wrap;
  margin: 30px 0px 30px 0px;
  justify-content: center;
}

.card {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  border: none;
  color: rgb(0, 0, 0);
  text-align: center;
  text-decoration: none;
  border-radius: 10px;
  justify-content: center;
  font-size: larger;
}
.addCard {
  display: flex;
  flex-wrap: wrap;
  width: 150px;
  height: 50px;
  align-items: center;
  padding: 15px;
  margin: 15px;
  margin-left: 15px;
  border: none;
  color: rgb(0, 0, 0);
  text-align: center;
  text-decoration: none;
  border-radius: 10px;
  justify-content: center;
  font-size: larger;
}

.card:hover {
  cursor: pointer;
}

.addCard {
  background-color: white;
  border: solid #bdbdbd 1px;
  box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  -webkit-box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  -moz-box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
}

.addCard:hover {
  background-color: rgb(236, 231, 231);
}

.addCardDetails {
  font-size: 3em;
  font-weight: 1000;
  color: rgb(95, 95, 95);
}

.study-session {
  display: flex;
  justify-content: center;
  align-items: center;
}

a.study,
a.random-study {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: #f8f9d2;
  background-image: linear-gradient(315deg, #d2e6f9 0%, #6b8fa7 74%);
  border: solid #747474 1px;
  width: 200px;
  height: 30px;
  margin: 10px 15px 0px 10px;
  padding: 12px;
  text-decoration: none;
  letter-spacing: 1.75px;
  text-align: center;
  font-size: 14px;
  font-family: monospace;
  border-radius: 12px;
  font-weight: bold;
  border: none;
  color: white;
  box-shadow: 5px 5px 18px 0px rgba(0, 0, 0, 0.7);
  -webkit-box-shadow: 5px 5px 18px 0px rgba(0, 0, 0, 0.7);
  -moz-box-shadow: 5px 5px 18px 0px rgba(0, 0, 0, 0.7);
}

.study:hover,
.random-study:hover,
.study:focus,
.random-study:focus {
  text-decoration: none;
  background-image: linear-gradient(315deg, #dbec8e 0%, #d8b30e 85%);
  box-shadow: inset 0 0 0 2em var(--hover);
  color: #09203f;
  font-weight: bold;
  cursor: pointer;
}

div#flex-text {
  display: flex;
  flex-direction: column;
  width: 100%;

  line-height: 20px;
}

div#card-title {
  text-transform: uppercase;
  padding: 10px;
  font-size: 16px;
  text-align: center;
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin: 0px;
}

a.card-name,
a {
  text-decoration: none;
  color: black;
}

a.card-name:visited {
  color: black;
}

div#card-title {
  display: flex;
  justify-content: center;
}

div#edit {
  display: flex;

  justify-content: center;

  vertical-align: bottom;
}

a.edit-card {
  font-size: 10px;
  text-align: center;
  padding: 0px;
  color: rgb(134, 134, 134);
  margin-top: 3px;
  user-select: none;
  -moz-user-select: none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  -o-user-select: none;
}

a.edit-card:hover {
  color: black;
}

div#button {
  display: flex;
  justify-content: right;
}

button#show-all {
  font-family: monospace;
  letter-spacing: 1px;
  width: 100px;
  height: 40px;
  border-radius: 10px;
  margin-top: 15px;
  margin-bottom: 20px;
  background-color: #ffffff;
  border: none;
  color: #09203f;
  box-shadow: 5px 5px 18px 0px rgba(0, 0, 0, 0.7);
  -webkit-box-shadow: 5px 5px 18px 0px rgba(0, 0, 0, 0.7);
  -moz-box-shadow: 5px 5px 18px 0px rgba(0, 0, 0, 0.7);
}

button#show-all:hover,
button#show-all:focus {
  text-decoration: none;
  color: #09203f;
  box-shadow: inset 0 0 0 2em var(--hover);
}

button#show-all:hover,
button#show-all:focus {
  color: #09203f;
  cursor: pointer;
  box-shadow: inset 0 0 0 2em var(--hover);
}
div.back-edit {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  column-gap: 20px;
  margin-bottom: 0px;
}

a.back-to-decks,
a.edit-deck {
  color: #ebeb85;
  text-transform: uppercase;
  letter-spacing: 1px;
  padding: 0px;
}

a.back-to-decks:hover,
a.edit-deck:hover {
  color: yellow;
}

.header {
  display: flex;
  flex-direction: column;
}

.subtitle {
  display: flex;
  text-align: center;
  justify-content: center;
  align-items: center;
  color: white;
  text-transform: uppercase;
}

/* CARD FLIP ACTION CSS */
.flip-card {
  background-color: transparent;
  width: 182px;
  height: 82px;
  perspective: 1000px;
  margin: 15px;
}

/* This container is needed to position the front and back side */
.flip-card-inner {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.8s;
  transform-style: preserve-3d;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 180px;
  height: 80px;
  flex: 1;
}

/* Do an horizontal flip when you move the mouse over the flip box container */
.flip-card:active .flip-card-inner {
  transform: rotateY(180deg);
}

/* Position the front and back side */
.flip-card-front,
.flip-card-back {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  width: 100%;
  height: 100%;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
  border: none;
  text-align: center;
  border-radius: 10px;
  padding: 15px;
  margin: 15px;
}

/* Style the front side (fallback if image is missing) */
.flip-card-front {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #f8f9d2;
  background-image: linear-gradient(315deg, #e6e9a1 0%, #c6b1e6 74%);
  border: solid #bdbdbd 1px;
  box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  -webkit-box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  -moz-box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  font-size: x-large;
  letter-spacing: 1.75px;
  font-weight: bold;
  width: 150px;
  height: 50px;
}

.front-card-text {
  text-transform: uppercase;
  font-size: 16px;
  user-select: none;
  -moz-user-select: none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  -o-user-select: none;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  font-weight: bold;
}

.row-gap {
  display: flex;
  flex-direction: column;
  row-gap: 8px;
  margin-top: 25px;
}

/* Style the back side */
.flip-card-back {
  display: flex;
  flex-wrap: wrap;
  background-color: #f8f9d2;
  background-image: linear-gradient(315deg, #f8f9d2 0%, #e8dbfc 74%);
  color: black;
  transform: rotateY(180deg);
  box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  -webkit-box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  -moz-box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  width: 150px;
  height: 50px;
}

.back-card-text {
  font-size: 12px;
  line-height: 1.25;
  margin: 0px;
  user-select: none;
  -moz-user-select: none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  -o-user-select: none;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>