package com.caffidev.unoone;

import com.caffidev.unoone.enums.GameDirection;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;


public class PlayerRoundDirector {
    private final Player[] players;
    private int current = 0;
    private GameDirection direction = GameDirection.CLOCKWISE;
    
    public PlayerRoundDirector(Player[] players){
        this.players = players;
    }

    public Stream<Player> stream() {
        return Arrays.stream(players);
    }
    
    public Player getCurrentPlayer() {
        return players[current];
    }
    
    /** Returns null if not found */
    public Player getPlayerByUuid(UUID playerId){
        for (Player player : players) {
            if (player.getUuid().equals(playerId)) {
                return player;
            }
        }
        return null;
    }
    
    public void reverseDirection() {
        direction = (direction == GameDirection.CLOCKWISE ? GameDirection.COUNTER_CLOCKWISE : GameDirection.CLOCKWISE);  
    }
    
    public Player next() {
        current = getNextPlayerIndex();
        return getCurrentPlayer();
    }
    
    private int getNextPlayerIndex() {
        int increment = direction == GameDirection.CLOCKWISE ? 1 : -1;
        return (players.length + current + increment) % players.length;
    }
}
