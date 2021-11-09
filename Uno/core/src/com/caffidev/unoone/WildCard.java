package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.enums.CardType;

public class WildCard extends Card {
    public WildCard(CardType type){
        super(type, null, -1);
    }

    public String toString() {
        return this.getCardType().toString().toLowerCase() + " card";
    }
}
