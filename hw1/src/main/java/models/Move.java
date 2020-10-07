package models;

public class Move {

  private Player player;

  private int moveX;

  private int moveY;
  
  /**
   * Constructor.
   */
  public Move(Player player, int x, int y) {
    this.player = player;
    this.moveX = x;
    this.moveY = y;
  }
  
  /**
   * Get x coord. 
   * @return the x coord of the move
   */
  public int getX() {
    return this.moveX;
  }
  
  /**
   * Get y coord. 
   * @return the y coord of the move
   */
  public int getY() {
    return this.moveY;
  }
  
  /**
   * Get player. 
   * @return the Player of the move.
   */
  public Player getPlayer() {
    return this.player;
  }

}
