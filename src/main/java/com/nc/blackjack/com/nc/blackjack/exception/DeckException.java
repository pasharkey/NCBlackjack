package com.nc.blackjack.com.nc.blackjack.exception;

/**
 * <h1>DeckException</h1>
 * Custom exception class used for exceptions occuring with the
 * {@link com.nc.blackjack.Deck} class.
 *
 * @author Patrick Sharkey
 * @version 1.0
 * @since 1.0
 */
public class DeckException extends Exception {
    public DeckException() { super(); }
    public DeckException(String message) { super(message); }
    public DeckException(String message, Throwable cause) { super(message, cause); }
    public DeckException(Throwable cause) { super(cause); }

    @Override
    public String toString() { return super.toString(); }

    @Override
    public String getMessage() { return super.getMessage(); }


}
