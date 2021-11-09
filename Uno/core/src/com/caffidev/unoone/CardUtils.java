package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.enums.CardColor;
import com.caffidev.unoone.enums.CardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardUtils {
    
    public static boolean isValidNumberCard() {return true;}
    
    public static List<Card> shuffleCards(List<Card> cards){
        Collections.shuffle(cards);
        return cards;
    }
    
    
    /** Returns true if color is not null */
    public static boolean validateColor(CardColor color){
        if (color == null) return false;
        return true;
    }

    /** Returns true if it's a valid number for Uno */
    public static boolean validateNumber(int number){
        if(number < 0 || number > 9) {
            return false;
        } return true;
    }
    
    /** Returns true if it's a wild card, false if not */
    public static boolean isWildCard(Card card) {
        return card.getCardType() == CardType.WILD_COLOR
                || card.getCardType() == CardType.PLUS_FOUR;
    }
}
