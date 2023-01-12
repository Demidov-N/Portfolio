import java.util.Random;

import model.image.Image;
import model.image.RGB;

/**
 * A mock image, that is used for testing the transformations. Mock is used to see if the pixels
 * transformed are the same as the transformation was intended to.
 */
public class MockImage implements Image {

  private RGB[][] data = new RGB[10][10];

  /**
   * Constructs a mock Image with all black pixels.
   */
  public MockImage() {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        data[i][j] = new RGB();
      }
    }
  }

  /**
   * constructs a MockImage with random values all around.
   *
   * @param r the Random
   */
  public MockImage(Random r) {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        data[i][j] = new RGB(r.nextInt(255), r.nextInt(255), r.nextInt(255));
      }
    }
  }

  /**
   * Constructs a MockImage given an array of an array of RGB.
   *
   * @param data the data to make the image out of
   */
  private MockImage(RGB[][] data) {
    this.data = data;
  }



  @Override
  public int getWidth() {
    return 10;
  }

  @Override
  public int getHeight() {
    return 10;
  }

  @Override
  public int getMaxValue() {
    return 255;
  }

  @Override
  public RGB getPixelAt(int x, int y) {
    return data[x][y];
  }

  @Override
  public void setPixelAt(int x, int y, RGB val) {
    data[x][y] = val;
  }

  @Override
  public Image copy() {
    RGB[][] newData = new RGB[10][10];
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        newData[i][j] = this.data[i][j];
      }
    }
    return new MockImage(newData);
  }
}
