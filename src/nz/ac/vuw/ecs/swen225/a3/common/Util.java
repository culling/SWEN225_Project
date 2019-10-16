package nz.ac.vuw.ecs.swen225.a3.common;

/**
 * A class that provides methods that perform simple useful tasks that don't fit anywhere else
 * 
 * @author straigfene 300373183
 *
 */
public class Util {

  /**
   * Convert a Class object of a primitive into the Class object of the wrapper class of the
   * primitive. If the Class object is not of a primitive returns the same Class object.
   * 
   * @param pimativeClass
   *          the Class object of the primitive.
   * @return the Class object of the wrapper
   */
  public static Class<?> toWrapperClass(Class<?> pimativeClass) {
    if (pimativeClass == boolean.class) {
      return Boolean.class;
    }
    if (pimativeClass == int.class) {
      return Integer.class;
    }
    if (pimativeClass == long.class) {
      return Long.class;
    }
    if (pimativeClass == double.class) {
      return Double.class;
    }
    return pimativeClass;
  }
}
