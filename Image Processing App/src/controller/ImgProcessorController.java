package controller;
// TODO Make a builder???

/**
 * A controller that is going to interact with the ImageProcessor model. It can specify commands
 * in order to modify images and save it under different names.
 */
public interface ImgProcessorController {

  /**
   * Runs the controller based on the specified implementation.
   *
   * @throws IllegalStateException if we cannot read input or output at any point in time.
   */
  void runController() throws IllegalStateException;
}
