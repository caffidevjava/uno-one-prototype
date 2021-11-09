package com.caffidev.unoone;

import com.caffidev.unoone.CardUtils;
import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.enums.CardColor;
import com.caffidev.unoone.enums.CardType;

public class ActionCard extends Card {
    public ActionCard(CardType type, CardColor color){
        super(type,color,-1);
        //todo: validate
    }
    
    public String toString(){
        return this.getCardColor().toString().toLowerCase()
                + " " + this.getCardType().toString().toLowerCase()
                + " card";
    }
    
}
