package nz.ac.vuw.ecs.swen225.a3.common;

/**
 * Thrown when there is any kind of error loading the classes in the level as a plugin
 * 
 * @author strigfene 300373183
 *
 */
public class LoadPluginException extends RuntimeException {

  /**
   * Constructs the exception.
   */
  public LoadPluginException() {
    super();
  }

  /**
   * Constructs the exception with a message.
   * 
   * @param msg
   *          - the message
   */
  public LoadPluginException(String msg) {
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
  public LoadPluginException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
