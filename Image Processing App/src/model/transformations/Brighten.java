package model.transformations;

import model.image.Image;
import model.image.RGB;

/**
 * Transformation that makes an image brighter or darker based by the specified amount. Cannot
 * change the brightness more than it is specified by the image, if it tries to do that, keep the
 * pixel values on maximum.
 */
// TODO: Fix issue of the 255, should be maximum of image
public class Brighten implements Transformation {

  private final int change;

  /**
   * Constructor that takes in the amount you'd like to change the brightness by.
   * Will do nothing if Image can't be brightened anymore, pixels are at their maximum amount
   * specified by the image.
   * If the Image can't be brightened all the way, then it will reach max pixel value.
   *
   * @param change the change to be applied, has to be positive
   * @throws IllegalArgumentException if change is negative
   */
  public Brighten(int change) {
    if (change < 0) {
      throw new IllegalArgumentException("Don't enter negative numbers");
    } else {
      this.change = change;
    }
  }


  /**
   * Brightens the given Image by the known amount.
   *
   * @param imgInitial image to be transformed
   * @return a transformed Image
   */
  @Override
  public Image doTo(Image imgInitial) {
    Image newImg = imgInitial.copy();
    int imgWidth = imgInitial.getWidth();
    int imgHeight = imgInitial.getHeight();
    int colorMax = 255;

    for (int x = 0; x < imgWidth; x++) {
      for (int y = 0; y < imgHeight; y++) {
        RGB val = imgInitial.getPixelAt(x, y);
        newImg.setPixelAt(x, y, new RGB(
                Integer.min(colorMax, val.get("red") + change),
                Integer.min(colorMax, val.get("green") + change),
                Integer.min(colorMax, val.get("blue") + change)));
      }
    }
    return newImg;
  }

}
