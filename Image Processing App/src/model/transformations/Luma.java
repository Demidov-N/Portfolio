package model.transformations;

import model.image.Image;
import model.image.RGB;

/**
 * Applies Luma greyscale transformation to the given Image. Luma greyscale is when every channel
 * value is transformed based on the following Luma formula: 0.2126r+0.7152g+0.0722b. Every channel
 * becomes equal to the result of this equation.
 */
public class Luma implements Transformation {
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
        int luma = (int) ((0.2126 * red) + (0.7152 * green) + (0.0722 * blue));
        img.setPixelAt(x, y, new RGB(luma, luma, luma));
      }
    }
    return img;
  }
}
