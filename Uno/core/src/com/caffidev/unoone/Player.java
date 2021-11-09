package com.caffidev.unoone;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.abstracts.Entity;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private final String name;
    protected HorizontalGroup horizontalGroup = new HorizontalGroup();
    List<Card> cards = new ArrayList<>();
    
    public Player(String name){
        this.name = name;
        Game.stage.addActor(horizontalGroup);
    }
    
    public void drawCards(int number){
        for (int i = 0; i < number; i++) {
            drawOneCard();
        }
    }
    
    public void drawOneCard(){
        //todo: draw one card
    }
}
