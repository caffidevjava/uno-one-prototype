package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameBuilder {
    private List<String> playerNames = new ArrayList<>();
    
    public GameBuilder withPlayer(String name){
        playerNames.add(name);
        return this;
    }
    
    public CardGame build(){
        List<Card> cards = new CardDeck().getCards();
        List<Card> shuffledCards = CardUtils.shuffleCards(cards);
        return new CardGame(shuffledCards, null);
    }
    
    private void buildPlayers(){}
}
