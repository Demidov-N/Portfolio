package model.transformations;

import model.image.Image;

/**
 * A Transformation is any action that can be made to an image and produces a transformed image
 * without modifying the initial one. Some examples of transformation are changing to the greyscale,
 * flipping horizontally or vertically.
 */
public interface Transformation {

  /**
   * Applies a transformation on a given image and returns a transformed image.
   *
   * @param imgInitial image to be transformed
   * @return an already transformed image
   */
  Image doTo(Image imgInitial);
}
