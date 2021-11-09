package com.caffidev.unoone;

public class GameService {
    private final CardGame game;
    public GameService(Integer numberOfPlayers) {
        game = new CardGame(numberOfPlayers);
    }
}
