package com.nc.blackjack.test;

import com.nc.blackjack.Card;
import com.nc.blackjack.Hand;
import com.nc.blackjack.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.nc.blackjack.Player.TYPE;

/**
 * Created by patrick.sharkey on 2/8/2016.
 */
public class PlayerTest {

    public Player player;
    public Player dealer;

    @Before
    public void initPlayerTest() {
        player = new Player("testuser", TYPE.PLAYER);
        dealer = new Player(Player.DEALER_USER, TYPE.DEALER);
    }

    @Test
    public void testPlayerConstructor() {

        String username = "testuser";
        Player p = new Player(username, TYPE.PLAYER);
        Player d = new Player(Player.DEALER_USER, TYPE.DEALER);

        Assert.assertNotNull(p);
        Assert.assertNotNull(d);

        Assert.assertEquals(p.getUsername(), username);
        Assert.assertEquals(p.getType(), TYPE.PLAYER);

        Assert.assertEquals(d.getUsername(), Player.DEALER_USER);
        Assert.assertEquals(d.getType(), TYPE.DEALER);

        Assert.assertNotNull(p.getHands());
        Assert.assertNotNull(d.getHands());
        Assert.assertEquals(p.getNumHands(), 1);
        Assert.assertEquals(d.getNumHands(), 1);
    }

    @Test
    public void testDefaultUsername() {
        Player p = new Player(null, TYPE.PLAYER);
        Assert.assertEquals(p.getUsername(), Player.DEFAULT_USER);
    }

    @Test
    public void testSetUsername() {
        String username = "newusername";
        String expected = "testuser";

        Assert.assertEquals(player.getUsername(), expected);
        player.setUsername(username);
        Assert.assertEquals(player.getUsername(), username);
        player.setUsername(expected);
    }

    @Test
    public void testResetHands() {

        //validate that a player should always have one hand
        Assert.assertEquals(player.getNumHands(), 1);
        player.resetHands();
        Assert.assertEquals(player.getNumHands(), 1);
    }

    @Test
    public void testToString() {

        String expectedPlayer = "PLAYER: testuser\n  Hand[0] Total[0] Result[IN_PROGRESS]";
        String expectedDealer = "DEALER: Dealer\n  Hand  Total[0]";

        Assert.assertEquals(player.toString(), expectedPlayer);
        Assert.assertEquals(dealer.toString(), expectedDealer);
    }
}
