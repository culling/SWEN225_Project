package nz.ac.vuw.ecs.swen225.a3.common;

/**
 * This error is thrown when a itemInfo, tileInfo, or actorInfo is made for the MazeState that
 * contains invalid data.
 * 
 * @author straigfene
 *
 */
public class InvalidMazeElementException extends RuntimeException {

  /**
   * Constructs the exception.
   */
  public InvalidMazeElementException() {
    super();
  }

  /**
   * Constructs the exception with a message.
   * 
   * @param msg
   *          - the message
   */
  public InvalidMazeElementException(String msg) {
    super(msg);
  }

  /**
   * Constructs the exception with a message and cause.
   * 
   * @param msg
   *          - the message
   * @param cause
   *          - the cause
   */
  public InvalidMazeElementException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
