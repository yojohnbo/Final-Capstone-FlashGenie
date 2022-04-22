package com.techelevator.dao;

import com.techelevator.model.Card;
import java.util.List;

public interface CardDao {

    List<Card> getAllCards(String username);

    List<Card> getAllCardsByDeckId(Long deckId, String username);

    Card createCard(Long deckId, Card card);

    void updateCard(Card card);

    void deleteCard(Long cardId);

}
