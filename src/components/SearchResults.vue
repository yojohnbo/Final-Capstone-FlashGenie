<template>
  <div class="searchResultsPage">
    <div class="decks-container">
      <div id="header">
        <h1 id="deck-results">Deck Results</h1>
        <img id="bolt" src="/bolt.png" alt="" />
      </div>

      <div class="results-container">
        <div class="no-decks" v-if="filteredDecks.length === 0">
          No deck subjects match this search
        </div>
        <div class="decks" v-for="deck in filteredDecks" v-bind:key="deck.id">
          <div class="deck-link">
            <router-link :to="{ name: 'cards', params: { id: deck.deckId } }">
              DECK NAME: <em>{{ deck.name }}</em
              ><br />
              SUBJECT: <em>{{ deck.subject }}</em>
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="cards-container">
      <div id="header">
        <h1 id="card-results">Card Results</h1>
        <img id="bolt" src="/bolt.png" alt="" />
      </div>

      <div class="results-container">
        <div class="no-cards" v-if="filteredCards.length === 0">
          No card keywords match this search
        </div>
        <div class="cards" v-for="card in filteredCards" v-bind:key="card.id">
          <div class="card-link">
            <router-link :to="{ name: 'cards', params: { id: card.deckId } }">
              DECK NAME: <em>{{ deckName(card.deckId) }}</em> <br />
              Term: <em>{{ card.frontText }}</em> <br />KEYWORDS:
              <em>{{ card.keyWords.split(" ").join(", ") }}</em></router-link
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import deckService from "@/services/DeckService";
import cardService from "@/services/CardService";
export default {
  data() {
    return {
      // decks: this.$store.state.decks,
      // cards: this.$store.state.allCards,
    };
  },
  computed: {
    decks() {
      return this.$store.state.decks;
    },
    cards() {
      return this.$store.state.allCards;
    },
    //creates array containing decks that match search term
    filteredDecks() {
      return this.decks.filter((deck) => {
        return (
          deck.subject.toLowerCase().includes(this.filterTerm) ||
          deck.name.toLowerCase().includes(this.filterTerm)
        );
      });
    },
    //passes search term from search bar
    filterTerm() {
      return this.$route.params.searchInput.toLowerCase();
    },
    //creates array containing cards that match search term
    filteredCards() {
      return this.cards.filter((card) => {
        return (
          card.keyWords.toLowerCase().includes(this.filterTerm) ||
          card.frontText.toLowerCase().includes(this.filterTerm)
        );
      });
    },
  },
  methods: {
    deckName(id) {
      return this.decks.find((deck) => deck.deckId === id).name;
    },
    //retrieves decks from data store
    getDecks() {
      if (this.$store.state.decks.length === 0) {
        deckService.getAllDecks().then((response) => {
          this.$store.commit("SET_DECKS", response.data);
        });
      }
    },
    //retrieves cards from data store
    getAllCards() {
      if (this.$store.state.allCards.length === 0) {
        cardService.getAllCards().then((response) => {
          this.$store.commit("SET_ALL_CARDS", response.data);
        });
      }
    },
  },
  created() {
    this.getDecks();
    this.getAllCards();
  },
};
</script>

<style scoped>
.searchResultsPage {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  min-height: 100vh;
  user-select: none;
  -moz-user-select: none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  -o-user-select: none;
}

.decks-container {
  background-color: #537895;
  background-image: linear-gradient(315deg, #537895 0%, #09203f 74%);
  justify-content: center;
  min-width: 600px;
  max-width: 1080px;
  border-radius: 30px;
  margin: 30px 0px 0px 0px;
  padding: 30px;
  width: 75%;
  color: white;
  vertical-align: middle;
}

.cards-container {
  background-color: #537895;
  background-image: linear-gradient(315deg, #537895 0%, #09203f 74%);
  justify-content: center;
  min-width: 600px;
  max-width: 1080px;
  border-radius: 30px;
  margin: 30px 0px 0px 0px;
  padding: 30px;
  width: 75%;
  color: white;
}

.results-container {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}

h1#deck-results,
h1#card-results {
  margin: 0px;
  margin-left: 25px;
}

.deck-link {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  width: 300px;
  height: 150px;
  align-items: center;
  padding: 15px;
  margin: 15px;
  margin-left: 20px;
  border: none;
  color: rgb(0, 0, 0);
  text-align: center;
  text-decoration: none;
  border-radius: 10px;
  justify-content: center;
  background-color: #f8f9d2;
  background-image: linear-gradient(315deg, #f8f9d2 0%, #e8dbfc 74%);
  border: solid #bdbdbd 1px;
  box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  -webkit-box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  -moz-box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  font-size: x-large;
  letter-spacing: 1.75px;
  font-weight: bold;
}

.deck-link:hover {
  background-image: linear-gradient(315deg, #e6e9a1 0%, #c6b1e6 74%);
  cursor: pointer;
}

.card-link {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  width: 300px;
  height: 175px;
  align-items: center;
  padding: 15px;
  margin: 15px;
  margin-left: 20px;
  border: none;
  color: rgb(0, 0, 0);
  text-align: center;
  text-decoration: none;
  border-radius: 10px;
  justify-content: center;
  font-size: larger;
  background-color: #f8f9d2;
  background-image: linear-gradient(315deg, #f8f9d2 0%, #e8dbfc 74%);
  border: solid #bdbdbd 1px;
  box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  -webkit-box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  -moz-box-shadow: 5px 5px 18px rgba(0, 0, 0, 0.93);
  font-size: x-large;
  letter-spacing: 1.75px;
  font-weight: bold;
}

.card-link:hover {
  background-image: linear-gradient(315deg, #e6e9a1 0%, #c6b1e6 74%);
  cursor: pointer;
}

a {
  display: flex;
  flex-direction: column;
  width: 100%;
  color: black;
  text-align: left;
  padding: 15px;
  text-decoration: none;
  font-size: 16px;
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

div#header {
  display: flex;
  justify-content: space-between;

  align-items: flex-end;
}

h1#deck-results,
h1#card-results {
  margin-bottom: 5px;
}

img#bolt {
  width: 15px;
  opacity: 0.85;
  padding: 0px;
  margin: 0px;
  margin-right: 25px;
}
</style>