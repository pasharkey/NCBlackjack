package com.nc.blackjack;

import com.nc.blackjack.com.nc.blackjack.exception.DeckException;
import java.util.Scanner;

/**
 * <h1>Card</h1>
 * Blackjack implements an application that allows the user to
 * play a simple game of blackjack through the console.
 *
 * @author Patrick Sharkey
 * @version 1.0
 * @since 1.0
 */
public class Blackjack {


    /**
     * Entry point into the blackjack application
     * @param args default {}
     */
    public static void main(String[] args) {

        Game game = new Game(); // create a new game of blackjack

        Scanner sc = new Scanner(System.in);
        System.out.print("Welcome to Blackjack! Please enter your player name: ");
        String name = sc.nextLine();  //get user input for username

        game.addPlayer(name); //add the player to the game

        newGame(game, sc);

    }

    /**
     * Start a new game of blackjack
     * @param game the game that will be started
     * @param sc the Scanner for the console output
     */
    private static void newGame(Game game, Scanner sc) {

        System.out.print("\nThe cards have been shuffled and the dealer is ready to deal. Press ENTER to begin. [enter] ");

        String input = sc.nextLine();

        try {
            game.dealCards();
            game.playerDecision(sc);    //let players decide to hit or stand
            game.dealerTurn(sc);        //execute dealer playing logic
            game.computeWinners();      //computer if the player has won

            System.out.println("\nWould you like to play again? Press y for YES and any other key for NO. [y/?]");

            input = sc.nextLine(); // get user input

            if (input.equalsIgnoreCase("y")) {
                game.reset();
                newGame(game, sc);
            } else {
                System.exit(0);
            }
        }
        //deck is in invalid state
        catch(DeckException de ) {
            System.out.println("An error in the game has occurred[" + de.getMessage() + "]. Restarting game.");
            game.reset();
            newGame(game, sc);
        }
    }


}
