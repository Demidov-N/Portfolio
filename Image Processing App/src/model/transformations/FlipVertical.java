package model.transformations;


import model.image.Image;
import model.image.RGB;

/**
 * Flips an image Vertically.
 */
public class FlipVertical implements Transformation {

  /**
   * Flips the given image Vertically.
   *
   * @param imgInitial image to be transformed
   * @return a Vertically flipped Image.
   */
  @Override
  public Image doTo(Image imgInitial) {
    Image img = imgInitial.copy();
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        RGB val = imgInitial.getPixelAt(x, img.getHeight() - 1 - y);
        img.setPixelAt(x, y, val);
      }
    }
    return img;
  }
}
