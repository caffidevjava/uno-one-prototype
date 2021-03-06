package com.caffidev.unoone.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.caffidev.unoone.Game;
import com.caffidev.unoone.GameCardService;

public class PackView {
    protected Stage stage;
    protected final GameCardService service;
    
    protected ImageButton cardButton;
    protected Label amountLabel;
    
    public PackView(Stage stage, GameCardService service, BitmapFont font){
        cardButton = new ImageButton(
                new TextureRegionDrawable(
                        new Texture(Gdx.files.internal("back.png"))));
        cardButton.align(Align.center);
        cardButton.setPosition(stage.getWidth() / 4f, stage.getHeight() / 2f - cardButton.getHeight() /2f);
        stage.addActor(cardButton);
        
        amountLabel = new Label("fallback", new Label.LabelStyle(font, Color.WHITE));
        amountLabel.setAlignment(Align.center);
        amountLabel.setPosition(cardButton.getWidth()/2f - amountLabel.getWidth()/2f,
                cardButton.getHeight() / 2f - amountLabel.getHeight()/2f );
        
        cardButton.addActor(amountLabel); //children
        
        this.stage = stage;
        this.service = service;
        addClickedListener();
    }
    
    public void addClickedListener(){
        cardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                for (PlayerView playerView : Game.playerViews) {
                    if(playerView.player.getUuid().toString().equals(service.getCurrentPlayer().getUuid().toString())){
                        Integer code = playerView.drawCard();
                        Game.infoView.updateDrawCardError(code);
                        
                        if(code == 0 || code == 1) Game.soundService.cardDrawSound.play();
                        if(code == 1) {
                            var lastDrawnCard = Game.gameService.getLastDrawnCard();
                            
                            if(lastDrawnCard != null) {
                                Game.drawPileView.putCard(lastDrawnCard);
                                Game.soundService.cardPlaySound.play();
                            }
                        }
                        
                        playerView.update();
                        Game.infoView.updateTurn();
                        return;
                    }
                }
                throw new IllegalStateException("Current player does not exist in frontend players");
            }});
        
        amountLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                cardButton.getClickListener().clicked(event, x, y);
            }
        });
    }
    
    public void update() {
        amountLabel.setText(getNumberOfCards() + "\ncards");
    }
    
    private Integer getNumberOfCards() {
        return service.getCardCount();
    }
    
}
