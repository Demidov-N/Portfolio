package model.transformations;

import model.image.Image;
import model.image.RGB;

/**
 * Performs an Intensity transfomration (average value of RGB).
 */
public class Intensity implements Transformation {
  /**
   * Applies a transformation on a given image and returns a transformed image.
   *
   * @param imgInitial image to be transformed
   * @return an already transformed image
   */
  @Override
  public Image doTo(Image imgInitial) {
    Image img = imgInitial.copy();
    int imgWidth = imgInitial.getWidth();
    int imgHeight = imgInitial.getHeight();

    for (int x = 0; x < imgWidth; x++) {
      for (int y = 0; y < imgHeight; y++) {
        int red = img.getPixelAt(x, y).get("red");
        int green = img.getPixelAt(x, y).get("green");
        int blue = img.getPixelAt(x, y).get("blue");
        int avg = (red + green + blue) / 3;
        img.setPixelAt(x, y, new RGB(avg, avg, avg));
      }
    }
    return img;
  }
}
