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
    
    public int size() { return cards.size();}
    
    /** Automatically deletes all cards (apart from one) from drawpile, and gives it back shuffled */
    public List<Card> reshuffle(){
        Card card = cards.pop();     
        Collections.shuffle(cards);
        List<Card> givenCards = cards;
        cards = new Stack<>();
        cards.add(card);
        return givenCards;
    }
}
