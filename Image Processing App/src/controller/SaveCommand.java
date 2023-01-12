package controller;

import java.io.IOException;
import model.ImageProcessor;


final class SaveCommand implements ImgProcessorCommand {
  private final String imagePath;
  private final String imageName;

  SaveCommand(String imagePath, String imageName) {
    this.imageName = imageName;
    this.imagePath = imagePath;
  }

  /**
   * Saves a specified image from the processor.
   * @param processor A specific processor to apply operation to.
   * @throws IllegalArgumentException if the image name specified is not the extension supported.
   *                                  or if the processor specified is empty
   * @throws IOException if the system cannot save the image in the specified path,
   *                     it is either the folder or is not available for writing.
   */
  public void runCommand(ImageProcessor processor) throws
      IllegalArgumentException, IOException {
    if (processor == null) {
      throw new IllegalArgumentException("Image Processor specified is null");
    }
    ImageReader.save(processor.getImage(this.imageName), this.imagePath);
  }
}
