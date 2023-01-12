package model.transformations;

import model.image.Image;
import model.image.RGB;

/**
 * Transformation that makes an image darker by the specified amount. Amount cannot be negative.
 */
public class Darken implements Transformation {

  int change;

  /**
   * Constructor that takes in the amount you'd like to darken by.
   * Will do nothing if Image can't be darkened anymore.
   * If the Image can't be darkened all the way, then it will reach min brightness.
   * Minimum brightness is achieved at pixel value equals to 0.
   *
   * @param change the change to be applied. Has to be positive.
   * @throws IllegalArgumentException if change is negative
   */
  public Darken(int change) {
    if (change < 0) {
      throw new IllegalArgumentException("Don't enter negative numbers");
    } else {
      this.change = change;
    }
  }


  /**
   * Darkens the given image by the known amount.
   *
   * @param imgInitial image to be transformed
   * @return new Image with transformation applies
   */
  @Override
  public Image doTo(Image imgInitial) {
    Image newImg = imgInitial.copy();
    int imgWidth = imgInitial.getWidth();
    int imgHeight = imgInitial.getHeight();

    for (int x = 0; x < imgWidth; x++) {
      for (int y = 0; y < imgHeight; y++) {
        RGB val = imgInitial.getPixelAt(x, y);
        newImg.setPixelAt(x, y, new RGB(
                Integer.max(0, val.get("red") - change),
                Integer.max(0, val.get("green") - change),
                Integer.max(0, val.get("blue") - change)));
      }
    }
    return newImg;
  }
}
