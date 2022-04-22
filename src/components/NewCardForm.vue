<template>
  <div class="create-card">
    <div class="form-container">
      <div class="header">
        <img id="bolt" src="/bolt.png" alt="" />
        <h3>Create New Card</h3>
        <img id="bolt" src="/bolt.png" alt="" />
      </div>

      <div class="form">
        <form @submit.prevent="saveCard" class="card-form">
          <input
            type="text"
            id="card-front-text"
            placeholder="Term"
            v-model="card.frontText"
          />
          <input
            type="text"
            id="card-back-text"
            placeholder="Definition"
            v-model="card.backText"
          />
          <input
            type="text"
            id="card-keywords"
            placeholder="Keywords (separate with spaces)"
            v-model="card.keyWords"
          />
          <div class="buttons">
            <div class="alert" v-if="creationErrors">
              {{ creationErrorMessage }}
            </div>
            <button id="cancel" type="cancel" @click.prevent="cancelForm">
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
  name: "newCardForm",
  data() {
    return {
      creationErrors: false,
      creationErrorMessage: "There was a problem creating this card",
      card: {
        deckId: this.$route.params.id,
        frontText: "",
        backText: "",
        keyWords: "",
      },
    };
  },
  methods: {
    saveCard() {
      if (
        //checks to make sure nothing is empty
        this.card.frontText === "" ||
        this.card.backText === "" ||
        this.card.keyWords === ""
      ) {
        this.creationErrors = true;
        this.creationErrorMessage = "All Fields Required";
      } else {
        cardService.create(this.card.deckId, this.card).then((response) => {
          if (response.status === 201) {
            this.$store.commit("UPDATE_ALL_CARDS", this.card);
            this.$router.push(`/deck/${this.card.deckId}/cards`);
          }
        });
      }
    },
    cancelForm() {
      this.card.frontText = "";
      this.card.backText = "";
      this.card.keyWords = "";
      this.$router.push(`/deck/${this.card.deckId}/cards`);
    },
    getDecks() {
      if (this.$store.state.decks.length === 0) {
        deckService.getAllDecks().then((response) => {
          this.$store.commit("SET_DECKS", response.data);
        });
      }
    },
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
.create-card {
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

.form-container {
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

div.form {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.card-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 50px;
  width: 325px;
  row-gap: 20px;
}

input#card-front-text,
input#card-back-text,
input#card-keywords {
  width: 100%;
  font-family: monospace;
  margin-bottom: 10px;
  padding: 15px;
  border-radius: 8px;
  font-size: medium;
  border-color: #05445e;
  background-color: rgba(241, 241, 241, 0.959);
}

input#card-front-text:hover,
input#card-back-text:hover,
input#card-keywords:hover {
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
  margin-bottom: 100px;
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
</style>