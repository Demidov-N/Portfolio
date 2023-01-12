package view;

import java.io.IOException;

/**
 * A visual representation of the ImageProcessors, that is going to output the log of our image
 * transformations.
 */
public interface ImgProcessorView {

  /**
   * Adds a specific message to the output specified by the implementation of the interface.
   * @throws IOException if it cannot add a message to the specified destination.
   */
  void addMessage(String message) throws IOException;

  /**
   * Shows how to use the program.
   * @throws IOException if it cannot add a message to the specified destination.
   */
  void makeUseme() throws IOException;
}
