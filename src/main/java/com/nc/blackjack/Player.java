package com.nc.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Player</h1>
 * Player represents a single player in a {@link Game}
 * A player will contain a {@link #username}, a {@link TYPE}, and a list
 * of {@link Hand} to represent all the hands the player is holding.
 *
 * @author Patrick Sharkey
 * @version 1.0
 * @since 1.0
 */
public class Player {

    //variable declarations
    public static final String DEFAULT_USER = "Default";
    public static final String DEALER_USER = "Dealer";

    private List<Hand> hands;
    private String username;
    private TYPE type;

    /**
     * TYPE is the enum representing the different types of players
     * <ul>
     * <li>{@link #DEALER}</li>
     * <li>{@link #PLAYER}</li>
     * </ul>
     */
    public enum TYPE { DEALER, PLAYER }

    /**
     * Constructor - constructs a player initialized to the contents of the
     * specified username and {@link TYPE}
     * @param username the player username
     * @param type the type of player
     */
    public Player(String username, TYPE type) {
        setUsername(username);
        setType(type);

        //create empty hand for initialization
        hands = new ArrayList<Hand>();
        insertHand(new Hand());
    }

    /**
     * Insert the specified hand into the hand list
     * @param hand the hand to add to the list
     */
    private void insertHand(Hand hand) {
        if(hand != null) {
            hands.add(hand);
        }
    }

    /**
     * Insert a card into a specified index of the hand list
     * @param index the index in the hand list
     * @param card the card that will be inserted
     *
    public void insertCard(int index, Card card) {

        //validate the hand exists in the array
        if(index <= hands.size() && card != null ) {
            Hand h = hands.get(index);
            h.insertCard(card);
        }
    } */

    /**
     * Reset and clear all of the hands of a player. Player will
     * be left with a single empty hand.
     */
    public void resetHands() {
        hands.clear();
        insertHand(new Hand());
    }

    /**
     * @return a string representation of each hand, the current total, and the current
     * result of the hand
     */
    public String toString() {

        StringBuilder sb = new StringBuilder();

        //print player type and username
        sb.append(getType().toString() + ": " + username);

        //if player is of type dealer only get the first hand
        if(getType() == TYPE.DEALER ) {
            sb.append("\n  Hand " + hands.get(0).toString() + " Total[" +
                    hands.get(0).currentHandStateTotal() + "]");
        }
        else {
            for (int i = 0; i < hands.size(); i++) {
                sb.append("\n  Hand[" + i + "]" + hands.get(i).toString() + " Total[" +
                        hands.get(i).currentHandStateTotal() + "] Result[" + hands.get(i).getResult() + "]");
            }
        }

        return sb.toString();
    }

    /**
     * @return the username of the player
     */
    public String getUsername() { return username; }

    /**
     * Set the username of a player
     * @param username the username that will be set
     */
    public void setUsername(String username ) {
        this.username = username != null ? username : DEFAULT_USER;
    }

    /**
     * @return the type of player
     */
    public TYPE getType() { return type; }

    /**
     * Set the type of the player
     * @param type the type that will be set
     */
    public void setType( TYPE type ) { this.type = type; }

    /**
     * @return a list of all the hands a player is holding
     */
    public List<Hand> getHands() { return hands; }

    /**
     * @return the number of hands a player is holding
     */
    public int getNumHands() { return hands.size(); }

}
