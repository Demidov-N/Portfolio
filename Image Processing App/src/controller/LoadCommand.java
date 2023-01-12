package controller;

import java.io.IOException;
import model.ImageProcessor;

/**
 * A function object command that is going to make image processor load an image under a specified
 * name. The path should always exist, and it should always be the file, not the folder, and be
 * accessible
 */
final class LoadCommand implements ImgProcessorCommand {
  private final String imagePath;
  private final String imageName;


  /**
   * Creates a command that is going to make the processor load an image from the path and save
   * it under the specified name.
   * @param imagePath The path to the image to be downloaded from. Cannot be the folder or
   *                  inaccessible file.
   * @param imageName The name under which the loaded image will be saved under.
   */
  LoadCommand(String imagePath, String imageName) {
    this.imageName = imageName;
    this.imagePath = imagePath;
  }

  /**
   * Loads an image into the processor.
   * @param processor A specific processor, which is executing operations on the image
   * @throws IllegalArgumentException if the specified path has an incompatible extension or if
   *                                  the provided controller is null.
   * @throws IOException If the file is not found, if it is a folder, or there
   *                     is an error in the file that does not allow to read it.
   *
   */
  public void runCommand(ImageProcessor processor) throws IllegalArgumentException,
      IOException {
    if (processor == null) {
      throw new IllegalArgumentException("Image Processor is null");
    }
    processor.addImage(ImageReader.read(this.imagePath), this.imageName);
  }
}
