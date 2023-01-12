package model;

import model.image.Image;
import model.image.ViewImage;
import model.image.ViewImageImpl;

/**
 * A View-purposed model that only has getter methods, used for the information retrieval and
 * prevents from casting to or modifying the original processor.
 */
public class ImageProcessorViewImpl implements ImageProcessorView {

  private final ImageProcessor delegate;

  /**
   * Constructs an ImageProcessorView.
   *
   * @param model the real model
   */
  public ImageProcessorViewImpl(ImageProcessor model) {
    this.delegate = model;
  }

  /**
   * gets a given Image.
   *
   * @param imgName the name of the Image
   * @return a copy of that desired Image
   * @throws IllegalArgumentException if image name does not exist
   */
  @Override
  public ViewImage getImage(String imgName) throws IllegalArgumentException {
    return new ViewImageImpl(delegate.getImage(imgName));
  }

  /**
   * makes a histogram from a given Image.
   *
   * @param image the image
   * @return a histogram
   */
  @Override
  public Histogram getHistogram(ViewImage image) {
    return new Histogram((Image) image);
  }
}
