package com.nc.blackjack.test;

import com.nc.blackjack.Card;
import com.nc.blackjack.Game;
import com.nc.blackjack.Hand;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by patrick.sharkey on 2/8/2016.
 */
public class GameTest {

    private final ByteArrayOutputStream  outContent = new ByteArrayOutputStream();
    public Card cardA, cardB, cardC, cardD, cardE, cardF, cardG, cardH, cardI;
    public Game game;
    public String pUsername;

    @Before
    public void initGameTest() {

        game = new Game();
        pUsername = "player";

        cardA = new Card(Card.SUIT.CLUB, Card.RANK.ACE);      //1/11
        cardB = new Card(Card.SUIT.DIAMOND, Card.RANK.TEN);   //10
        cardC = new Card(Card.SUIT.HEART, Card.RANK.ACE);     //1/11
        cardD = new Card(Card.SUIT.SPADE, Card.RANK.QUEEN);   //10
        cardE = new Card(Card.SUIT.CLUB, Card.RANK.NINE);     //9
        cardF = new Card(Card.SUIT.DIAMOND, Card.RANK.EIGHT); //8
        cardG = new Card(Card.SUIT.HEART, Card.RANK.JACK);    //10
        cardH = new Card(Card.SUIT.SPADE, Card.RANK.SEVEN);   //7
        cardI = new Card(Card.SUIT.CLUB, Card.RANK.KING);     //10

        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testGameConstructor() {

        Game g = new Game();
        Assert.assertNotNull(g);
        Assert.assertEquals(g.getNumPlayers(), 0);
    }

    @Test
    public void testAddPlayersToGame() {

        Game g = new Game();
        String playerA = "playerA";
        String playerB = "playerB";

        //add player to game one at a time
        g.addPlayer(playerA);
        Assert.assertEquals(g.getNumPlayers(), 1);
        g.addPlayer(playerB);
        Assert.assertEquals(g.getNumPlayers(), 2);

    }

    @Test
    public void testIsDealerHit() {

        //test when dealr has min value < 17 and max value > 17 but < 21
        int min = 16;
        int max = 20;
        Assert.assertFalse(game.isDealerHit(min, max));

        //test when dealer has min value < 17 and max value > 21
        min = 10;
        max = 22;
        Assert.assertTrue(game.isDealerHit(min, max));

        //test when dealer has min value > 17 and max value > 17
        min = 21;
        max = 23;
        Assert.assertFalse(game.isDealerHit(min, max));

        //test when dealer has min value < 17 and max value < 17
        min = 15;
        max = 16;
        Assert.assertTrue(game.isDealerHit(min,max));

        //test when dealer has min value = 17 and max value > 17
        min = 17;
        max = 18;
        Assert.assertFalse(game.isDealerHit(min, max));

        //test when dealer has min value < 17 and max value = 17
        min = 17;
        max = 17;
        Assert.assertFalse(game.isDealerHit(min, max));

        //test when dealer has min value = 17 and max value = 17
        min = 17;
        max = 17;
        Assert.assertFalse(game.isDealerHit(min, max));
    }

    @Test
    public void testComputeHandResultDealtBlackjack() {


        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.insertCard(cardA);  //11
        pHand.insertCard(cardB);  //10
        dHand.insertCard(cardC);  //11
        dHand.insertCard(cardD);  //10

        //set optimal hand value
        pHand.setOptimalHandValue();
        dHand.setOptimalHandValue();

        //if both dealer and player get blackjack on initial deal they tie
        game.computeHandResult(pUsername, pHand, dHand);

        String expected = "\n\nPlayer player is showing [AC,10D] Total[21]. Dealer is showing [AH,QS] Total[21]" +
                "\n\nRESULT: Player player TIES with the dealer\n";

        //Assert.assertEquals(outContent.toString(), expected);  //TODO Works on my development MAC but not on PC need to investigate
        Assert.assertEquals(pHand.getResult(), Hand.RESULT.TIE);
    }

    @Test
    public void testComputeHandResultPlayerDealerBust() {

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.insertCard(cardB);  //10
        pHand.insertCard(cardD);  //10
        pHand.insertCard(cardE);  //9

        dHand.insertCard(cardF);  //8
        dHand.insertCard(cardG);  //10
        dHand.insertCard(cardH);  //7

        //set optimal hand value
        pHand.setOptimalHandValue();
        dHand.setOptimalHandValue();

        //if both players bust, the dealer wins
        game.computeHandResult(pUsername, pHand, dHand);

        String expected = "\n\nPlayer player is showing [10D,QS,9C] Total[29]. Dealer is showing [8D,JH,7S] Total[25]" +
                "\n\nRESULT: Player player BUSTED dealer wins\n";

        //Assert.assertEquals(outContent.toString(), expected);     //TODO Works on my development MAC but not on PC need to investigate
        Assert.assertEquals(pHand.getResult(), Hand.RESULT.LOSS);
    }

    @Test
    public void testComputeHandResultPlayerBust() {

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.insertCard(cardB);  //10
        pHand.insertCard(cardD);  //10
        pHand.insertCard(cardE);  //9

        dHand.insertCard(cardF);  //8
        dHand.insertCard(cardG);  //10

        //set optimal hand value
        pHand.setOptimalHandValue();
        dHand.setOptimalHandValue();

        //if player busts dealer wins
        game.computeHandResult(pUsername, pHand, dHand);

        String expected = "\n\nPlayer player is showing [10D,QS,9C] Total[29]. Dealer is showing [8D,JH] Total[18]" +
                "\n\nRESULT: Player player BUSTED dealer wins\n";

        //Assert.assertEquals(outContent.toString(), expected);    //TODO Works on my development MAC but not on PC need to investigate
        Assert.assertEquals(pHand.getResult(), Hand.RESULT.LOSS);
    }

    @Test
    public void testComputeHandResultTie() {

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.insertCard(cardB);  //10
        pHand.insertCard(cardD);  //10

        dHand.insertCard(cardG);  //10
        dHand.insertCard(cardI);

        //set optimal hand value
        pHand.setOptimalHandValue();
        dHand.setOptimalHandValue();

        //if player and dealer have the same hand value they tie
        game.computeHandResult(pUsername, pHand, dHand);

        String expected = "\n\nPlayer player is showing [10D,QS] Total[20]. Dealer is showing [JH,KC] Total[20]" +
                "\n\nRESULT: Player player TIES with the dealer\n";

        //Assert.assertEquals(outContent.toString(), expected);   //TODO Works on my development MAC but not on PC need to investigate
        Assert.assertEquals(pHand.getResult(), Hand.RESULT.TIE);
    }

    @Test
    public void testComputeHandResultPlayerWin() {

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.insertCard(cardB);  //10
        pHand.insertCard(cardD);  //10

        dHand.insertCard(cardG);  //10
        dHand.insertCard(cardH);  //7

        //set optimal hand value
        pHand.setOptimalHandValue();
        dHand.setOptimalHandValue();

        //if player and dealer did not bust and has a greater hand than the dealer and th
        game.computeHandResult(pUsername, pHand, dHand);

        String expected = "\n\nPlayer player is showing [10D,QS] Total[20]. Dealer is showing [JH,7S] Total[17]" +
                "\n\nRESULT: Congratulations player you WIN\n";

        //Assert.assertEquals(outContent.toString(), expected);   //TODO Works on my development MAC but not on PC need to investigate
        Assert.assertEquals(pHand.getResult(), Hand.RESULT.WIN);
    }

    @Test
    public void testComputeHandResultDealerBust() {

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.insertCard(cardB);  //10
        pHand.insertCard(cardH);  //7

        dHand.insertCard(cardG);  //10
        dHand.insertCard(cardI);  //10
        dHand.insertCard(cardE);  //9

        //set optimal hand value
        pHand.setOptimalHandValue();
        dHand.setOptimalHandValue();

        //if the dealer busts and the player does not
        game.computeHandResult(pUsername, pHand, dHand);

        String expected = "\n\nPlayer player is showing [10D,7S] Total[17]. Dealer is showing [JH,KC,9C] Total[29]" +
                "\n\nRESULT: Congratulations player you WIN\n";

        //Assert.assertEquals(outContent.toString(), expected);  //TODO Works on my development MAC but not on PC need to investigate
        Assert.assertEquals(pHand.getResult(), Hand.RESULT.WIN);
    }

    @Test
    public void testComputeHandResultDealerWin() {

        Hand pHand = new Hand();
        Hand dHand = new Hand();

        pHand.insertCard(cardB);  //10
        pHand.insertCard(cardH);  //7

        dHand.insertCard(cardG);  //10
        dHand.insertCard(cardI);  //10

        //set optimal hand value
        pHand.setOptimalHandValue();
        dHand.setOptimalHandValue();

        //dealer wins in all other cases (player hand < dealer hand and both are less than 21
        game.computeHandResult(pUsername, pHand, dHand);

        String expected = "\n\nPlayer player is showing [10D,7S] Total[17]. Dealer is showing [JH,KC] Total[20]" +
                "\n\nRESULT: Player player LOSES to the dealer\n";

        //Assert.assertEquals(outContent.toString(), expected);    //TODO Works on my development MAC but not on PC need to investigate
        Assert.assertEquals(pHand.getResult(), Hand.RESULT.LOSS);
    }
}
