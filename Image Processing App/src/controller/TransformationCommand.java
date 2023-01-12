package controller;

import java.io.IOException;
import model.ImageProcessor;
import model.transformations.Transformation;

/**
 * An abstract class of the function object command that is making the model apply the
 * transformation on the image.
 */
class TransformationCommand implements ImgProcessorCommand {
  private final String oldImage;
  private final String newImage;
  private final Transformation transformation;

  /**
   * Creates a function object command that is going to make the model apply the transformation.
   *
   * @param oldImage       The name of image that is being transformed.
   *                       It should be loaded in the model before
   * @param newImage       A new name of an image for the newly transformed one.
   * @param transformation The transformation function object that applies the transformation.
   */
  public TransformationCommand(String oldImage, String newImage,
                               Transformation transformation) {
    this.oldImage = oldImage;
    this.newImage = newImage;
    this.transformation = transformation;
  }


  /**
   * Makes a transformation command on the processor.
   * @param processor An Image processor that is going to execute this command.
   * @throws IllegalArgumentException if the processor specified is null,
   *                                  if the input value of the transformation does not specify
   *                                  the transformation requirements.
   * @throws IOException if at any time of applying the specific operation, the model throws
   *                     this error.
   */
  public void runCommand(ImageProcessor processor) throws IOException,
      IllegalArgumentException {
    processor.transform(this.transformation, this.oldImage, this.newImage);
  }
}
