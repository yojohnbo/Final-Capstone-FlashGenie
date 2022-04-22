package com.techelevator.dao;

import com.techelevator.model.Card;
import com.techelevator.model.Deck;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCardDao implements CardDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcCardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Card> getAllCards(String username){
        List<Card> cards= new ArrayList<>();

        String sql = "SELECT card_id, cards.deck_id, front_text, back_text, keywords " +
                "FROM cards JOIN decks ON decks.deck_id = cards.deck_id " +
                "JOIN users ON users.user_id = decks.user_id " +
                "WHERE username = ? " +
                "ORDER BY front_text; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while (results.next()) {
            cards.add(mapRowToCard(results));
        }

        return cards;

    }

    public List<Card> getAllCardsByDeckId(Long deckId, String username) {
        List<Card> cards = new ArrayList<>();

        String sql = "SELECT card_id, cards.deck_id, front_text, back_text, keywords " +
                "FROM cards JOIN decks ON decks.deck_id = cards.deck_id " +
                "JOIN users ON users.user_id = decks.user_id " +
                "WHERE username = ? AND decks.deck_id = ? " +
                "ORDER BY front_text; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username, deckId);
        while (results.next()) {
            cards.add(mapRowToCard(results));
        }

        return cards;
    }

    public Card createCard(Long deckId, Card card) {
        String sql = "INSERT INTO cards (deck_id, front_text, back_text, keywords) " +
                "VALUES (?, ?, ?, ?) RETURNING cards.card_id; ";
        Long card_id = jdbcTemplate.queryForObject(sql, Long.class, deckId, card.getFrontText(), card.getBackText(), card.getKeyWords());
        card.setCardId(card_id);
        return card;
    }

    public void updateCard(Card card){
        String sql = "UPDATE cards SET front_text = ?, back_text = ?, keywords = ? WHERE deck_id = ? AND card_id = ?;";
        jdbcTemplate.update(sql, card.getFrontText(), card.getBackText(), card.getKeyWords(), card.getDeckId(), card.getCardId());
    }

    public void deleteCard(Long cardId){
        String sql = "DELETE FROM cards WHERE card_id = ?; ";
        jdbcTemplate.update(sql, cardId);
    }

    private Card mapRowToCard(SqlRowSet rowSet) {
        Card card = new Card();
        card.setCardId(rowSet.getLong("card_id"));
        card.setDeckId(rowSet.getLong("deck_id"));
        card.setFrontText(rowSet.getString("front_text"));
        card.setBackText(rowSet.getString("back_text"));
        card.setKeyWords(rowSet.getString("keywords"));
        return card;
    }
}
