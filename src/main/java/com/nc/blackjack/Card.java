package com.nc.blackjack;

/**
 * <h1>Card</h1>
 * Card represents a single playing card. Each
 * playing card will contain a {@link RANK} and a {@link SUIT}.
 *
 * @author Patrick Sharkey
 * @version 1.0
 * @since 1.0
 */
public class Card {

    //variable declarations
    public static final int ACE_HIGH = 11;
    private RANK rank;
    private SUIT suit;

    /**
     * RANK is the enum representing the thirteen possible ranks of card in a playing
     * deck as well as their corresponding values.
     * <ul>
     * <li>{@link #ACE}</li>
     * <li>{@link #TWO}</li>
     * <li>{@link #THREE}</li>
     * <li>{@link #FOUR}</li>
     * <li>{@link #FIVE}</li>
     * <li>{@link #SIX}</li>
     * <li>{@link #SEVEN}</li>
     * <li>{@link #EIGHT}</li>
     * <li>{@link #NINE}</li>
     * <li>{@link #TEN}</li>
     * <li>{@link #JACK}</li>
     * <li>{@link #QUEEN}</li>
     * <li>{@link #KING}</li>
     * </ul>
     * <p>
     * In addition to the textual enum name, each RANK has a String rank and int value.
     * The String rank is a shorthand representation of the RANK. The int value is the
     * value that rank is worth.
     *
     * @since 1.0
     */
    public enum RANK {

        /**
         * The singleton instance for the RANK ACE
         */
        ACE("A", 1),

        /**
         * The singleton instance for the RANK TWO
         */
        TWO("2", 2),

        /**
         * The singleton instance for the RANK THREE
         */
        THREE("3", 3),

        /**
         * The singleton instance for the RANK FOUR
         */
        FOUR("4", 4),

        /**
         * The singleton instance for the RANK FIVE
         */
        FIVE("5", 5),

        /**
         * The singleton instance for the RANK SIX
         */
        SIX("6", 6),

        /**
         * The singleton instance for the RANK SEVEN
         */
        SEVEN("7", 7),

        /**
         * The singleton instance for the RANK EIGHT
         */
        EIGHT("8", 8),

        /**
         * The singleton instance for the RANK NINE
         */
        NINE("9", 9),

        /**
         * The singleton instance for the RANK TEN
         */
        TEN("10", 10),

        /**
         * The singleton instance for the RANK JACK
         */
        JACK("J", 10),

        /**
         * The singleton instance for the RANK QUEEN
         */
        QUEEN("Q", 10),

        /**
         * The singleton instance for the RANK KING
         */
        KING("K", 10);

        private final String rank;
        private final int value;

        private RANK(String r, int v) {
            rank = r;
            value = v;
        }

        /**
         * @return returns the int value of the RANK. For example
         * TEN would return 10, ACE would return 1
         */
        public int getValue() {
            return this.value;
        }

        /**
         * @return returns a shorthand string representation of
         * the RANK. For example TEN would return "10" and "KING"
         * would return "K".
         */
        public String toString() {
            return this.rank;
        }
    }

    /**
     * SUIT is the enum representing the four suits of a card in a playing deck
     * <ul>
     * <li>{@link #HEART}</li>
     * <li>{@link #CLUB}</li>
     * <li>{@link #DIAMOND}</li>
     * <li>{@link #SPADE}</li>
     * </ul>
     *
     * @since 1.0
     */
    public enum SUIT {

        /**
         * The singleton instance for the SUIT HEART
         */
        HEART("H"),

        /**
         * The singleton instance for the SUIT CLUB
         */
        CLUB("C"),

        /**
         * The singleton instance for the SUIT DIAMOND
         */
        DIAMOND("D"),

        /**
         * The singleton instance for the SUIT SPADE
         */
        SPADE("S");

        private final String suit;
        private SUIT(String s) {
            suit = s;
        }

        /**
         * @return returns shorthand String representation of
         * the SUIT. For example ACE would return "A" and TEN
         * would return "10"
         */
        public String toString() {
            return this.suit;
        }
    }

    /**
     * Constructor - constructs a card initialized to the contents of the
     * specified SUIT and RANK.
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public Card(SUIT suit, RANK rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Check for equality between two cards
     * @param card card to check equality against
     * @return true if equals, false otherwise
     */
    public boolean equals(Card card) {

        if(card == null) {
            return false;
        }
        if(card.getRank() == getRank() && card.getSuit() == getSuit()) {
            return true;
        }
        return false;
    }

    /**
     * @return a string representation of the rank and suit
     */
    public String toString() {
        return rank.toString() + suit.toString();
    }

    /**
     * @return the RANK of the card
     */
    public RANK getRank() { return rank; }

    /**
     * @return the SUIT of the card
     */
    public SUIT getSuit() { return suit; }
}
