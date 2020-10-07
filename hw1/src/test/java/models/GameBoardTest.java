package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBoardTest {

  GameBoard gb = new GameBoard();

  @BeforeEach
  public void initGame() {
    gb.setPlayer1('X');
    gb.setPlayer2('O');
    gb.startGame();
  }
  
  /**
   * Startover the game agter each example game.
  */
  @AfterEach
  public void clearGame() {
    gb.startOver();
    assertFalse(gb.isGameStarted());
    assertEquals(gb.getPlayer1(), null);
    assertEquals(gb.getPlayer2(), null);
    assertFalse(gb.getIsDraw());
    assertEquals(gb.getWinner(), 0);
    assertEquals(gb.getTurn(), 0);
  }

  /**
   * test startgame.
  */
  @Test
  void testStartGame() {
    assertTrue(gb.isGameStarted());
  }

  /**
   * Game1 to test.
  */
  @Test
  void testGame1() {
    Player p1 = gb.getPlayer1();
    assertEquals('X', p1.getType());
    Player p2 = gb.getPlayer2();
    assertEquals('O', p2.getType());
    assertEquals(1, p1.getId());
    assertEquals(2, p2.getId());
    Move m1 = new Move(p1, 0, 0);
    assertTrue(gb.tryMove(m1));
    Move m2 = new Move(p1, 1, 1);
    assertFalse(gb.tryMove(m2));
    Move m3 = new Move(p2, 1, 1);
    assertTrue(gb.tryMove(m3));
    Move m4 = new Move(p1, 0, 1);
    assertTrue(gb.tryMove(m4));
    Move m5 = new Move(p2, 1, 2);
    assertTrue(gb.tryMove(m5));
    Move m6 = new Move(p1, 0, 2);
    assertTrue(gb.tryMove(m6));
    assertEquals(gb.getWinner(), 1);
  }
  
  /**
   * Game2 to test.
  */
  @Test
  void testGame2() {
    Player p1 = gb.getPlayer1();
    Player p2 = gb.getPlayer2();
    Move m1 = new Move(p2, 0, 0);
    assertFalse(gb.tryMove(m1));
    Move m2 = new Move(p1, 2, 2);
    assertTrue(gb.tryMove(m2));
    Move m3 = new Move(p2, 1, 1);
    assertTrue(gb.tryMove(m3));
    Move m = new Move(p1, 1, 1);
    assertFalse(gb.tryMove(m));
    Move m4 = new Move(p1, 0, 2);
    assertTrue(gb.tryMove(m4));
    Move m5 = new Move(p2, 0, 1);
    assertTrue(gb.tryMove(m5));
    Move m6 = new Move(p1, 0, 0);
    assertTrue(gb.tryMove(m6));
    Move m7 = new Move(p2, 2, 1);
    assertTrue(gb.tryMove(m7));
    Move m8 = new  Move(p1, 0, 2);
    assertFalse(gb.tryMove(m8));
    assertEquals(gb.getWinner(), 2);
  }
  
  /**
   * Game3 to test.
  */
  @Test
  void testGame3() {
    Player p1 = gb.getPlayer1();
    Player p2 = gb.getPlayer2();

    Move m1 = new Move(p1, 1, 1);
    assertTrue(gb.tryMove(m1));
    Move m2 = new Move(p2, 1, 0);
    assertTrue(gb.tryMove(m2));
    Move m3 = new Move(p1, 0, 0);
    assertTrue(gb.tryMove(m3));
    Move m4 = new Move(p2, 2, 0);
    assertTrue(gb.tryMove(m4));
    Move m5 = new Move(p1, 2, 2);
    assertTrue(gb.tryMove(m5));
    assertEquals(gb.getWinner(), 1);
  }
  
  /**
   * Game4 to test.
  */
  @Test
  void testGame4() {
    Player p1 = gb.getPlayer1();
    Player p2 = gb.getPlayer2();

    Move m1 = new Move(p1, 1, 1);
    assertTrue(gb.tryMove(m1));
    Move m2 = new Move(p2, 1, 0);
    assertTrue(gb.tryMove(m2));
    Move m3 = new Move(p1, 0, 2);
    assertTrue(gb.tryMove(m3));
    Move m4 = new Move(p2, 2, 2);
    assertTrue(gb.tryMove(m4));
    Move m5 = new Move(p1, 2, 0);
    assertTrue(gb.tryMove(m5));
    assertEquals(gb.getWinner(), 1);
  }

  /**
   * Game5 to test.
  */
  @Test
  void testGame5() {
    Player p1 = gb.getPlayer1();
    Player p2 = gb.getPlayer2();

    Move m1 = new Move(p1, 1, 1);
    assertTrue(gb.tryMove(m1));
    Move m2 = new Move(p2, 1, 0);
    assertTrue(gb.tryMove(m2));
    Move m3 = new Move(p1, 0, 2);
    assertTrue(gb.tryMove(m3));
    Move m4 = new Move(p2, 2, 0);
    assertTrue(gb.tryMove(m4));
    Move m5 = new Move(p1, 0, 0);
    assertTrue(gb.tryMove(m5));
    Move m6 = new Move(p2, 0, 1);
    assertTrue(gb.tryMove(m6));
    Move m7 = new Move(p1, 1, 2);
    assertTrue(gb.tryMove(m7));
    Move m8 = new Move(p2, 2, 2);
    assertTrue(gb.tryMove(m8));
    Move m9 = new Move(p1, 2, 1);
    assertTrue(gb.tryMove(m9));
    assertEquals(gb.getWinner(), 0);
    assertTrue(gb.getIsDraw());
    assertFalse(gb.tryMove(m9));
  }

}
