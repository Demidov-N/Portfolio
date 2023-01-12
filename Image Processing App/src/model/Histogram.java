package model;

import model.image.Image;
import model.image.RGB;
import model.image.ViewImage;
import model.image.ViewImageImpl;

/**
 * Class to represent a color Histogram for an image.
 */
public final class Histogram {

  private final double[] rTable;
  private final double[] gTable;
  private final double[] bTable;
  private final double[] iTable;
  private final int maxValue;
  private double maxIntensity;
  private double maxRed;
  private double maxGreen;
  private double maxBlue;

  /**
   * Constructs a Histogram for an Image.
   *
   * @param img the image to create a Histogram for
   */
  public Histogram(Image img) {
    this(new ViewImageImpl(img));
  }

  /**
   * Constructs color Histogram from a ViewImage.
   *
   * @param img the image
   */
  public Histogram(ViewImage img) {
    maxValue = img.getMaxValue();
    maxRed = 0;
    maxGreen = 0;
    maxBlue = 0;
    maxIntensity = 0;
    this.rTable = new double[img.getMaxValue() + 1];
    this.gTable = new double[img.getMaxValue() + 1];
    this.bTable = new double[img.getMaxValue() + 1];
    this.iTable = new double[img.getMaxValue() + 1];

    //populate every map with 0 from 0 to max val
    for (int i = 0; i <= img.getMaxValue(); i++) {
      rTable[i] = 0;
      gTable[i] = 0;
      bTable[i] = 0;
      iTable[i] = 0;
    }

    //go through every pixel and add one to the map based on the given RGB val
    for (int x = 0; x < img.getWidth(); x++) {
      for (int y = 0; y < img.getHeight(); y++) {
        RGB rgb = img.getPixelAt(x, y);
        rTable[rgb.get("red")]++;
        gTable[rgb.get("green")]++;
        bTable[rgb.get("blue")]++;
        int intensity = (rgb.get("red") + rgb.get("green") + rgb.get("blue")) / 3;
        iTable[intensity]++;
      }
    }
    for (int i = 0; i < this.getSize(); i++) {
      if (rTable[i] > maxRed) {
        maxRed = rTable[i];
      }
      if (bTable[i] > maxBlue) {
        maxBlue = bTable[i];
      }
      if (gTable[i] > maxGreen) {
        maxGreen = gTable[i];
      }
      if (iTable[i] > maxIntensity) {
        maxIntensity = iTable[i];
      }
    }
  }

  /**
   * Gets the size of a Histogram, meaning domain of the X axis of the table.
   *
   * @return an Int representing the max value
   */
  public int getSize() {
    return maxValue;
  }


  /**
   * Gets the corresponding value of the histogram for a given channel (percentage of max).
   *
   * @param x       the x value in the table
   * @param channel the specific channel you're interested in
   * @return the y value for the given x within a channel
   * @throws IllegalArgumentException if the x is less than 0 or greater than size, or if
   *                                  the channel is invalid
   */
  public int getValue(int x, String channel) throws IllegalArgumentException {
    if (x < 0 || x > getSize()) {
      throw new IllegalArgumentException("invalid index. Must be >= 0 & <= maxVal");
    }
    if (channel.equals("red")) {
      return (int) Math.round((rTable[x] / maxRed) * 100);
    } else if (channel.equals("green")) {
      return (int) Math.round((gTable[x] / maxGreen) * 100);
    } else if (channel.equals( "blue")) {
      return (int) Math.round((bTable[x] / maxBlue) * 100);
    } else if (channel.equals("intensity")) {
      return (int) Math.round((iTable[x] / maxIntensity) * 100);
    } else {
      throw new IllegalArgumentException("invalid channel");
    }

  }
}
