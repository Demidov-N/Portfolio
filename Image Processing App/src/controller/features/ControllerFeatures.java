package controller.features;


/**
 * Interface that includes all the features that an asynchronous controller should support.
 */
public interface ControllerFeatures {

  /**
   * Brightens an Image.
   *
   * @param image    the image to brighten
   * @param newImage name of image after being brightened
   * @param value    the value to brighten by
   */
  void brighten(String image, String newImage, int value);

  /**
   * blurs an image.
   *
   * @param image    the image to blur
   * @param newImage the name of the image after being blurred
   */
  void blur(String image, String newImage);

  /**
   * flips an image.
   *
   * @param image     the image to flip
   * @param newImage  the name of the image after being flipped
   * @param direction a "horizontal" or "vertical" type of flip
   */
  void flip(String image, String newImage, String direction);

  /**
   * applies a greyscale filter to an Image (uses Luma).
   *
   * @param image    the image to apply the filter to
   * @param newImage the name of the image after the filter is applied to it
   */
  void greyscale(String image, String newImage);

  /**
   * Find the intensity component of an image.
   *
   * @param image    the image to apply the transformation to
   * @param newImage the name of the image after the transformation is applies
   */
  void intensity(String image, String newImage);

  /**
   * applies a Luma filter to an Image.
   *
   * @param image    the image to apply Luma to
   * @param newImage the name of the image after the transformation is applied to it
   */
  void luma(String image, String newImage);

  /**
   * Applies a Sepia color filter on an Image.
   *
   * @param image    the image to apply the filter to
   * @param newImage the name of hte image after the filter is applied to it
   */
  void sepia(String image, String newImage);

  /**
   * Sharpen an image.
   *
   * @param image    the image to sharpen
   * @param newImage the name of the resulting image
   */
  void sharpen(String image, String newImage);

  /**
   * Applies a value component transformation on an Image.
   *
   * @param image    the image to apply the transformation to
   * @param newImage the name of the resulting image
   */
  void value(String image, String newImage);

  /**
   * Applies a channel component transformation on an Image.
   *
   * @param image    the image to apply the transformation to
   * @param newImage the name of the resulting image
   * @param channel  the "red" "green" "blue" channels to find the component of
   */
  void component(String image, String newImage, String channel);


  /**
   * loads an image from a given path.
   *
   * @param path the path to load an image from
   * @param name the name of the image to load
   */
  void load(String path, String name);

  /**
   * saves an image to a given path.
   *
   * @param path the path to save the image in
   * @param name the name of the image to save
   */
  void save(String path, String name);

}
