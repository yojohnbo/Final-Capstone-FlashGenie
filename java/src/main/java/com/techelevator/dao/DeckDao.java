package com.techelevator.dao;

import com.techelevator.model.Deck;

import java.security.Principal;
import java.util.List;

public interface DeckDao {

    List<Deck> getAllDecks(String userName);

    //Maybe needed later?
    Deck getDeck(String userName, Long deckId);

    Deck createDeck(String userName, Deck passedDeck);

    void updateDeck(String userName, Deck deck);

    void deleteDeck(Long deckId);

}
