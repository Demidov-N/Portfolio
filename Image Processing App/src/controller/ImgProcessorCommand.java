package controller;

import java.io.IOException;
import model.ImageProcessor;

/**
 * A command that is being executed inside the controller to make the model execute a specific set
 * of operations specified. Used inside the {@code ImgProcessorController}.
 */
interface ImgProcessorCommand {
  /**
   * Makes a specific command on the processor.
   * @param processor An Image processor that is going to execute this command.
   * @throws IllegalArgumentException if the processor specified is null.
   * @throws IOException if at any time of applying the specific operation, the model throws
   *                     this error.
   */
  void runCommand(ImageProcessor processor) throws IllegalArgumentException, IOException;
}
