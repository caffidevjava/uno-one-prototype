package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DrawPile {
    private Stack<Card> cards;
    
    public DrawPile(List<Card> cards){
        this();
        cards.addAll(cards);
    }
    
    public DrawPile(){
        cards = new Stack<>();
    }
    
    public Card lastCard(){
        return cards.peek();
    }
    
    public void putCard(Card card){
        cards.add(card);
    }
    
    public List<Card> reshuffle(){
        Collections.shuffle(cards);
        List<Card> givenCards = cards;
        cards = new Stack<>();
        return givenCards;
    }
}
