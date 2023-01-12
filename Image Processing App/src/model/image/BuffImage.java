package model.image;


/**
 * Represents an image that can be loaded using BuffedImage class and supported by the ImageIO
 * library of the java.
 */
public class BuffImage implements Image, ViewImage {

  private final RGB[][] image;
  private final int width;
  private final int height;
  private final int maxValue;


  /**
   * Creates an empty image, where every pixel is a transparent black pixel.
   * @param width the width of the image to be created.
   * @param height the height of the image to be created.
   * @param maxValue the maximum pixel value of the image to be created.
   */
  public BuffImage(int width, int height, int maxValue) {
    this.image = new RGB[width][height];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        this.image[i][j] = new RGB(255, 255, 255, 0);
      }
    }
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
  }


  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  @Override
  public RGB getPixelAt(int x, int y) throws IllegalArgumentException {
    if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeight()) {
      throw new IllegalArgumentException("Trying to get a pixel outside an image");
    }
    return this.image[x][y];
  }

  @Override
  public void setPixelAt(int x, int y, RGB val) throws IllegalArgumentException {
    if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeight()) {
      throw new IllegalArgumentException("Trying to get a pixel outside an image");
    }
    this.image[x][y] = val;
  }

  @Override
  public Image copy() {

    Image newImage = new BuffImage(this.getWidth(), this.getHeight(), this.getMaxValue());
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        newImage.setPixelAt(i, j, this.image[i][j]);
      }
    }

    return newImage;
  }
}
