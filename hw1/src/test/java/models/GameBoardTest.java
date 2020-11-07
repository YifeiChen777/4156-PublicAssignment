package models;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import utils.DatabaseLite;

class GameBoardTest {

  DatabaseLite db = new DatabaseLite();
  GameBoard gb = new GameBoard();

  /**
   * test startgame.
  */
  @Test
  @Order(1)
  void testStartGame() {
    Connection connect = db.createConnection();
    gb.startOver(connect, db);
    db.closeConnection(connect);
  }

  /**
   * Game1 to test.
  */
  @Test
  @Order(2)
  void testGame1() {
    Connection connect = db.createConnection();
    gb.startOver(connect, db);
    gb.setPlayer1('X');
    gb.setPlayer2('O');
    gb.startGame();
    Player p1 = gb.getPlayer1();
    assertEquals('X', p1.getType());
    Player p2 = gb.getPlayer2();
    assertEquals('O', p2.getType());
    assertEquals(1, p1.getId());
    assertEquals(2, p2.getId());
    assertTrue(gb.isGameStarted());
    Move m1 = new Move(p1, 0, 0);
    System.out.println("test 1 move");
    
    assertTrue(gb.tryMove(m1, connect, db));
    Move m2 = new Move(p1, 1, 1);
    assertFalse(gb.tryMove(m2, connect, db));
    Move m3 = new Move(p2, 1, 1);
    assertTrue(gb.tryMove(m3, connect, db));
    Move m4 = new Move(p1, 0, 1);
    assertTrue(gb.tryMove(m4, connect, db));
    Move m5 = new Move(p2, 1, 2);
    assertTrue(gb.tryMove(m5, connect, db));
    Move m6 = new Move(p1, 0, 2);
    assertTrue(gb.tryMove(m6, connect, db));
    assertEquals(gb.getWinner(), 1);
    db.closeConnection(connect);
    System.out.println("close 1");
  }
  
  /**
   * Game2 to test.
  */
  @Test
  @Order(3)
  void testGame2() {
    Connection connect = db.createConnection();
    gb.startOver(connect, db);
    gb.setPlayer1('X');
    gb.setPlayer2('O');
    gb.startGame();
    Player p1 = gb.getPlayer1();
    Player p2 = gb.getPlayer2();
    Move m1 = new Move(p2, 0, 0);
    assertFalse(gb.tryMove(m1, connect, db));
    Move m2 = new Move(p1, 2, 2);
    assertTrue(gb.tryMove(m2, connect, db));
    Move m3 = new Move(p2, 1, 1);
    assertTrue(gb.tryMove(m3, connect, db));
    Move m = new Move(p1, 1, 1);
    assertFalse(gb.tryMove(m, connect, db));
    Move m4 = new Move(p1, 0, 2);
    assertTrue(gb.tryMove(m4, connect, db));
    Move m5 = new Move(p2, 0, 1);
    assertTrue(gb.tryMove(m5, connect, db));
    Move m6 = new Move(p1, 0, 0);
    assertTrue(gb.tryMove(m6, connect, db));
    Move m7 = new Move(p2, 2, 1);
    assertTrue(gb.tryMove(m7, connect, db));
    Move m8 = new  Move(p1, 0, 2);
    assertFalse(gb.tryMove(m8, connect, db));
    assertEquals(gb.getWinner(), 2);
    db.closeConnection(connect);
  }
  
  /**
   * Game3 to test.
  */
  @Test
  @Order(4)
  void testGame3() {
    Connection connect = db.createConnection();
    gb.startOver(connect, db);
    gb.setPlayer1('X');
    gb.setPlayer2('O');
    gb.startGame();
    Player p1 = gb.getPlayer1();
    Player p2 = gb.getPlayer2();
    assertEquals(gb.getTurn(), 1);
    Move m1 = new Move(p1, 1, 1);
    assertTrue(gb.tryMove(m1, connect, db));
    Move m2 = new Move(p2, 1, 0);
    assertTrue(gb.tryMove(m2, connect, db));
    Move m3 = new Move(p1, 0, 0);
    assertTrue(gb.tryMove(m3, connect, db));
    Move m4 = new Move(p2, 2, 0);
    assertTrue(gb.tryMove(m4, connect, db));
    Move m5 = new Move(p1, 2, 2);
    assertTrue(gb.tryMove(m5, connect, db));
    assertEquals(gb.getWinner(), 1);
    db.closeConnection(connect);
  }
  
  /**
   * Game4 to test.
  */
  @Test
  @Order(5)
  void testGame4() {
    Connection connect = db.createConnection();
    gb.startOver(connect, db);
    gb.setPlayer1('X');
    gb.setPlayer2('O');
    gb.startGame();
    Player p1 = gb.getPlayer1();
    Player p2 = gb.getPlayer2();

    Move m1 = new Move(p1, 1, 1);
    assertTrue(gb.tryMove(m1, connect, db));
    Move m2 = new Move(p2, 1, 0);
    assertTrue(gb.tryMove(m2, connect, db));
    Move m3 = new Move(p1, 0, 2);
    assertTrue(gb.tryMove(m3, connect, db));
    Move m4 = new Move(p2, 2, 2);
    assertTrue(gb.tryMove(m4, connect, db));
    Move m5 = new Move(p1, 2, 0);
    assertTrue(gb.tryMove(m5, connect, db));
    assertEquals(gb.getWinner(), 1);
    db.closeConnection(connect);
  }

  /**
   * Game5 to test.
  */
  @Test
  @Order(6)
  void testGame5() {
    Connection connect = db.createConnection();
    gb.startOver(connect, db);
    gb.setPlayer1('X');
    gb.setPlayer2('O');
    gb.startGame();
    Player p1 = gb.getPlayer1();
    Player p2 = gb.getPlayer2();

    Move m1 = new Move(p1, 1, 1);
    assertTrue(gb.tryMove(m1, connect, db));
    Move m2 = new Move(p2, 1, 0);
    assertTrue(gb.tryMove(m2, connect, db));
    Move m3 = new Move(p1, 0, 2);
    assertTrue(gb.tryMove(m3, connect, db));
    Move m4 = new Move(p2, 2, 0);
    assertTrue(gb.tryMove(m4, connect, db));
    Move m5 = new Move(p1, 0, 0);
    assertTrue(gb.tryMove(m5, connect, db));
    Move m6 = new Move(p2, 0, 1);
    assertTrue(gb.tryMove(m6, connect, db));
    Move m7 = new Move(p1, 1, 2);
    assertTrue(gb.tryMove(m7, connect, db));
    Move m8 = new Move(p2, 2, 2);
    assertTrue(gb.tryMove(m8, connect, db));
    Move m9 = new Move(p1, 2, 1);
    assertTrue(gb.tryMove(m9, connect, db));
    assertEquals(gb.getWinner(), 0);
    assertTrue(gb.getIsDraw());
    assertFalse(gb.tryMove(m9, connect, db));
    db.closeConnection(connect);
    Connection c1 = db.createConnection();
    GameBoard newgb = new GameBoard(c1, db);
    assertEquals(newgb.getWinner(), 0);
    assertEquals(newgb.getIsDraw(), true);
    db.closeConnection(c1);
  }

}
