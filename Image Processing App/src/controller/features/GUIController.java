package controller.features;

import controller.ImageReader;
import model.ImageProcessor;
import model.image.Image;
import model.transformations.Blur;
import model.transformations.Brighten;
import model.transformations.Darken;
import model.transformations.FlipHorizontal;
import model.transformations.FlipVertical;
import model.transformations.Greyscale;
import model.transformations.Intensity;
import model.transformations.Luma;
import model.transformations.Sepia;
import model.transformations.Sharpen;
import model.transformations.Transformation;
import model.transformations.Value;
import view.gui.ImageGUI;

/**
 * An Asynchronous controller that is based on a GUI View.
 */
public class GUIController implements ControllerFeatures {

  private ImageProcessor model;
  private ImageGUI view;

  /**
   * Constructs a controller.
   *
   * @param model the ImageProcessor
   * @param view  the GUI
   */
  public GUIController(ImageProcessor model, ImageGUI view) {
    this.model = model;
    this.view = view;
    view.addFeature(this);
  }

  private void transformationHelp(Transformation tr, String img, String newImg)
          throws IllegalArgumentException {
    try {
      model.transform(tr, img, newImg);
    } catch (IllegalArgumentException e) {
      view.addError("Make sure that the given image exists. ERROR: " + e.getMessage());
      return;
    }

    view.addImage(newImg);
  }

  /**
   * Brightens an Image.
   *
   * @param image    the image to brighten
   * @param newImage name of image after being brightened
   * @param value    the value to brighten by
   */
  @Override
  public void brighten(String image, String newImage, int value) {
    if (value > 0) {
      transformationHelp(new Brighten(value), image, newImage);
    } else {
      transformationHelp(new Darken(value * -1), image, newImage);
    }
  }

  /**
   * blurs an image.
   *
   * @param image    the image to blur
   * @param newImage the name of the image after being blurred
   */
  @Override
  public void blur(String image, String newImage) {
    transformationHelp(new Blur(), image, newImage);
  }

  /**
   * flips an image.
   *
   * @param image     the image to flip
   * @param newImage  the name of the image after being flipped
   * @param direction a "horizontal" or "vertical" type of flip
   */
  @Override
  public void flip(String image, String newImage, String direction) {
    if (direction.equalsIgnoreCase("horizontal")) {
      transformationHelp(new FlipHorizontal(), image, newImage);
    } else if (direction.equalsIgnoreCase("vertical")) {
      transformationHelp(new FlipVertical(), image, newImage);
    } else {
      view.addError("The direction entered is not valid");
    }

  }

  /**
   * applies a greyscale filter to an Image (uses Luma).
   *
   * @param image    the image to apply the filter to
   * @param newImage the name of the image after the filter is applied to it
   */
  @Override
  public void greyscale(String image, String newImage) {
    luma(image, newImage);
  }

  /**
   * Find the intensity component of an image.
   *
   * @param image    the image to apply the transformation to
   * @param newImage the name of the image after the transformation is applies
   */
  @Override
  public void intensity(String image, String newImage) {
    transformationHelp(new Intensity(), image, newImage);
  }

  /**
   * applies a Luma filter to an Image.
   *
   * @param image    the image to apply Luma to
   * @param newImage the name of the image after the transformation is applied to it
   */
  @Override
  public void luma(String image, String newImage) {
    transformationHelp(new Luma(), image, newImage);
  }

  /**
   * Applies a Sepia color filter on an Image.
   *
   * @param image    the image to apply the filter to
   * @param newImage the name of hte image after the filter is applied to it
   */
  @Override
  public void sepia(String image, String newImage) {
    transformationHelp(new Sepia(), image, newImage);
  }

  /**
   * Sharpen an image.
   *
   * @param image    the image to sharpen
   * @param newImage the name of the resulting image
   */
  @Override
  public void sharpen(String image, String newImage) {
    transformationHelp(new Sharpen(), image, newImage);
  }

  /**
   * Applies a value component transformation on an Image.
   *
   * @param image    the image to apply the transformation to
   * @param newImage the name of the resulting image
   */
  @Override
  public void value(String image, String newImage) {
    transformationHelp(new Value(), image, newImage);
  }

  /**
   * Applies a channel component transformation on an Image.
   *
   * @param image    the image to apply the transformation to
   * @param newImage the name of the resulting image
   * @param channel  the "red" "green" "blue" channels to find the component of
   */
  @Override
  public void component(String image, String newImage, String channel) {
    switch (channel) {
      case ("red"):
        transformationHelp(new Greyscale("red"), image, newImage);
        break;
      case ("blue"):
        transformationHelp(new Greyscale("blue"), image, newImage);
        break;
      case ("green"):
        transformationHelp(new Greyscale("green"), image, newImage);
        break;
      default:
        view.addError("Invalid channel");

    }
  }

  /**
   * loads an image from a given path.
   *
   * @param path the path to load an image from
   * @param name the name of the image to load
   */
  @Override
  public void load(String path, String name) {
    Image img;
    try {
      img = ImageReader.read(path);
    } catch (Exception e) {
      view.addError("there was an error reading the image from the path. ERROR: " + e.getMessage());
      return;
    }

    model.addImage(img, name);
    view.addImage(name);
  }

  /**
   * saves an image to a given path.
   *
   * @param path the path to save the image in
   * @param name the name of the image to save
   */
  @Override
  public void save(String path, String name) {
    Image img;
    try {
      img = model.getImage(name);
    } catch (Exception e) {
      view.addError("Could not find image in system. ERROR: " + e.getMessage());
      return;
    }

    try {
      ImageReader.save(img, path);
    } catch (Exception e) {
      view.addError("Error saving the image to the given path. ERROR: " + e.getMessage());
      return;
    }
    //A way to notify that image was saved
  }
}
