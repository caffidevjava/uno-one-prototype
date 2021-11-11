package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;

import java.util.List;
import java.util.Stack;

public class CardPack {
    private final Stack<Card> cards;
    
    public CardPack(List<Card> shuffledCards) {
        cards = new Stack<>();
        cards.addAll(shuffledCards);
    }
    
    public Card drawCard() {
        return cards.pop();
    }
}
