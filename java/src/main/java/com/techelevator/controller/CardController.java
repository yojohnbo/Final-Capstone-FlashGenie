package com.techelevator.controller;

import com.techelevator.dao.CardDao;
import com.techelevator.model.Card;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class CardController {
    private CardDao cardDao;

    public CardController(CardDao cardDao) { this.cardDao = cardDao; }

    @RequestMapping (value = "/decks/cards", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Card> getAllCards(Principal userName) {
        return cardDao.getAllCards(userName.getName());
    }

    @RequestMapping (value = "/decks/{deckId}/cards", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Card> getAllCardsByDeckId(@PathVariable Long deckId, Principal userName) {
        return cardDao.getAllCardsByDeckId(deckId, userName.getName());
    }

    @RequestMapping(value = "/decks/{deckId}/cards", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Card postCard(@RequestBody Card newCard, @PathVariable Long deckId){
        return cardDao.createCard(deckId, newCard);
    }

    @RequestMapping(value = "/deck/card/edit-card", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCard(@RequestBody Card card){
        cardDao.updateCard(card);
    }

    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable Long cardId){
        cardDao.deleteCard(cardId);
    }

}
