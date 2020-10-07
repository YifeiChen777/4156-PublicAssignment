package models;

public class GameBoard {

  private Player p1;

  private Player p2;

  private boolean gameStarted;

  private int turn;

  private char[][] boardState;

  private int winner;

  private boolean isDraw;
  
  /**
   * Constructor.
   */
  public GameBoard() {
    this.p1 = null;
    this.p2 = null;
    this.gameStarted = false;
    this.turn = 0;
    this.boardState = new char[3][3];
    this.winner = 0;
    this.isDraw = false;
  }
  
  /**
   * To start a new game.
   */
  public void startOver() {
    this.p1 = null;
    this.p2 = null;
    this.gameStarted = false;
    this.turn = 0;
    this.boardState = new char[3][3];
    this.winner = 0;
    this.isDraw = false;
  }
  
  /**
   * Check if game is started.
   * @return if game is started.
   */
  public boolean isGameStarted() {
    return this.gameStarted; 
  }
  
  /**
   * Start initial game.
   */
  public void startGame() {
    this.gameStarted = true;
    this.turn = 1;
  }
 
  /**
   * Set player.
   * @param type player1 info.
   */
  public void setPlayer1(char type) {
    this.p1 = new Player(type, 1);
  }
  
  /**
   * Set player.
   * @param type player2 info.
   */
  public void setPlayer2(char type) {
    this.p2 = new Player(type, 2);
    this.gameStarted = true;
  }
  
  /**
   * Get player1.
   * @return player1 in the game.
   */
  public Player getPlayer1() {
    return this.p1;
  }
  
  /** Get player2.
   * @return player2 in this game.
   */
  public Player getPlayer2() {
    return this.p2;
  }
  
  /**
   * get the winner of the game.
   * @return winner 
   */
  public int getWinner() {
    return this.winner;
  }
  
  /**
   * get turn.
   * @return turn
   */
  public int getTurn() {
    return this.turn;
  }
  
  /**
   * get if the game is draw.
   * @return if is draw
   */
  public boolean getIsDraw() {
    return this.isDraw;
  }
  
  /**
   * Check which player it is according to the type char.
   * @param type The char type we want to check.
   * @return the id of the player.
   */
  public int checkTypePlayer(char type) {
    if (type == this.p1.getType()) {
      return 1;
    }
    return 2;
  }
  
  /**
   * Check if a player win based on the current board state.
   * 
   * @return whether one of the player win the game.
   */
  public boolean checkWin() {
    // check if have 3 consecutive in a column
    for (int i = 0; i < this.boardState.length; i++) { 
      char a = this.boardState[i][0];
      for (int j = 1; j < this.boardState[0].length; j++) {
        if (this.boardState[i][j] != a) {
          break;
        }
        if (j == boardState[0].length - 1 && a != 0) {
          this.winner = this.checkTypePlayer(a);
          return true;
        }
      }
    }
    
    // check if have 3 consecutive in a row
    for (int i = 0; i < this.boardState[0].length; i++) { 
      char a = this.boardState[0][i];
      for (int j = 1; j < this.boardState.length; j++) {
        if (this.boardState[j][i] != a) {
          break;
        }
        if (j == boardState[0].length - 1 && a != 0) {
          this.winner = this.checkTypePlayer(a);
          return true;
        }
      }
    }
    
    // check diagonal
    char b = this.boardState[0][0];
    for (int i = 1; i < this.boardState.length; i++) {
      if (b != this.boardState[i][i] || b == 0) {
        break;
      }
      if (i == boardState.length - 1) {
        this.winner = this.checkTypePlayer(b);
        return true;
      }
    }
      
    // check diagonal
    char c = this.boardState[0][this.boardState.length - 1];
    for (int i = 1; i < this.boardState.length; i++) {
      if (c != this.boardState[i][this.boardState.length - 1 - i] || c == 0) {
        break;
      }
      if (i == boardState.length - 1) {
        this.winner = this.checkTypePlayer(c);
        return true;
      }
    }
    
    // check if draw
    for (int i = 0; i < this.boardState.length; i++) { 
      for (int j = 1; j < this.boardState[0].length; j++) {
        if (this.boardState[i][j] == 0) {
          return false;
        }
      }
    }
    
    this.isDraw = true;
    
    return false;
  }
  
  /** Try if the move is valid, if so move.
   * @param move The move we want to try.
   * @return Whether the move is valid.
   */
  public boolean tryMove(Move move) {
    if (this.winner != 0 || this.isDraw == true) {
      return false;
    }
    int x = move.getX();
    int y = move.getY();
    Player player = move.getPlayer();
    
    if (this.turn != player.getId()) {
      return false;
    }
    
    if (this.boardState[x][y] == 0) {
      boardState[x][y] = player.getType();
    } else {
      return false;
    }  
    this.checkWin();
    
    if (this.turn == 1) {
      this.turn = 2;
    } else {
      this.turn = 1;
    }
    return true;
  }
}
