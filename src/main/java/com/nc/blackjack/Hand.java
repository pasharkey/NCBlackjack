package com.nc.blackjack;

import java.util.List;
import java.util.ArrayList;

/**
 * <h1>Hand</h1>
 * Hand represents a single hand of playing cards.
 * <br>
 * A hand contains a list of {@link Card} objects as well as a
 * {@link RESULT} of the hand, and a value for the hand.
 *
 * @author Patrick Sharkey
 * @version 1.0
 * @since 1.0
 */
public class Hand {

    //variable declarations
    public static final int BLACKJACK = 21;
    public static final int DEALER_STAND = 17;
    private List<Card> hand;
    private boolean hasACE = false;
    private int handValue = 0;
    private int minHandValue = 0;
    private int maxHandValue = 0;
    private RESULT result;

    /**
     * RESULT is the enum representing the different types of hand
     * results.
     * <ul>
     * <li>{@link #TIE}</li>
     * <li>{@link #LOSS}</li>
     * <li>{@link #WIN}</li>
     * <li>{@link #IN_PROGRESS}</li>
     * </ul>
     */
    public enum RESULT {TIE, LOSS, WIN, IN_PROGRESS}

    /**
     * Constructor - constructs an empty hand and initializes
     * the result to in progress.
     */
    public Hand() {
        hand = new ArrayList<Card>();
        setResult(RESULT.IN_PROGRESS);
    }

    /**
     * Insert a card into the hand. min and max hand values will be
     * updated with the card value. If the card is an ACE increment max
     * value with 11 and min value with 1.
     * @param card the card that will be inserted into the hand
     */
    public void insertCard(Card card) {

        if(card == null ) {
            return;
        }

        //add the card to the hand
        hand.add(card);

        int rankValue = card.getRank().getValue(); //get the rank value

        /*
            If card is an ACE and the hand currently does not already have an
            ACE, then treat the ACE's rank value as ACE_HIGH
         */
        if(card.getRank() == Card.RANK.ACE && !hasACE ) {
            setMaxHandValue(getMaxHandValue() + card.ACE_HIGH);
            hasACE = true;
        } else {
            setMaxHandValue(getMaxHandValue() + rankValue);
        }

        setMinHandValue(getMinHandValue() + rankValue);
    }

    /**
     * Set the value of a hand to the optimal value for blackjack by comparing
     * the min hand value and max hand value.
     */
    public void setOptimalHandValue() {
        setHandValue(getOptimalHandValue(minHandValue, maxHandValue));
    }

    /**
     * Calculate which hand value is optimal for blackjack and set that value
     * as the hand value.
     * @param minHandValue the min value of the hand
     * @param maxHandValue the max value of the hand
     * @return the hand value that is optimal for a game of blackjack
     */
    private int getOptimalHandValue(int minHandValue, int maxHandValue) {

        //if min value is a BUST then max value must be a bust
        if(minHandValue > Hand.BLACKJACK ) {
            return minHandValue;
        }
        //if maxValue is BUST then always use min value
        else if( (Hand.BLACKJACK - maxHandValue) < 0 ) {
            return minHandValue;
        }

        return maxHandValue;  //always use max value in all other cases
    }

    /**
     * Check if the value is blackjack.
     * @param value the value to check for blackjack
     * @return true if blackjack, false otherwise
     */
    public static boolean isBlackjack(int value) {
        if(value == BLACKJACK) {
            return true;
        }
        return false;
    }

    /**
     * Check if the value of is greater than blackjack
     * aka a BUST.
     * @param value the value to check for greater than blackjack
     * @return true if greater than blackjack, false otherwise
     */
    public static boolean isBust(int value) {
        if(value > BLACKJACK ) {
            return true;
        }
        return false;
    }

    /**
     * @return string return a string of the all possible values of
     * a current hand
     */
    public String currentHandStateTotal() {

        if( (minHandValue != maxHandValue) && (minHandValue < BLACKJACK) && (maxHandValue < BLACKJACK) ) {
            return minHandValue + "/" + maxHandValue;
        }
        else {
            return Integer.toString(minHandValue);
        }
    }

    /**
     * @return a string representation of the hand
     */
    public String toString() {

        StringBuilder sb = new StringBuilder();
        String delimiter = "";

        for(int i = 0; i < hand.size(); i++ ) {
            sb.append(delimiter + hand.get(i).toString() );
            delimiter = ",";
        }

        return sb.toString();
    }

    /**
     * @return a string representation of a hand with only
     * the first hand visible
     */
    public String toStringHidden() {

        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        boolean hidden = false;

        for(int i = 0; i < hand.size(); i++ ) {
            if(!hidden) {
                sb.append(delimiter + hand.get(i).toString());
                hidden = true;
            } else {
                sb.append(delimiter + "XX");  //display card as XX
            }
            delimiter = ",";
        }

        return sb.toString();
    }

    /**
     * @return the value of the hand
     */
    public int getHandValue() { return handValue; }

    /**
     * Set the hand value
     * @param handValue the value that will be set
     */
    private void setHandValue(int handValue) { this.handValue = handValue; }

    /**
     * @return the min hand value
     */
    public int getMinHandValue() { return minHandValue; }

    /**
     * Set the min hand value
     * @param minHandValue the value that will be set
     */
    public void setMinHandValue(int minHandValue) { this.minHandValue = minHandValue;}

    /**
     * @return the max hand value
     */
    public int getMaxHandValue() { return maxHandValue; }

    /**
     * Set the max hand value
     * @param maxHandValue the value that will be set
     */
    public void setMaxHandValue(int maxHandValue) { this.maxHandValue = maxHandValue;}

    /**
     * Set the result of the hand
     * @param result the result that will be set
     */
    public void setResult(RESULT result) { this.result = result; }

    /**
     * @return the result of the hand
     */
    public RESULT getResult() { return result; }

    /**
     * @return the size of the hand
     */
    public int getSize() { return hand.size(); }

}
