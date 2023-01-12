import java.io.IOException;

import controller.features.ControllerFeatures;
import view.gui.ImageGUI;


/**
 * MockGui class for testing.
 */
public class MockGUI implements ImageGUI {

  Appendable log;

  /**
   * constructs a MockGUI based on the input log.
   *
   * @param appendable the input log.
   */
  public MockGUI(Appendable appendable) {
    this.log = appendable;
  }

  /**
   * Adds all the features of the controller to the view.
   *
   * @param feature the features object
   */
  @Override
  public void addFeature(ControllerFeatures feature) {

    try {
      log.append("Features were added." + "\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Broken Appendable");
    }

  }

  /**
   * Shows an image on the main grid with the specified name from the model and sets it up as
   * a working image, the one that transformations are going to be applied to.
   *
   * @param image the name of the image to be shown
   * @throws IllegalArgumentException if the image name is not specified in the model.
   */
  @Override
  public void addImage(String image) throws IllegalArgumentException {
    try {
      log.append("Added image " + image + "\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Broken Appendable");
    }
  }

  /**
   * Creates an error pop-up.
   *
   * @param error A message of an error.
   */
  @Override
  public void addError(String error) {
    try {
      log.append("Error: " + error + "\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("Broken Appendable");
    }
  }
}
