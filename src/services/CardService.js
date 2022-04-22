import axios from 'axios';

export default {

    getAllCards(){
        return axios.get('/decks/cards')
    },
    getAllCardsByDeckId(id){
        return axios.get(`/decks/${id}/cards`);
    },

    create(id, card){
        return axios.post(`/decks/${id}/cards`, card);
    },
    update(card){
        return axios.put('/deck/card/edit-card', card);
    },
    delete(cardId){
        return axios.delete(`/card/${cardId}`);
    }


}