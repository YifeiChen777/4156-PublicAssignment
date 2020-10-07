package models;

public class Player {

  private char type;

  private int id;
  
  /**
   * Constructor.
   */
  public Player(char type, int id) {
    this.type = type;
    this.id = id;
  }
  
  /**
   * Get the type char of player.
   * @return the type (X or O) of the player
   */
  public char getType() {
    return this.type;
  }
  
  /**
   * Get id of player.
   * @return the id of the player.
   */
  public int getId() {
    return this.id;
  }

}
