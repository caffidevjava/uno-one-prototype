package com.caffidev.unoone.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.utils.Align;
import com.caffidev.unoone.GameCardService;
import com.caffidev.unoone.ImmutablePlayer;
import com.caffidev.unoone.abstracts.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerView {
    protected float height;
    protected final Stage stage;
    public HorizontalGroup horizontalGroup;
    protected List<Card> cards;
    protected final ImmutablePlayer player;
    protected final GameCardService service;
    
    public PlayerView(Stage stage, ImmutablePlayer player, GameCardService service, float height){
        horizontalGroup = new HorizontalGroup();
        stage.addActor(horizontalGroup);
        
        cards = new ArrayList<>();
        this.player = player;
        this.service = service;
        
        this.stage = stage;
        this.height = height;
    }
    
    public void update() {
        horizontalGroup.clear();
        
        cards.clear();
        cards = service.getHandCards(player.getUuid()).collect(Collectors.toList());
        
        for (Card card : cards) {
            horizontalGroup.addActor(card);
        }
        horizontalGroup.align(Align.center);
        horizontalGroup.setPosition(stage.getWidth()/2f, height);
    }
    public void playCardFromHand(Card card){
        throw new UnsupportedOperationException("not implemented yet");
    }
    
    public void drawCard(){
        service.drawCard(player.getUuid());
    }
}
