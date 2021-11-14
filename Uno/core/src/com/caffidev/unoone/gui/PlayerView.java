package com.caffidev.unoone.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.caffidev.unoone.Game;
import com.caffidev.unoone.GameCardService;
import com.caffidev.unoone.ImmutablePlayer;
import com.caffidev.unoone.Player;
import com.caffidev.unoone.abstracts.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerView {
    public HorizontalGroup horizontalGroup;
    protected List<Card> cards;
    protected final ImmutablePlayer player;
    protected final GameCardService service;
    
    public PlayerView(Stage stage, ImmutablePlayer player, GameCardService service){
        horizontalGroup = new HorizontalGroup();
        stage.addActor(horizontalGroup);
        
        cards = new ArrayList<>();
        this.player = player;
        this.service = service;

        horizontalGroup.setPosition(stage.getWidth() / 2f - 60 , 50);
    }
    
    public void update() {
        horizontalGroup.clear();
        
        cards.clear();
        cards = service.getHandCards(player.getUuid()).collect(Collectors.toList());
        
        for (Card card : cards) {
            card.setScale(card.getScaleX(), card.getScaleY());
            horizontalGroup.addActor(card);
        }
    }
    public void playCardFromHand(Card card){
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    public void drawCard(){
        service.drawCard(player.getUuid());
    }
}
