package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;

import java.util.ArrayList;
import java.util.List;

public class HandCards {
    private final List<Card> handCards = new ArrayList<>();
    
    public void addCard(Card newCard) {
        handCards.add(newCard);
    }

    public boolean removeCard(Card removeCard) {
        return handCards.remove(removeCard);
    }
    
    public int size() {return handCards.size();}
}
