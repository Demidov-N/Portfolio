package model.image;

import java.awt.Color;

/**
 * A class that represents the single pixel of the image. It can get the value of the pixel for
 * every channel, or it can be compared to the other versions of itself. Has all the values
 * no greater than 255 and no less than 0.
 */
public class RGB {
  private final Color pix;

  /**
   * makes an RGB with all values 255, a white transparent background.
   */
  public RGB() {
    this.pix = new Color(255, 255, 255, 255);
  }

  /**
   * makes an RGB with given values.
   *
   * @param red   red val
   * @param green green val
   * @param blue  blue val
   * @throws IllegalStateException if the values are negative, or it is greater than 255.
   */
  public RGB(int red, int green, int blue) throws IllegalStateException {
    if (red < 0 || green < 0 || blue < 0 || red > 255 || green > 255 || blue > 255) {
      throw new IllegalStateException("An RGB value is Invalid: negative val or value greater"
          + "than 255");
    }

    this.pix = new Color(red, green, blue, 255);
  }

  /**
   * Creates a pixel based on the binary int value.
   * @param pixel a binary value of the color.
   */
  public RGB(int pixel) {
    this.pix = new Color(pixel, true);
  }


  /**
   * Creates a pixel based on its 3 channels: red blue, green and alpha. Alpha by default, if not
   * specified, is 255.
   * @param red the value of the red channel, from 0 to 255
   * @param green the value of the green channel, from 0 to 255
   * @param blue the value of the blue channel, from 0 to 255
   * @param alpha the value of the alpha channel, from 0 to 255
   * @throws IllegalStateException if the values are not within their range, from 0 to 255
   */
  public RGB(int red, int green, int blue, int alpha) throws IllegalStateException {
    if (red < 0 || green < 0 || blue < 0 || alpha < 0
        || red > 255 || green > 255 || blue > 255 || alpha > 255) {
      throw new IllegalStateException("An RGB value is Invalid: negative val.");
    }
    this.pix = new Color(red, green, blue, alpha);
  }

  /**
   * gets values of an individual RGB values.
   *
   * @param color the color channel that to be retried: "red", "green", "blue", "alpha"
   * @return the color value. It returns -1 for "alpha" if alpha is not supported in the image
   * @throws IllegalArgumentException if you inputted a color that doesn't exist
   */
  public int get(String color) throws IllegalArgumentException {
    switch (color.toLowerCase()) {
      case ("red"):
        return this.pix.getRed();
      case ("green"):
        return this.pix.getGreen();
      case ("blue"):
        return this.pix.getBlue();
      case("alpha"):
        return this.pix.getAlpha();
      default:
        throw new IllegalArgumentException("Inputted invalid channel");
    }
  }

  public int getRGBByte() {
    return this.pix.getRGB();
  }

  /**
   * Whether this RGB is equal to a given object.
   *
   * @param o the other object to compare to
   * @return true if the two objects are the same
   */
  public boolean equals(Object o) {
    if (o instanceof RGB) {
      RGB that = (RGB) o;
      return (this.get("red") == that.get("red"))
          && (this.get("blue") == that.get("blue"))
          && (this.get("green") == that.get("green"))
          && (this.get("alpha") == that.get("alpha"));
    }
    return false;
  }

  /**
   * returns a unique hascode for this RGB.
   *
   * @return hashCode
   */
  public int hashCode() {
    return Integer.hashCode(
        (this.get("red") * 2 + 100) * (this.get("blue") / 4 + 289)
            * (this.get("green") * 3 - 89) * (this.get("alpha")));
  }

  /**
   * represents this object as a String.
   *
   * @return a string
   */
  public String toString() {
    return String.format("R: %d, G: %d, B: %d", this.get("red"), this.get("green"),
        this.get("blue"));
  }
}
