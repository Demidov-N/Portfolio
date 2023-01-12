package model;

import model.image.Image;
import model.transformations.Transformation;

/**
 * Represents an Image processor, that interacts with images. Every image is loaded from
 * the external file and saved it under the specified name in the internal memory. Any loaded
 * image can be transformed using one of the transformation function objects provided, or a new
 * one can be created based on the provided interface. Images are transformed and saved internally
 * under a new name, without modifying the original one. The transformed images can be saved as file
 * on the computer.
 */
public interface ImageProcessor {

  /**
   * Applies a transformation to a given Image.
   *
   * @param tr         the Transformation to be applied
   * @param imgName    the name of the Image to be transformed
   * @param newImgName the new name of the transformed Image
   * @throws IllegalArgumentException if the image name does not exist
   */
  void transform(Transformation tr, String imgName, String newImgName)
      throws IllegalArgumentException;

  /**
   * gets a given Image.
   *
   * @param imgName the name of the Image
   * @return a copy of that desired Image
   * @throws IllegalArgumentException if image name does not exist
   */
  Image getImage(String imgName) throws IllegalArgumentException;

  /**
   * Adds a pre-loaded image into the processor.
   *
   * @param image The image to add
   * @param name  the name under which to remember the image inside the model.
   */
  void addImage(Image image, String name);

  /**
   * Returns the color Histogram for an image.
   *
   * @param image the image.
   * @return A Histogram
   */
  Histogram getHistogram(Image image);
}
