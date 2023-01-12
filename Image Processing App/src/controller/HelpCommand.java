package controller;

import java.io.IOException;
import model.ImageProcessor;
import view.ImgProcessorView;

/**
 * A Command that is creating a useme string.
 */
public class HelpCommand implements ImgProcessorCommand {

  ImgProcessorView output;

  /**
   * Creates a help command with the provided output where to pass.
   * @param output the output of the help.
   */
  public HelpCommand(ImgProcessorView output) {
    this.output = output;
  }

  @Override
  public void runCommand(ImageProcessor processor) throws IllegalArgumentException, IOException {
    this.output.makeUseme();
  }
}
