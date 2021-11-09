package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.enums.CardColor;
import com.caffidev.unoone.enums.CardType;

public class NumberCard extends Card {
    public NumberCard(int number, CardColor color){
        super(CardType.NUMBER, color, number);
        if(CardUtils.validateColor(color)) {
        }
        if (CardUtils.validateNumber(number)) {
        }
    }

    public String toString(){
        return this.getCardColor().toString().toLowerCase()
                + " number " + this.getCardNumber()
                + " card";
    }
}
