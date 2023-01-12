package model.image;


/**
 * An image interface, which represents any instance of an image and saves it in the internal
 * memory. An image can be saved into the file with the same extension, specified transformation
 * can be applied, and can return and set the values of the pixel at the specified coordinate of
 * the image grid.
 * This Image has the most regular RGB pixel with alpha channel.
 */
public interface Image {

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



  /**
   * Sets a pixel one the image grid.
   *
   * @param x   the column of the pixel in the image, starting at 0 to width - 1
   * @param y   the row of the pixel in the image, starting at 0 to height - 1
   * @param val the RGB val to set the pixel to
   * @throws IllegalArgumentException if the coordinate is out of the range of the image.
   */
  void setPixelAt(int x, int y, RGB val) throws IllegalArgumentException;


  /**
   * Creates a new copy of this image. Copy has all the same parameters as another image, all
   * the new parameters are new.
   *
   * @return a copy of this image.
   */
  Image copy();
}
