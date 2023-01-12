package model;

import java.util.HashMap;
import java.util.Map;

import model.image.Image;
import model.transformations.Transformation;

/**
 * A simple implementation of the model. Supports only one type of image .ppm. Can apply
 * transformations of the images, load them from external files, and save them under specific names.
 */
public class ImageProcessorModel implements ImageProcessor {

  private Map<String, Image> data;

  public ImageProcessorModel() {
    this.data = new HashMap<String, Image>();
  }

  /**
   * Gets the extension of the file at the given path.
   *
   * @param path the path of the file
   * @return returns the 3 letter extension of a file
   */
  private String getExt(String path) {
    int len = path.length();
    return path.substring(len - 3, len);
  }



  /**
   * Applies a transformation to a given Image.
   *
   * @param tr         the Transformation to be applied
   * @param imgName    the name of the Image to be transformed
   * @param newImgName the new name of the transformed Image
   * @throws IllegalArgumentException if the image name does not exist
   */
  @Override
  public void transform(Transformation tr, String imgName, String newImgName)
          throws IllegalArgumentException {
    Image img = data.get(imgName);
    if (img == null) {
      throw new IllegalArgumentException("This image name doesn't exist within the model");
    }
    data.put(newImgName, tr.doTo(img));
  }

  /**
   * gets a given Image.
   *
   * @param imgName the name of the Image
   * @return a copy of that desired Image
   * @throws IllegalArgumentException if image name does not exist
   */
  @Override
  public Image getImage(String imgName) throws IllegalArgumentException {
    Image img = data.get(imgName);
    if (img == null) {
      throw new IllegalArgumentException("This image name doesn't exist within the model");
    }
    return img.copy();
  }

  /**
   * adds an image to the model.
   *
   * @param image The image to add
   * @param name  the name under which to remember the image inside the model.
   */
  @Override
  public void addImage(Image image, String name) {
    data.put(name, image);
  }

  /**
   * makes a histogram for a given Image.
   *
   * @param image the image.
   * @return the image Histogram
   */
  @Override
  public Histogram getHistogram(Image image) {
    return new Histogram(image);
  }
}