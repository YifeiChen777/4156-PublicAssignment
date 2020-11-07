package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import models.Move;

public class DatabaseLite {

  /**
   * Create a database connection.
   * @return a successful connection
   */
  public Connection createConnection() {
    Connection c = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:ase_i3.db");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      return null;
    }
    System.out.println("Database connection succeed.");
    return c;
  }
  
  /**
   * Close connection for the db.
   * @param c connection
   * @return whether the connection is closed successfully.
   */
  public boolean closeConnection(Connection c) {
    try {
      c.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      return false;
    }
    System.out.println("Database closed.");
    return true;
  }
  
  /**
   * Create a table for the database.
   * @param c A connection
   * @return if the table is created successfully.
   */
  public boolean createStateTable(Connection c) {
    Statement stmt = null;
    try {
      stmt = c.createStatement();
      String sql = "CREATE TABLE IF NOT EXISTS GAMEBOARD " 
                         + "(PLAYER_TYPE CHAR(1) NOT NULL," 
                         + "COORD_X INT NOT NULL, "  
                         + "COORD_Y INT NOT NULL, "
                         + "PLAYER_ID INT NOT NULL"
                         + ") "; 
      stmt.executeUpdate(sql);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      return false;
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println("BOARDSTATE Table created successfully");
    return true;
  }
  
  /**
   * add a move to database.
   * @param c database connection
   * @param move a move
   * @return if the move is added successfully.
   */
  public boolean addMoveData(Connection c, Move move) {
    Statement stmt = null;
    
    try {
      c.setAutoCommit(false);
      System.out.println("Database opened successfully");
    
      stmt = c.createStatement();
      String sql = "INSERT INTO GAMEBOARD (PLAYER_TYPE, COORD_X, COORD_Y, PLAYER_ID)" 
               + "VALUES (" + "'" + move.getPlayer().getType() + "', "
               + move.getX() + ", " + move.getY() + ", " + move.getPlayer().getId() + " );";
      stmt.executeUpdate(sql);
      c.commit();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      return false;
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    
    System.out.println("Add move succeed.");
    return true;
  }
  
  /**
   * retrieve game board state from database.
   * @param c database connection
   * @return if the move is added successfully.
   */
  public char[][] getBoard(Connection c) {
    Statement stmt = null;
    ResultSet rs = null;
    char[][] board = new char[4][3];
    
    try {
      c.setAutoCommit(false);
      System.out.println("Database opened successfully");
    
      stmt = c.createStatement();
      String sql = "SELECT * FROM GAMEBOARD;";
      rs = stmt.executeQuery(sql);
      if (!rs.next()) {
        return null;
      }
      int id = rs.getInt("PLAYER_ID");
      int x = rs.getInt("COORD_X");
      int y = rs.getInt("COORD_Y");
      char type = rs.getString("PLAYER_TYPE").charAt(0);
      board[x][y] = type;
      if (id == 1) {
        board[3][0] = type;
      }

      while (rs.next()) {
        id = rs.getInt("PLAYER_ID");
        x = rs.getInt("COORD_X");
        y = rs.getInt("COORD_Y");
        type = rs.getString("PLAYER_TYPE").charAt(0);
        board[x][y] = type;
        if (id == 1) {
          board[3][0] = type;
        }
      }
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      return null;
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    
    System.out.println("Get Board Succeed.");
    return board;
  }
  
  /**
   * drop a table in the database.
   * @param c A connection
   * @param tableName name of the table
   * @return if the table is dropped successfully.
   */
  public boolean dropTable(Connection c, String tableName) {
    Statement stmt = null;
    try {
      c.setAutoCommit(false);
      stmt = c.createStatement();
      String sql = "DROP TABLE IF EXISTS " + tableName + " ;";
      stmt.executeUpdate(sql);
      c.commit();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      return false;
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println("Table droped successfully");
    return true;
  }

}
