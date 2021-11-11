package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;

import java.util.ArrayList;
import java.util.List;

public class HandList {
    private final List<Card> handList = new ArrayList<>();
    public void addCard(Card newCard) {
        handList.add(newCard);
    }

    public boolean removeCard(Card removeCard) {
        return handList.remove(removeCard);
    }

    public int size() {return handList.size();}
}
