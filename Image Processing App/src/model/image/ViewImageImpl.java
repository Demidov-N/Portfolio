package model.image;

/**
 * An implementation of ViewImageImpl, that imitates the behavior of the original image.
 */
public class ViewImageImpl implements ViewImage {

  private final Image delegate;

  /**
   * constructs a View Image.
   *
   * @param delegate a real Image
   */
  public ViewImageImpl(Image delegate) {
    this.delegate = delegate;
  }

  /**
   * Outputs the width of the image.
   *
   * @return the width.
   */
  @Override
  public int getWidth() {
    return delegate.getWidth();
  }

  /**
   * Outputs the height of the image.
   *
   * @return the height.
   */
  @Override
  public int getHeight() {
    return delegate.getHeight();
  }

  /**
   * Outputs the maximum pixel value of the image. No pixel color cannot be greater
   * than this value.
   *
   * @return the maximum pixel color value.
   */
  @Override
  public int getMaxValue() {
    return delegate.getMaxValue();
  }

  /**
   * Gets a pixel from the image grid.
   *
   * @param x the column of the image, starting at 0 to width - 1
   * @param y the row of an image, starting at 0 to height - 1
   * @return the value of the pixel in the specified coordinate
   * @throws IllegalArgumentException if the coordinate is out of the range of the image.
   */
  @Override
  public RGB getPixelAt(int x, int y) throws IllegalArgumentException {
    return delegate.getPixelAt(x, y);
  }
}
