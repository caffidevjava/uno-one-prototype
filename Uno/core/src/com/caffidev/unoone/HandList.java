package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class HandList {
    private final List<Card> handList = new ArrayList<>();
    public void addCard(Card newCard) {
        handList.add(newCard);
    }

    public boolean removeCard(Card removeCard) {
        return handList.remove(removeCard);
    }
    
    public Stream<Card> getCardStream() { return handList.stream();}
    
    public int size() {return handList.size();}
}
