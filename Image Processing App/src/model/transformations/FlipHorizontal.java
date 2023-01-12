package model.transformations;

import model.image.Image;
import model.image.RGB;

/**
 * Flips an image horizontally.
 */
public class FlipHorizontal implements Transformation {

  /**
   * Flips the given image horizontally.
   *
   * @param imgInitial image to be transformed
   * @return a horizontally flipped Image.
   */
  @Override
  public Image doTo(Image imgInitial) {
    Image img = imgInitial.copy();
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        RGB val = imgInitial.getPixelAt(img.getWidth() - 1 - x, y);
        img.setPixelAt(x, y, val);
      }
    }
    return img;
  }
}
