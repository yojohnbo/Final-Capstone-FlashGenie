<template>
  <div class="create-deck">
    <div class="form-container">
      <div class="header">
        <img id="bolt" src="/bolt.png" alt="" />
        <h3>Create New Deck</h3>
        <img id="bolt" src="/bolt.png" alt="" />
      </div>

      <div class="form">
        <form @submit.prevent="saveDeck" class="deck-form">
          <input
            type="text"
            id="deck-name"
            placeholder="Deck Name"
            v-model="deck.name"
          />

          <input
            type="text"
            id="subject"
            placeholder="Deck Subject"
            v-model="deck.subject"
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
import deckService from "@/services/DeckService";
import cardService from "@/services/CardService";
export default {
  name: "newDeckForm",
  data() {
    return {
      deck: {
        name: "",
        clickCount: 0,
        subject: "",
      },
      creationErrors: false,
      creationErrorMessage: "There was a problem creating this deck",
    };
  },
  methods: {
    saveDeck() {
      if (this.deck.name === "" || this.deck.subject === "") {
        this.creationErrors = true;
        this.creationErrorMessage = "All Fields Required";
      } else {
        deckService.create(this.deck).then((response) => {
          if (response.status === 201) {
            this.$router.push("/");
          }
        });
      }
    },
    cancelForm() {
      this.deck.name = "";
      this.deck.subject = "";
      this.$router.push("/");
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
.create-deck {
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

.deck-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 50px;
  width: 325px;
  row-gap: 20px;
}

div.form {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

input#deck-name,
input#subject {
  width: 100%;
  font-family: monospace;
  margin-bottom: 10px;
  padding: 15px;
  border-radius: 8px;
  font-size: medium;
  border-color: #05445e;
  background-color: rgba(241, 241, 241, 0.959);
}

input#deck-name:hover,
input#subject:hover {
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