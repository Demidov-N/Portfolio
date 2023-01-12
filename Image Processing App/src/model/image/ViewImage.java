package model.image;

/**
 * A view purposed Image that can wrap an Image without the ability to modify it. Has only
 * informational methods.
 */
public interface ViewImage {

  /**
   * Outputs the width of the image.
   *
   * @return the width.
   */
  int getWidth();

  /**
   * Outputs the height of the image.
   *
   * @return the height.
   */
  int getHeight();

  /**
   * Outputs the maximum pixel value of the image. No pixel color cannot be greater
   * than this value.
   *
   * @return the maximum pixel color value.
   */
  int getMaxValue();

  /**
   * Gets a pixel from the image grid.
   *
   * @param x the column of the image, starting at 0 to width - 1
   * @param y the row of an image, starting at 0 to height - 1
   * @return the value of the pixel in the specified coordinate
   * @throws IllegalArgumentException if the coordinate is out of the range of the image.
   */
  RGB getPixelAt(int x, int y) throws IllegalArgumentException;

}
