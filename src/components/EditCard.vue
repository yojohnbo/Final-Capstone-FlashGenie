<template>
  <div class="editCardPage" v-if="!isLoading">
    <div class="form-container">
      <div class="header">
        <img id="bolt" src="/bolt.png" alt="" />
        <h3>CARD DETAILS</h3>
        <img id="bolt" src="/bolt.png" alt="" />
      </div>

      <div class="card-details">
        <h4 class="term">TERM: {{ currentCard.frontText }}</h4>
        <h4 class="definition">DEFINITION: {{ currentCard.backText }}</h4>
        <h4 class="keywords">
          KEYWORDS: {{ currentCard.keyWords.split(" ").join(", ") }}
        </h4>
      </div>

      <h5>EDIT CARD</h5>
      <div class="form">
        <form @submit.prevent="updateCard" class="update-card-form">
          <input
            type="text"
            class="front-text"
            placeholder="New term"
            v-model="card.frontText"
          />

          <input
            type="text"
            class="back-text"
            placeholder="New definition"
            v-model="card.backText"
          />

          <input
            type="text"
            class="key-words"
            v-model="card.keyWords"
            placeholder="New keywords (Separate with spaces)"
          />

          <div class="buttons">
            <button id="delete" @click.prevent="deleteCard">DELETE CARD</button>
            <button id="cancel" type="cancel" @click.prevent="cancelUpdate">
              Cancel
            </button>
            <button id="save" type="submit">Submit</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import cardService from "@/services/CardService";
import deckService from "@/services/DeckService";
export default {
  data() {
    return {
      getCount: 0,
      card: {
        cardId: Number(this.$route.params.cardId),
        deckId: Number(this.$route.params.deckId),
        frontText: "",
        backText: "",
        keyWords: "",
      },
    };
  },
  computed: {
    cards() {
      return this.$store.state.cards;
    },
    currentCard() {
      return this.cards.find((card) => card.cardId == this.card.cardId);
    },
    isLoading() {
      return this.getCount !== 3;
    },
  },
  methods: {
    updateCard() {
      cardService.update(this.card).then((response) => {
        if (response.status === 200) {
          this.$store.commit("UPDATE_ALL_CARDS", this.card);
          this.$router.push(`/deck/${this.card.deckId}/cards`);
        }
      });
    },
    cancelUpdate() {
      this.card.frontText = "";
      this.card.backText = "";
      this.card.keyWords = "";
      this.$router.push(`/deck/${this.card.deckId}/cards`);
    },
    deleteCard() {
      if (
        confirm("Do you really want to delete the card? This cannot be undone.")
      ) {
        cardService.delete(this.card.cardId).then((response) => {
          if (response.status === 204) {
            this.getDecks();
            this.getCards();
            this.$router.push(`/deck/${this.card.deckId}/cards`);
          }
        });
      }
    },
    getDecks() {
        //done for refreshes
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
    getCards() {
      cardService.getAllCardsByDeckId(this.card.deckId).then((response) => {
        this.$store.commit("SET_CARDS_BY_DECK_ID", response.data);
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
.editCardPage {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0;
  margin: 0;
  width: 100%;
  min-height: 100vh;
}

.form-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #537895;
  background-image: linear-gradient(315deg, #537895 0%, #09203f 74%);
  min-width: 600px;
  border-radius: 30px;
  margin: 30px 0px 0px 0px;
  padding-top: 50px;
  padding-bottom: 50px;
  width: 75%;
  max-width: 1200px;
}

div.header {
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
}

h3 {
  color: white;
  font-size: 45px;
  text-align: center;
  text-transform: uppercase;
  letter-spacing: 1.75px;
  margin: 15px;
}

h4 {
  text-align: center;
  color: black;
  letter-spacing: 1px;
  padding: 5px;
  margin: 0px;
  font-size: 16px;
}

h5 {
  font-size: 18px;
  margin: 0px;
  margin-top: 40px;
  color: white;
}

.term,
.definition,
.keywords {
  font-weight: bold;
}

.card-details {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  flex-grow: 1;
  flex-basis: auto;
  width: 400px;
  height: auto;
  padding: 25px;
  margin: 15px;
  border: none;
  color: rgb(0, 0, 0);
  text-decoration: none;
  border-radius: 10px;
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

div.form {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.update-card-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px 10px 50px 10px;
  width: 400px;
  row-gap: 20px;
}

input.front-text,
input.back-text,
input.key-words {
  width: 100%;
  font-family: monospace;
  margin-bottom: 10px;
  padding: 15px;
  border-radius: 8px;
  font-size: medium;
  border-color: #05445e;
  background-color: rgba(241, 241, 241, 0.959);
}

input.front-text:hover,
input.back-text:hover,
input.key-words:hover {
  background-color: white;
}

div.buttons {
  display: flex;
  column-gap: 10px;
}

button#save,
button#cancel {
  background-color: #f8f9d2;
  background-image: linear-gradient(315deg, #d2e6f9 0%, #6b8fa7 74%);
  border: solid #747474 1px;
  width: 120px;
  margin: 10px 10px 20px 10px;
  display: inline-block;
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

button#save:hover,
button#save:focus,
button#cancel:hover,
button#cancel:focus {
  text-decoration: none;
  background-image: linear-gradient(315deg, #dbec8e 0%, #d8b30e 85%);
  box-shadow: inset 0 0 0 2em var(--hover);
  color: #09203f;
  font-weight: bold;
  cursor: pointer;
}

button#delete {
  background-color: #b8b8b8;
  border: solid #bdbdbd 1px;
  width: 80px;
  margin: 10px 10px 20px 10px;
  text-decoration: none;
  text-align: center;
  font-family: monospace;
  border: none;
  color: rgb(0, 0, 0);
  padding: 10px;
  display: inline-block;
  border-radius: 12px;
  font-size: 12px;
  letter-spacing: 1.75px;
  font-weight: bold;
  box-shadow: 5px 5px 18px rgba(0, 0, 0, 1.93);
  -webkit-box-shadow: 5px 5px 18px rgba(0, 0, 0, 1.93);
  -moz-box-shadow: 5px 5px 18px rgba(0, 0, 0, 1.93);
}

button#delete:hover {
  background-color: red;
  cursor: pointer;
  width: 80px;
  margin: 10px 10px 20px 10px;
  text-decoration: none;
  text-align: center;
  font-family: monospace;
  border: none;
  color: rgb(0, 0, 0);
  padding: 10px;
  display: inline-block;
  border-radius: 12px;
  font-size: 12px;
  letter-spacing: 1.75px;
  font-weight: bold;
  box-shadow: 5px 5px 18px rgba(0, 0, 0, 1.93);
  -webkit-box-shadow: 5px 5px 18px rgba(0, 0, 0, 1.93);
  -moz-box-shadow: 5px 5px 18px rgba(0, 0, 0, 1.93);
}
</style>