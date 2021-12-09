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

public class PlayerView{
    public float VIEW_WIDTH;
    public HorizontalGroup horizontalGroup;
    protected final Stage stage;
    
    protected List<Card> cards;
    protected final ImmutablePlayer player;
    
    protected final GameCardService service;
    
    protected float width;
    protected float height;
    protected float rotationDeg;
    
    public PlayerView(Stage stage, ImmutablePlayer player, GameCardService service, float width, float height, float rotationDeg){
        horizontalGroup = new HorizontalGroup();
        stage.addActor(horizontalGroup);
        
        cards = new ArrayList<>();
        this.player = player;
        this.service = service;
        
        this.stage = stage;
        
        this.width = width;
        this.height = height;
        this.rotationDeg = rotationDeg;
        
        VIEW_WIDTH = stage.getWidth() / 2f;
    }
    
    public void update() {
        horizontalGroup.clear();
        
        
        cards.clear();
        cards = service.getHandCards(player.getUuid()).collect(Collectors.toList());
        
        for (Card card : cards) {
            horizontalGroup.addActor(card);
            card.linkPlayer(this, service);
        }
        
        //todo: fix alignment bug
        horizontalGroup.setPosition(width, height, Align.center);
        horizontalGroup.setRotation(rotationDeg);
        // horizontalGroup.getChild(1). we can get style && hide.cards
        //horizontalGroup.wrap(true);
        //horizontalGroup.setWidth(SCREEN_SPACE);
        //horizontalGroup.setHeight(horizontalGroup.getChild(0).getHeight());
        var space = (VIEW_WIDTH - (horizontalGroup.getChildren().size * horizontalGroup.getChild(0).getWidth())) / (horizontalGroup.getChildren().size - 1);
        if (horizontalGroup.getChildren().size > 0 && space <= 0) {
            horizontalGroup.space(space);
            //horizontalGroup.setOrigin((horizontalGroup.getChild(0).getWidth() * horizontalGroup.getChildren().size) / 2f, horizontalGroup.getHeight()/2f);
        }
        else horizontalGroup.space(0);
    }
    
    public ImmutablePlayer getPlayer() {
        return player;
    }
    
    public Integer drawCard(){
        return service.drawCard(player.getUuid());
    }
}
