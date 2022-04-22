import axios from 'axios';

export default {

    getAllDecks(){
        return axios.get('/decks');
    },

    create(deck){
        return axios.post(`/decks/`, deck);
    },
    update(deck){
        return axios.put('/decks/edit-deck', deck);
    },
    delete(deckId){
        return axios.delete(`decks/${deckId}`);
    }

}
