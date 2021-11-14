package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.abstracts.Entity;

import java.util.stream.Stream;

public class Player extends Entity {
    private final String name;
    private final HandList handList;
    public Player(String name, HandList handList)
    {
        super();
        this.name = name;
        this.handList = handList;
    }
    
    public String getName() { return name; }
    
    public Stream<Card> getHandList() { return this.handList.getCardStream();}
    
    public void addToHandCards(Card card) {
        handList.addCard(card);
    }
    
    public void removePlayedCard(Card card){
        handList.removeCard(card);
    }
    
    public boolean hasHandCard(Card card) {
        return this.handList.hasCard(card);
    }
    public ImmutablePlayer toImmutable() {
        return new ImmutablePlayer(this);
    }
}
