<template>
  <div class="editDeckPage" v-if="!isLoading">
    <div class="form-container">
      <div class="header">
        <img id="bolt" src="/bolt.png" alt="" />
        <h3>Edit Deck</h3>
        <img id="bolt" src="/bolt.png" alt="" />
      </div>

      <div class="deck-details">
        <h4 class="name">DECK NAME: {{ currentDeck.name }}</h4>
        <h4 class="subject">DECK SUBJECT: {{ currentDeck.subject }}</h4>
      </div>
      <h5>EDIT CARD</h5>
      <div class="form">
        <form @submit.prevent="updateDeck" class="update-deck-form">
          <input
            type="text"
            class="name"
            placeholder="New deck name"
            v-model="deck.name"
          />

          <input
            type="text"
            class="subject"
            placeholder="New subject"
            v-model="deck.subject"
          />

          <div class="buttons">
            <button id="delete" @click.prevent="deleteDeck">DELETE DECK</button>
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
import deckService from "@/services/DeckService";
import cardService from "@/services/CardService";
export default {
  data() {
    return {
      getCount: 0,
      deck: {
        deckId: Number(this.$route.params.id),
        name: "",
        subject: "",
        clickCount: 0,
      },
      // decks: this.$store.state.decks,
    };
  },
  computed: {
    decks() {
      return this.$store.state.decks;
    },
    currentDeckId() {
      return Number(this.$route.params.id);
    },
    currentDeck() {
      return this.decks.find((deck) => deck.deckId === this.currentDeckId);
    },
    isLoading() {
      return this.getCount !== 2;
    }
  },
  methods: {
    updateDeck() {
      deckService.update(this.deck).then((response) => {
        if (response.status === 200) {
          this.$router.push("/");
        }
      });
    },
    cancelUpdate() {
      this.deck.name = "";
      this.deck.subject = "";
      this.$router.push("/");
    },
    deleteDeck() {
      if (
        confirm(
          "Do you really want to delete the deck? It will delete all cards in the deck."
        )
      ) {
        if (confirm("Are you sure? This action cannot be undone!")) {
          deckService.delete(this.deck.deckId).then((response) => {
            if (response.status === 204) {
              this.getDecks();
              this.getAllCards();
              this.$router.push("/");
            }
          });
        }
      }
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
  },
};
</script>

<style scoped>
.editDeckPage {
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
  letter-spacing: 1.75px;
  margin: 15px;
}

h4 {
  text-align: center;
  color: black;
  letter-spacing: 1px;
  padding: 0px;
  margin: 0px;
  font-size: 16px;
}

h5 {
  font-size: 18px;
  margin: 0px;
  margin-top: 40px;
  color: white;
}

.name,
.subject {
  font-weight: bold;
}

.deck-details {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  flex-basis: auto;
  width: 400px;
  height: auto;
  min-height: 100px;
  font-size: larger;
  border-radius: 10px;
  padding: 25px;
  margin: 15px;
  border: none;
  line-height: 3;
  align-items: center;
  justify-content: center;
  color: rgb(0, 0, 0);
  text-align: center;
  text-decoration: none;
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

.update-deck-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px 50px 50px 50px;
  width: 400px;
  row-gap: 20px;
}

input.name,
input.subject {
  width: 100%;
  font-family: monospace;
  margin-bottom: 10px;
  padding: 15px;
  border-radius: 8px;
  font-size: medium;
  border-color: #05445e;
  background-color: rgba(241, 241, 241, 0.959);
}
input.name:hover,
input.subject:hover {
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