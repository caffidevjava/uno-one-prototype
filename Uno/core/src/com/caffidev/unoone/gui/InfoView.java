package com.caffidev.unoone.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.caffidev.unoone.GameCardService;

public class InfoView {
    protected Stage stage;
    protected final GameCardService service;
    
    protected Label turnLabel;
    protected Label errorLabel;
    public InfoView(Stage stage, GameCardService service, BitmapFont font){
        turnLabel = new Label("fallback", new Label.LabelStyle(font, Color.WHITE));
        turnLabel.setPosition(stage.getWidth()/2f - turnLabel.getWidth()/2f , stage.getHeight() / 2f - turnLabel.getHeight() /2f + 150f);
        turnLabel.setAlignment(Align.center);
        stage.addActor(turnLabel);

        this.stage = stage;
        this.service = service;
    }
    
    public void playCardError(Integer errorCode){
        
    }
    
    public void update() {
        turnLabel.setText("It's turn of " + getPlayerName() + "!");
    }
    
    private String getPlayerName() {
        return service.getCurrentPlayer().getName();
    }
    
}
