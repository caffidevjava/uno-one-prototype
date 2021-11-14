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
    
    public CardGame(PlayerRoundDirector players, CardPack pack){
        super();
        this.players = players;
        this.pack = pack;
    }
    
    public Stream<ImmutablePlayer> getPlayers() {
        return players.stream().map(Player::toImmutable);
    }
    public Stream<Card> getHandCards(UUID playerId) {
        return players.getPlayerByUuid(playerId).getHandList();
    }
    
    public ImmutablePlayer getCurrentPlayer() { return players.getCurrentPlayer().toImmutable();}
    
    public void drawCard(UUID playerId){
        if(getCurrentPlayer().getUuid().equals(playerId)) {
            Card drawnCard = drawCards(players.getPlayerByUuid(playerId), 1).get(0);
        }
    }
    
    public Integer getCardCount() {
        return pack.getCount();
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
