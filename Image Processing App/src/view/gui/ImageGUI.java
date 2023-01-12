package view.gui;

import controller.features.ControllerFeatures;

/**
 * A Graphic User Interface version of the Image processor with all the supported transformations
 * presented. Can be used by any model.
 * The information on how to use GUI is presented inside the GUI at the button help.
 */
public interface ImageGUI {
  
  /**
   * Adds all the features of the controller to the view. Required to be added in the constructor
   * to make the GUI working.
   *
   * @param feature the features object
   */
  void addFeature(ControllerFeatures feature);
  
  /**
   * Shows an image on the main grid with the specified name from the model and sets it up as
   * a working image, the one that transformations are going to be applied to.
   *
   * @param image the name of the image to be shown
   * @throws IllegalArgumentException if the image name is not specified in the model.
   */
  void addImage(String image) throws IllegalArgumentException;
  
  
  /**
   * Creates an error pop-up with a specified error.
   *
   * @param error A message of an error.
   */
  void addError(String error);
  
}
