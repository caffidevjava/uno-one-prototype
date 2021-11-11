package com.caffidev.unoone;

import java.util.UUID;

public class ImmutablePlayer {
    private final Player player;
    
    public ImmutablePlayer(Player player) { this.player = player; }
    
    public UUID getUuid() { return player.getUuid(); }
    
    public String getName() { return player.getName();}
    
    public HandList getHandList() { return player.getHandList(); }
    
    public int getTotalCards() { return (int) getHandList().size();}
}
