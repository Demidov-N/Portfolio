package model;

import model.image.ViewImage;

/**
 * A View-purposed model that only has getter methods.
 */
public interface ImageProcessorView {

  /**
   * gets a given Image.
   *
   * @param imgName the name of the Image
   * @return a copy of that desired Image
   * @throws IllegalArgumentException if image name does not exist
   */
  ViewImage getImage(String imgName) throws IllegalArgumentException;

  /**
   * makes a histogram from a given Image.
   *
   * @param image the image
   * @return a histogram
   */
  Histogram getHistogram(ViewImage image);
}
