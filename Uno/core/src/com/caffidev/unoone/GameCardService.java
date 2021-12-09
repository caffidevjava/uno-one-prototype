package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    
    public Card getLastDrawnCard() { return game.getLastDrawnCard();}
    
    public List<ImmutablePlayer> getPlayerInformation(){
        return game.getPlayers().collect(Collectors.toList());
    }

    /** Returns -4, if unknown error
     *  Returns -3, if card is invalid 
     *  Returns -2, if owner of playerID is not current player
     *  Returns -1, if user does not have card
     *  Returns zero, if successful
     */
    public Integer playCard(UUID playerId, Card card){
        Game.logger.info("Player "+ playerId + " plays " + card.toString());
        return game.playCard(playerId, card);
    }
    
    public Card peekTopCard() {
        return game.getTopCard();
    }
    
    public Integer drawCard(UUID playerId) {
        return game.drawCard(playerId);
    } 
    
    public Integer getCardCount() { return game.getCardCount();}
    
    public Stream<Card> getHandCards(UUID playerId) {
        return game.getHandCards(playerId);
    }
    
    public boolean gameIsOver() { return game.isOver(); }
    
    public ImmutablePlayer getWinner() { return game.getWinner();}
    
    private void logCreationOfGame() {
        Game.logger.info("Game was created successfully.");
        // We can't see cards of all players now.
        game.getPlayers().forEach(players -> {
            String joinedCardValues = players.getHandList()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));

            Game.logger.debug(String.format("Player "+ players.getName() + " with "+ players.getTotalCards() + " cards => ["+joinedCardValues+"]"));
        });
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
