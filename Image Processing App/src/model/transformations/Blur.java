package model.transformations;

import model.image.Image;

/**
 * Transformation class that Blurs an Image. Can be applied several way to make image more blur.
 */
public class Blur implements Transformation {

  /**
   * Applies a transformation on a given image and returns a transformed image.
   *
   * @param imgInitial image to be transformed
   * @return an already transformed image
   */
  @Override
  public Image doTo(Image imgInitial) {
    double[][] kernel = {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};
    return TransformationsUtil.applyKernel(kernel, imgInitial);
  }
}
