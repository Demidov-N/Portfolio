package controller;

import model.transformations.Brighten;
import model.transformations.Darken;
import model.transformations.Transformation;

/**
 * A function object command that applies the Brighten or Darken transformation to the image.
 */
final class BrightenCommand extends TransformationCommand {

  /**
   * Make command brighten that changes brightness of the object.
   * @param amount the amount to change the value. Can be positive or negative, but cannot
   *               be greater than maximum pixel value.
   * @param oldImage The image name that is going to be transformed. The image should be already
   *                 uploaded.
   * @param newImage the image name that the newly transformed image is going to be saved under.
   */
  BrightenCommand(int amount, String oldImage, String newImage) {
    super(oldImage, newImage, makeTransformation(amount));
  }

  private static Transformation makeTransformation(int amount) {
    if (amount >= 0) {
      return new Brighten(amount);
    } else {
      return new Darken(-amount);
    }
  }
}
