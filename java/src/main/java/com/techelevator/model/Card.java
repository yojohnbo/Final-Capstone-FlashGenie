package com.techelevator.model;

public class Card {
    private Long cardId;
    private Long deckId;
    private String frontText;
    private String backText;
    private String keyWords;

    public Card(Long cardId, Long deckId, String frontText, String backText, String keyWords) {
        this.frontText = frontText;
        this.backText = backText;
        this.keyWords = keyWords;
        this.cardId = cardId;
        this.deckId = deckId;
    }

    public Card(){

    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public String getFrontText() {
        return frontText;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }


    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", deckId=" + deckId +
                ", frontText='" + frontText + '\'' +
                ", backText='" + backText + '\'' +
                ", keyWords='" + keyWords + '\'' +
                '}';
    }
}
