package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.abstracts.Entity;
import com.caffidev.unoone.enums.CardType;

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

    /** Returns -4, if unknown error
     *  Returns -3, if card is invalid 
     *  Returns -2, if owner of playerID is not current player
     *  Returns -1, if user does not have card
     *  Returns zero, if successful
     */
    public Integer playCard(UUID playerID, Card playedCard) {
        var currentPlayer = players.getCurrentPlayer();
        int answer = CardUtils.validatePlayedCard(playerID, currentPlayer, playedCard);
        if(answer != 0) {
            return answer;
        }
        
        if(getCurrentPlayer().getUuid().equals(playerID)) {
            switch(playedCard.getCardType()) {
                case NUMBER-> {
                    if(!checkNumberCard(playedCard)) return -3;
                    acceptPlayedCard(playedCard);
                    
                    players.next();
                }
                case SKIP -> {
                    if(!checkActionCard(playedCard)) return -3;
                    acceptPlayedCard(playedCard);
                    
                    players.next();
                    players.next();
                }
                case REVERSE -> {
                    if(!checkActionCard(playedCard)) return -3;
                    acceptPlayedCard(playedCard);
                    
                    reverse();
                }
                case PLUS_TWO -> {
                    if(!checkActionCard(playedCard)) return -3;
                    acceptPlayedCard(playedCard);
                    
                    players.next();
                    drawTwoCards(players.getCurrentPlayer());
                    players.next();
                }
                case WILD_COLOR -> {
                    if(!checkWildCard(playedCard)) return -3;
                    acceptPlayedCard(playedCard);
                    
                    players.next();
                }
                case WILD_PLUS_FOUR -> {
                    if(!checkWildCard(playedCard)) return -3;
                    acceptPlayedCard(playedCard);
                    
                    players.next();
                    drawFourCards(players.getCurrentPlayer());
                    players.next();
                }
                default -> {
                    return -4;
                }
            }
        }
        return 0;
    }

    /**
     * Returns -2 when unknown error
     * Returns -1 when not playerId's turn
     * Returns zero if successful
     * @param playerId
     * @return
     */
    public Integer drawCard(UUID playerId){
        try {
            if (getCurrentPlayer().getUuid().equals(playerId)) {
                Card drawnCard = drawCards(players.getPlayerByUuid(playerId), 1).get(0);
                tryToPlayDrawnCard(playerId, drawnCard);
                return 0;
            } else {
                //Not your turn, works only in multiplayer
                return -1;
            }
        } catch (Exception e){
            Game.logger.error("Unknown exception: "+e.getLocalizedMessage());
            return -2;
        }
    }
    
    public Integer getCardCount() {
        return pack.getCount();
    }
    
    public Card getTopCard(){
        return drawPile.lastCard();
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
    
    private void acceptPlayedCard(Card card){
        players.getCurrentPlayer().removePlayedCard(card);
        discard(card);
    }
    
    private Boolean checkNumberCard(Card playedCard){
        var topCard = drawPile.lastCard();
        
        if(isFirstWildCard() || CardUtils.isValidNumberCard(topCard, playedCard)) {
            return true;
        }
        return false;
    }

    private Boolean checkActionCard(Card playedCard){
        var topCard = drawPile.lastCard();

        if(isFirstWildCard() || CardUtils.isValidActionCard(topCard, playedCard)) {
            return true;
        }
        return false;
    }

    private Boolean checkWildCard(Card playedCard){
        return CardUtils.isValidWildCard(playedCard);
    }
    
    private Boolean isFirstWildCard() {
        return drawPile.size() == 1 && 
                (drawPile.lastCard().getCardType() == CardType.WILD_COLOR ||
                drawPile.lastCard().getCardType() == CardType.WILD_PLUS_FOUR);        
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
