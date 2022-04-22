package com.techelevator.dao;

import com.techelevator.model.Deck;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDeckDao implements DeckDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcDeckDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Deck> getAllDecks(String userName) {
        List<Deck> decks = new ArrayList<>();

        String sql = "SELECT deck_id, decks.user_id, click_count, deck_name, subject " +
                "FROM decks JOIN users ON users.user_id = decks.user_id " +
                "WHERE username = ? " +
                "ORDER BY decks.deck_name; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userName);
        while (results.next()) {
            decks.add(mapRowToDeck(results));
        }
        return decks;
    }

//Maybe needed later?
    @Override
    public Deck getDeck(String userName, Long deckId) {
        Deck resultDeck = null;

        String sql = "SELECT deck_id, decks.user_id, click_count, deck_name, subject " +
                "FROM decks JOIN users ON decks.user_id = users.user_id " +
                "WHERE deck_id = ? AND users.username = ? " +
                "ORDER BY deck_name; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, deckId, userName);

        if (results.next()) {
            resultDeck = mapRowToDeck(results);
        }
        return resultDeck;
    }

    @Override
    public Deck createDeck(String userName, Deck deck){
        String sql = "INSERT INTO decks (user_id, deck_name, subject) " +
                "VALUES ((SELECT user_id FROM users WHERE username = ?), ?, ?) RETURNING deck_id";
        Long decks_id = jdbcTemplate.queryForObject(sql, Long.class, userName, deck.getName(), deck.getSubject());
        deck.setDeckId(decks_id);
        return deck;
    }

    @Override
    public void updateDeck(String userName, Deck deck){
        String sql = "UPDATE decks SET deck_name = ?, subject = ? " +
                "WHERE decks.user_id = (SELECT users.user_id FROM users WHERE username = ?) AND decks.deck_id = ?; ";
        jdbcTemplate.update(sql, deck.getName(), deck.getSubject(), userName, deck.getDeckId());

    }

    @Override
    public void deleteDeck(Long deckId){
        String sql = "DELETE FROM cards WHERE deck_id = ?; ";
        jdbcTemplate.update(sql, deckId);

        sql = "DELETE FROM decks WHERE deck_id = ?; ";
        jdbcTemplate.update(sql, deckId);
    }

    private Deck mapRowToDeck(SqlRowSet rowSet) {
        Deck deck = new Deck();
        deck.setDeckId(rowSet.getLong("deck_id"));
        deck.setUserId(rowSet.getLong("user_id"));
        deck.setClickCount(rowSet.getInt("click_count"));
        deck.setSubject(rowSet.getString("subject"));
        deck.setName((rowSet.getString("deck_name")));
        return deck;
    }


}
