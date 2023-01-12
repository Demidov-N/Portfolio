package model.transformations;

import model.image.Image;

/**
 * A Transformation class that applies the Sepia color filter.
 */
public class Sepia implements Transformation {

  /**
   * Applies a transformation on a given image and returns a transformed image.
   *
   * @param imgInitial image to be transformed
   * @return an already transformed image
   */
  @Override
  public Image doTo(Image imgInitial) {
    double[][] matrix = {{0.399, 0.789, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    return TransformationsUtil.applyMatrix(matrix, imgInitial);
  }
}
