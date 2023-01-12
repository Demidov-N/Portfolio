package model.transformations;

import model.image.Image;
import model.image.RGB;

/**
 * Make a value component greyscale transformation on the image. Value component transformation
 * is a type of greyscale transformation, where the value of every channel in every pixel
 * is equal to the maximum of the three channels.
 */

//TODO: Make Intensity Transformation also.
public class Value implements Transformation {
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
        int max = Integer.max(red, Integer.max(green, blue));
        img.setPixelAt(x, y, new RGB(max, max, max));
      }
    }
    return img;
  }
}
