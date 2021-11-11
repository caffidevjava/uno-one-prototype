package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GameCardService {
    private final CardGame game;
    private List<String> players;
    
    public static final int INITIAL_HAND_CARDS = 7;
    
    public GameCardService(String... names){
        players = Arrays.asList(names.clone());
        game = buildGame();
        logCreationOfGame();
    }
    private CardGame buildGame() {
        List<Card> basicDeck = new CardDeck().getImmutableCards();
        
        // Shuffles up the cards
        Collections.shuffle(basicDeck);
        CardPack pack = new CardPack(basicDeck);
        
        PlayerRoundDirector players = buildPlayers(pack);
        
        return new CardGame(players, pack);
    }
    
    public ImmutablePlayer getCurrentPlayer() {
        return game.getCurrentPlayer();
    }
    
    public void drawCard(UUID playerId) {
        game.drawCard(playerId);
        Game.logger.info("Player " + game.getPlayers().getPlayerByUuid(playerId).getName() + " draws a card.");
    } 
    private void logCreationOfGame() {
        Game.logger.info("Game was created successfully.");
        // We can't see cards of all players now.
        Game.logger.info(game.getCurrentPlayer().getHandList().toString());
    }
    
    private PlayerRoundDirector buildPlayers(CardPack pack) {
        if(players.size() < 2 || players.size() > 10) {
            throw new IllegalArgumentException("2-10 players are required to play this game.");
        }
        
        HandList[] handList = giveInitialHandCards(pack, players.size());
        Player[] players = new Player[this.players.size()];
        for (Integer i = 0; i<this.players.size(); i++) {
            players[i] = new Player(this.players.get(i), handList[i]);
        }
        return new PlayerRoundDirector(players);
    }
    
    private HandList[] giveInitialHandCards(CardPack pack, int amountOfPlayers) {
        HandList[] handLists = new HandList[amountOfPlayers];
        for(int player = 0; player < amountOfPlayers; player++) {
            handLists[player] = giveInitialHandCards(pack);
        }
        return handLists;
    }
    
    private HandList giveInitialHandCards(CardPack pack) {
        HandList handList = new HandList();
        for (int i = 0; i < INITIAL_HAND_CARDS; i++) {
            handList.addCard(pack.drawCard());
        }
        return handList;
    }
}
