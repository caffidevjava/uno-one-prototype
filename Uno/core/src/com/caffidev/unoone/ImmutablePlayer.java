package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;

import java.util.UUID;
import java.util.stream.Stream;

public class ImmutablePlayer {
    private final Player player;
    
    public ImmutablePlayer(Player player) { this.player = player; }
    
    public UUID getUuid() { return player.getUuid(); }
    
    public String getName() { return player.getName();}
    
    public Stream<Card> getHandList() { return player.getHandList(); }
    
    public int getTotalCards() { return (int) getHandList().count();}
}
