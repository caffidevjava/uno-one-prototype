package com.caffidev.unoone;

import com.caffidev.unoone.abstracts.Card;
import com.caffidev.unoone.enums.CardColor;
import com.caffidev.unoone.enums.CardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Card deck has:
 * 4 colors; e.g 
 * Numbers: 4 zeros; 9*4*2=72(+4)=76 cards;
 * Skip, reverse + plus 2: 2^3*3=24 cards;
 * Wild cards: 8 cards
 * Total = 76+32+8=108 cards
 * https://commons.wikimedia.org/wiki/File:UNO_cards_deck.svg?uselang=ru
 */
// Тут можно было бы хорошо объяснить про простоту
public class CardDeck {
    private final List<Card> cards = new ArrayList<>();

    public CardDeck(){
        createCards();

        Game.logger.debug("Created a starter deck of cards.");
    }

    public List<Card> getCards() {
        return cards;
    }

    private void createCards(){
        createNumberCards();
        createActionCards();
        createWildCards();
    }
    
    private void createNumberCards() {
        for (CardColor color : CardColor.values()) {
            // There is only 1 repr of 0 card of each card
            cards.add(new NumberCard(0, color));
            
            for (int number = 1; number <= 9; number++) {
                // 2 reprs of other cards
                cards.add(new NumberCard(number, color));
                cards.add(new NumberCard(number, color));
            }
        }
    }
    
    private void createActionCards() {
        for (CardColor color : CardColor.values()) {
            for (int i = 0; i < 2; i++) {
                cards.add(new ActionCard(CardType.SKIP, color));
                cards.add(new ActionCard(CardType.REVERSE, color));
                cards.add(new ActionCard(CardType.PLUS_TWO, color));
            }
        }
    }

    private void createWildCards() {
        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard(CardType.WILD_COLOR));
            cards.add(new WildCard(CardType.PLUS_FOUR));
        }
    }
}
