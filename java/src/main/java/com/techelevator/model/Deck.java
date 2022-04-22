package com.techelevator.model;

public class Deck {
    private Long deckId;
    private Long userId;
    private String name;
    private String subject;
    private Integer clickCount;


    public Deck(Long deckId, Long userId, String name, String subject, Integer clickCount) {
        this.deckId = deckId;
        this.userId = userId;
        this.name = name;
        this.subject = subject;
        this.clickCount = clickCount;
    }

    public Deck() {

    }

    public Long getDeckId() {
        return deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "deckId=" + deckId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", clickCount=" + clickCount +
                '}';
    }
}
