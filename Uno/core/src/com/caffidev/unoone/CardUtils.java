package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.cards.NumberCard;
import com.caffidev.unoone.enums.CardColor;
import com.caffidev.unoone.enums.CardType;

import java.util.UUID;

public class CardUtils {
    
    /** Returns -2, if owner of playerID is not current player
     *  Returns -1, if user does not have card
     *  Returns zero, if successful
     */
    public static Integer validatePlayedCard(UUID playerID, Player currentPlayer, Card card){
        if(!currentPlayer.getUuid().equals(playerID)) {
            return -2;
        }
        if (!currentPlayer.hasHandCard(card)) {
            return -1;
        }
        return 0;
    }
    
    public static Boolean validateColor(CardColor color) {
        if (color == null) {
            return false;
        }
        return true;
    }

    public static Boolean validateNumber(int number) {
        if (number < 0 || number > 9) {
            return false;
        }
        return true;
    }

    public static Boolean validateActionType(CardType type) {
        if (type == CardType.SKIP || type == CardType.REVERSE || type == CardType.PLUS_TWO) {
            return true;
        }

        return false;
    }

    public static boolean isValidNumberCard(Card topCard, Card playedCard) {
        if(isSameColor(topCard, playedCard)){
            return true;
        }

        if (topCard.getCardType() == CardType.NUMBER) {
            return  topCard.getCardNumber().equals(playedCard.getCardNumber());
        }

        return false;
    }

    public static boolean isValidActionCard(Card topCard, Card playedCard) {
        if(isSameColor(topCard, playedCard)){
            return true;
        }

        return topCard.getCardType() == playedCard.getCardType();
    }

    public static boolean isValidWildCard(Card playedCard) {
        return playedCard.getCardColor() == null;
    }

    public static boolean isWildCard(Card card) {
        return card.getCardType() == CardType.WILD_COLOR || card.getCardType() == CardType.WILD_PLUS_FOUR;
    }
    
    private static boolean isSameColor(Card topCard, Card playedCard){
        return topCard.getCardColor() == playedCard.getCardColor();
    }
}
