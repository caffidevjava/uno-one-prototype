package com.caffidev.unoone.cards;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.enums.CardColor;
import com.caffidev.unoone.enums.CardType;

public class ActionCard extends Card {
    public ActionCard(CardType type, CardColor color){
        super(type,color,-1);
    }

    public String toString(){
        return this.getCardColor().toString().toLowerCase()
                + " " + this.getCardType().toString().toLowerCase()
                + " card";
    }

}
