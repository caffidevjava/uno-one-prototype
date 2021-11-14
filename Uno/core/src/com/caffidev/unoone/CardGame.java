package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.abstracts.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class CardGame extends Entity {
    private final PlayerRoundDirector players;
    
    private CardPack pack;
    private DrawPile drawPile;
    
    public CardGame(PlayerRoundDirector players, CardPack pack){
        super();
        this.players = players;
        this.pack = pack;
        drawPile = new DrawPile();
        
        startDrawPile();
    }
    
    
    public Stream<ImmutablePlayer> getPlayers() {
        return players.stream().map(Player::toImmutable);
    }
    public Stream<Card> getHandCards(UUID playerId) {
        return players.getPlayerByUuid(playerId).getHandList();
    }
    
    public ImmutablePlayer getCurrentPlayer() { return players.getCurrentPlayer().toImmutable();}
    
    public void playCard(UUID playerID, Card playedCard) {
        // Validation of played card
        switch(playedCard.getCardType()){
            default -> {
                players.getCurrentPlayer().removePlayedCard(playedCard);
                discard(playedCard);
                // Uno?
                // Winner
                // It's own acceptPlayedCard func?
            }
        }
    }
    
    public void drawCard(UUID playerId){
        if(getCurrentPlayer().getUuid().equals(playerId)) {
            Card drawnCard = drawCards(players.getPlayerByUuid(playerId), 1).get(0);
            tryToPlayDrawnCard(playerId, drawnCard);
        }
        else {
            //Not your turn
        }
    }
    
    public Integer getCardCount() {
        return pack.getCount();
    }

    private void startDrawPile() {
        Card card = pack.drawCard();
        switch (card.getCardType()) {
            case NUMBER, WILD_COLOR -> discard(card);
            case SKIP ->{
                discard(card);
                players.next();
            }
            case REVERSE -> {
                discard(card);
                reverse();
            }
            case PLUS_TWO -> {
                discard(card);
                drawTwoCards(players.getCurrentPlayer());
            }
            case WILD_PLUS_FOUR -> {
                // todo: make a new drawpile
                throw new UnsupportedOperationException("not implemented");
            }
        }
    }
    
    private void discard(Card card) {
        drawPile.putCard(card);
    }
    
    private void reverse() {
        players.reverseDirection();
        players.next();
    }
    
    private void tryToPlayDrawnCard(UUID playerId, Card drawnCard){
        // play card
        players.next();
    }
    
    private void drawTwoCards(Player player) {
        Game.logger.debug("Drawn 2 cards, to be more precise ->");
        drawCards(player, 2);
    }
    
    private void drawFourCards(Player player) {
        Game.logger.debug("Drawn 4 cards, to be more precise ->");
        drawCards(player, 4);
    }
    
    private List<Card> drawCards(Player player, int amount){
        // Cards that we will give away
        List<Card> drawnCards = new ArrayList<Card>();
        for (int i = 0; i < amount; i++) {
            Card drawnCard = pack.drawCard();
            drawnCards.add(drawnCard);
            player.addToHandCards(drawnCard);
            Game.logger.debug("Drawn "+ drawnCard.toString());
        }
        return drawnCards;
    }
}
