package com.caffidev.unoone.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.caffidev.unoone.GameCardService;

import javax.imageio.plugins.tiff.BaselineTIFFTagSet;

public class InfoView {
    private static final int SHOW_ERROR_TIME = 2000;
    protected Stage stage;
    protected final GameCardService service;
    
    protected Label turnLabel;
    protected Label errorLabel;
    
    public long sinceChange;
    protected long lastTimeCounted;
    
    public InfoView(Stage stage, GameCardService service, BitmapFont font){
        turnLabel = new Label("fallback", new Label.LabelStyle(font, Color.WHITE));
        turnLabel.setPosition(stage.getWidth()/2f - turnLabel.getWidth()/2f , stage.getHeight() / 2f - turnLabel.getHeight() /2f + 150f);
        turnLabel.setAlignment(Align.center);
        stage.addActor(turnLabel);

        errorLabel = new Label("error", new Label.LabelStyle(font, Color.RED));
        errorLabel.setPosition(stage.getWidth()/2f - errorLabel.getWidth()/2f , stage.getHeight() / 2f - errorLabel.getHeight() /2f + 100f);
        errorLabel.setAlignment(Align.center);
        errorLabel.setVisible(false);
        
        stage.addActor(errorLabel);
        
        sinceChange = -1;
        lastTimeCounted = 0;
        
        this.stage = stage;
        this.service = service;
    }
    
    public void updatePlayCardError(Integer errorCode){
        /** Returns -4, if unknown error
         *  Returns -3, if card is invalid 
         *  Returns -2, if owner of playerID is not current player
         *  Returns -1, if user does not have card
         *  Returns zero, if successful
         */
        switch (errorCode) {
            case -4 -> { updateError("Unknown error. Maybe someone is cheating?");}
            case -3 -> { updateError("Card is invalid. (Color/Type)");}
            case -2 -> { updateError("It's not your turn.");}
            case -1 -> { updateError("You don't have this card.");}
        }
    }
    
    public void updateDrawCardError(Integer errorCode){
        switch (errorCode) {
            case -2 -> { updateError("Unknown error.");}
            case -1 -> { updateError("It's not your turn.");}
            case 1 -> {
                //not error, todo: place it in another place
                updateError("Played drawn card!");
            }
        }
    }
    
    public void updateError(){
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();
        sinceChange += delta;
        
        if(sinceChange >= 1000) {
            errorLabel.setVisible(false);
            
            sinceChange = -1;
        } 
    }
    
    public void updateError(String error) {
        lastTimeCounted = TimeUtils.millis();
        sinceChange = 0;

        errorLabel.setVisible(true);
        errorLabel.setText(error);
    }
    
    private void updateError(String error, long time, long lastTimeCounted) {
        
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();
        time += delta;
        
        if(time >= SHOW_ERROR_TIME) errorLabel.setVisible(false);
        else updateError(error, time, lastTimeCounted);
    }
    
    public void updateTurn() {
        turnLabel.setText("It's turn of " + getPlayerName() + "!");
    }
    
    private String getPlayerName() {
        return service.getCurrentPlayer().getName();
    }
    
}
