package com.caffidev.unoone.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RotatingBackground extends Actor {
    protected Stage stage;
    protected TextureRegion backgroundTexture;
    protected float rotation;
    protected float rotationSpeed;
    
    public RotatingBackground(TextureRegion background, float rotationSpeed, Stage stage){
        backgroundTexture = background;
        this.rotationSpeed = rotationSpeed;
        rotation = 0;
        
        this.stage = stage;
    }
    
    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(backgroundTexture, 0, 0,stage.getWidth()/2f, stage.getHeight()/2f, stage.getWidth(), stage.getHeight(), 2.8f,2.8f,rotation);
        rotation += rotationSpeed * Gdx.graphics.getDeltaTime();
    }
}
