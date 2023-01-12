package model.transformations;

import model.image.Image;
import model.image.RGB;


/**
 * Transformation that makes a specified image greyscale based on the specified color channel.
 * This greyscale transformation can be based on the red, blue or green component. It takes the
 * value of the single channel and makes all the other channels equal to that value.
 */
public class Greyscale implements Transformation {

  private String type;

  /**
   * Constructs a Greyscale transformation on an Image, making every pixel channel values equal
   * to the specific channel value.
   *
   * @param greyscaleType the type of greyscale transformation to be performed, which channel
   *                      to be used as a base value either "red","green", "blue"
   * @throws IllegalArgumentException if the type is anything other than RGB
   */
  public Greyscale(String greyscaleType) throws IllegalArgumentException {
    String type = greyscaleType.toLowerCase();
    if (greyscaleType.equals("red") || greyscaleType.equals("green")
        || greyscaleType.equals("blue")) {
      this.type = type;
    } else {
      throw new IllegalArgumentException("Inputted invalid Type");
    }
  }


  /**
   * does a greyscale transformation on a given Image.
   *
   * @param imgInitial image to be transformed
   * @return an Image after being turned greyscale
   */
  @Override
  public Image doTo(Image imgInitial) {
    Image img = imgInitial.copy();
    int imgWidth = imgInitial.getWidth();
    int imgHeight = imgInitial.getHeight();

    for (int x = 0; x < imgWidth; x++) {
      for (int y = 0; y < imgHeight; y++) {
        int color = img.getPixelAt(x, y).get(type);
        img.setPixelAt(x, y, new RGB(color, color, color));
      }
    }
    return img;
  }
}
