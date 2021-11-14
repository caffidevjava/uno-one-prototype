package com.caffidev.unoone.gui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.caffidev.unoone.Game;
import com.caffidev.unoone.GameCardService;
import com.caffidev.unoone.abstracts.Card;

import java.util.ArrayList;
import java.util.List;

public class DrawPileView {
    private static final float OFFSET_RANGE = 10f;
    private static final Integer LIMIT_OF_CARDS = 20;
    private Stage stage;
    private GameCardService service;
    
    List<Card> drawPileCards;
    Group drawPile;
    
    public DrawPileView(Stage stage, GameCardService service){
        drawPile = new Group();
        this.stage = stage;
        this.service = service;
        stage.addActor(drawPile);
        
        drawPile.setPosition(stage.getWidth() /2f, stage.getHeight() / 2f);
        
        drawPileCards = new ArrayList<Card>();
        var card = service.peekTopCard();
        drawPileCards.add(card);
        card.align(Align.center);
        card.setPosition(drawPile.getWidth() /2f - card.getWidth() /2f,
                drawPile.getHeight() /2f - card.getHeight() /2);
        drawPile.addActor(card);
    }
    
    public void putCard(Card card){
        drawPileCards.add(card);
        float offsetX = (float) ((Math.random() * OFFSET_RANGE * 2) - OFFSET_RANGE);
        float offsetY = (float) ((Math.random() * OFFSET_RANGE * 2) - OFFSET_RANGE);
        card.setPosition(drawPile.getWidth() /2f - card.getWidth() /2f + offsetX, 
                drawPile.getHeight() /2f - card.getHeight() /2 + offsetY);
        drawPile.addActor(card);
        if(drawPile.getChildren().size > LIMIT_OF_CARDS) {
            drawPile.removeActorAt(0, true);
        }
    }
}
