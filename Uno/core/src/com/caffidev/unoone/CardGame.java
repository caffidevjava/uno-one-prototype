package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.abstracts.Entity;

import java.util.List;

/** Represents a card match, also has it's own unique uuid */
public class CardGame extends Entity {
    private final PlayerRoundDirector players;
    private List<Card> cards;
    
    public CardGame(List<Card> cards, PlayerRoundDirector players){
        super(); //Creates uuids and etc..
        this.players = players;
        this.cards = cards;
    }
}
