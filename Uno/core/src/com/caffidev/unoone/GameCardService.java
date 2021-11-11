package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.sun.org.apache.xml.internal.serializer.ToHTMLSAXHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.lang.model.element.VariableElement;

public class GameCardService {
    private final CardGame game;
    private Integer amountOfPlayers;
    public GameCardService(int amountOfPlayers){
        this.amountOfPlayers = amountOfPlayers;
        game = buildGame();
    }
    public CardGame buildGame() {
        List<Card> basicDeck = new CardDeck().getImmutableCards();
        
        // Shuffles up the cards
        Collections.shuffle(basicDeck);
        CardPack pack = new CardPack(basicDeck);
        
        List<Player> players = buildPlayers();
        
        return new CardGame(players, pack);
    }
    
    public void drawCard(UUID playerId) {
        game.drawCard(playerId);
    } 
    
    private List<Player> buildPlayers() {
        List<Player> players = new ArrayList<Player>() {
        };
        for (Integer i = 0; i<amountOfPlayers; i++) {
            players.add(new Player("Player" + i.toString()));
        }
        return players;
    }
}
