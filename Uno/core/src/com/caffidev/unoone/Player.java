package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.abstracts.Entity;

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
    
    public HandList getHandList() { return this.handList;}
    
    public void addToHandCards(Card card) {
        handList.addCard(card);
    }

    public ImmutablePlayer toImmutable() {
        return new ImmutablePlayer(this);
    }
}
