package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.abstracts.Entity;
import com.caffidev.unoone.enums.CardType;
import com.caffidev.unoone.gui.DrawPileView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CardGame extends Entity {
    private final PlayerRoundDirector players;
    
    private CardPack pack;
    private DrawPile drawPile;
    private ImmutablePlayer winner;
    private Card lastDrawnCard;
    
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
    
    public Card getLastDrawnCard() { return lastDrawnCard; }
    /** Returns -4, if unknown error\n
     *  Returns -3, if card is invalid\n
     *  Returns -2, if owner of playerID is not current player\n
     *  Returns -1, if user does not have card\n
     *  Returns zero, if successful
     */
    public Integer playCard(UUID playerID, Card playedCard)
    {   return playCard(playerID, playedCard, false);}
    
    /**
     * Returns -2 when unknown error
     * Returns -1 when not playerId's turn
     * Returns zero if successful
     * Returns 1 if also card was drawn
     * @param playerId
     * @return
     */
    public Integer drawCard(UUID playerId){
        try {
            if (getCurrentPlayer().getUuid().equals(playerId)) {
                Card drawnCard = drawCards(players.getPlayerByUuid(playerId), 1).get(0);
                return tryToPlayDrawnCard(playerId, drawnCard);
            } else {
                //Not your turn, works only in multiplayer
                return -1;
            }
        } catch (Exception e){
            Game.logger.error("Unknown exception: "+e.toString());
            return -2;
        }
    }
    
    public Integer getCardCount() {
        return pack.getCount();
    }
    
    public Card getTopCard(){
        return drawPile.lastCard();
    }
    
    public boolean isOver() { return winner != null; }
    public ImmutablePlayer getWinner() { return winner; }

    /** Returns -4, if unknown error
     *  Returns -3, if card is invalid 
     *  Returns -2, if owner of playerID is not current player
     *  Returns -1, if user does not have card
     *  Returns zero, if successful
     */
    private Integer playCard(UUID playerID, Card playedCard, boolean ignoreTurnRules) {
        var currentPlayer = players.getCurrentPlayer();
        int answer = CardUtils.validatePlayedCard(playerID, currentPlayer, playedCard);
        if(ignoreTurnRules ? answer != 3 && answer != 0 : answer != 0) {
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
                    players.next();
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
                reverse(); //mention bug
            }
            case PLUS_TWO -> {
                discard(card);
                drawTwoCards(players.getCurrentPlayer());
            }
            case WILD_PLUS_FOUR -> {
                //Makes new pack
                Game.logger.info("+4 card can't be first on a drawpile, recreating it");
                recreateCardPack(card);
            }
        }
    }
    
    private void discard(Card card) {
        drawPile.putCard(card);
    }

    private void recreateCardPack(){
        if(drawPile.size() == 0) {
            throw new IllegalStateException("Not enough cards in draw pile to recreate card pack");
        }
        
        var cards = new ArrayList<Card>();
        cards.addAll(drawPile.reshuffle());
        
        pack = new CardPack(cards);
        
    }
    
    private void recreateCardPack(Card card){
        if(pack.getCount() == 0) {
            throw new IllegalStateException("Not enough cards in card pack to recreate card pack");
        }
        drawPile = new DrawPile();
        
        var oldCards = new ArrayList<Card>();
        oldCards.add(card);
        Integer count = pack.getCount();
        for (int i = 0; i < count; i++) {
            oldCards.add(pack.drawCard());
        }
        
        Collections.shuffle(oldCards);
        pack = new CardPack(oldCards);
        
        startDrawPile();
    }
    
    private void reverse() {
        players.reverseDirection();
    }

    /**
     * Tries to play drawn card. If not successful, returns zero. 1 on success. Changes the turn.
     * @param playerId
     * @param drawnCard
     * @return Success
     */
    private Integer tryToPlayDrawnCard(UUID playerId, Card drawnCard){
        // play card
        var code = playCard(playerId, drawnCard, true);
        players.next();
        
        if (code == 0) {
            Game.logger.info("Player " + playerId + " played with a drawn card " + drawnCard);
            lastDrawnCard = drawnCard;
            return 1;
        }
        lastDrawnCard = null;
        return 0;
    }
    
    private void playerWon(ImmutablePlayer player){
        // Congrats
        winner = player;
        Game.logger.info("Player "+ player.getName() + " with uuid " +
                player.getUuid() + " has won the game!");
    }
    
    private void acceptPlayedCard(Card card){
        var currentPlayer = players.getCurrentPlayer();
        currentPlayer.removePlayedCard(card);
        discard(card);
        
        // Checking for a winner
        var cardsRemaining = getCurrentPlayer().getTotalCards();
        Game.logger.info(String.valueOf(cardsRemaining));
        if(cardsRemaining == 0) playerWon(getCurrentPlayer());
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
        if(amount >= pack.getCount()) {
            Game.logger.info("Recreating card pack due to it's end, needed "+
                    amount + " cards, pack contains "+pack.getCount() + " cards");
            recreateCardPack();
        }
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
