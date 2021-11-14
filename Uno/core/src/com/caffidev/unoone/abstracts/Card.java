package com.caffidev.unoone.abstracts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.caffidev.unoone.Game;
import com.caffidev.unoone.GameCardService;
import com.caffidev.unoone.Player;
import com.caffidev.unoone.enums.CardColor;
import com.caffidev.unoone.enums.CardType;

import java.security.Provider;
import java.util.UUID;

/** A card model, that has its own Image */
public abstract class Card extends ImageButton {
    private final CardType cardType;
    private final CardColor cardColor;
    private final Integer cardNumber; //if it's unique, has -1 number;

    public Card(CardType type, CardColor color, Integer number){
        super(new TextureRegionDrawable(new TextureRegion(new Texture(getTexturePath(type,color,number)))));
        getStyle().imageDisabled = new TextureRegionDrawable(new TextureRegion(new Texture("back.png")));
        cardType = type;
        cardColor = color;
        cardNumber = number;
    }
    
    public CardType getCardType() {return cardType;}

    public CardColor getCardColor() {return cardColor;}

    public Integer getCardNumber() {return cardNumber;}
    
    @Override
    public abstract String toString();
    
    public void show(boolean show){
        setDisabled(!show);
        clearListeners();
    }
    
    public void linkPlayer(final Player player, final GameCardService service){
        addListener(new ClickListener(){
           public void clicked(InputEvent event, float x, float y){
               playCard(player.getUuid(), Card.this, service);
           } 
        });
    }
    
    private void playCard(UUID playerId, Card card, GameCardService service){
        //Service.playCard?
    }
    
    //Static i.e has instance
    private static String getTexturePath(CardType type, CardColor color, Integer number){
        // Wildcards
        if(type == CardType.WILD_COLOR) return "black_wildcard.png";
        else if(type == CardType.WILD_PLUS_FOUR) return "black_+4.png";

        String colorStr = color.toString().toLowerCase();

        switch (type){
            case PLUS_TWO: return colorStr + "_+2.png";
            case REVERSE: return colorStr + "_reverse.png";
            case SKIP: return colorStr + "_skip.png";
            case NUMBER: return colorStr + "_" + number + ".png";
            default:
                Game.logger.error("Could not load assets. App will exit now.");
                Gdx.app.exit();
                return null;
        }
    }
}