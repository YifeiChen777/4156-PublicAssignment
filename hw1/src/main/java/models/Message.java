package models;

public class Message {

  private boolean moveValidity;

  private int code;

  private String message;
  
  /**
   * Constructor.
   */
  public Message(boolean y, int code, String msg) {
    this.moveValidity = y;
    this.code = code;
    this.message = msg;
  }

  /**
   * Getter.
   * @return move validity.
   */
  public boolean getValid() {
    return this.moveValidity;
  }
  
  /**
   * Getter.
   * @return code.
   */
  public int getCode() {
    return this.code;
  }
  
  /**
   * Getter.
   * @return message.
   */
  public String getMsg() {
    return this.message;
  }
}
