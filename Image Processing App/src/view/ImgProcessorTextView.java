package view;

import java.io.IOException;

/**
 * An implementation of ImgProcessorView, for a simple terminal-based text view. It is going to
 * output messages as strings in the provided output. If output is not specified, outputs in the
 * terminal.
 */
public class ImgProcessorTextView implements ImgProcessorView {

  private Appendable output;

  /**
   * Creates a view with the specialized output, where everything will be recorded to.
   * @param output the output of the view.
   */
  public ImgProcessorTextView(Appendable output) {
    this.output = output;
  }

  /**
   * Creates a view with the output as a terminal.
   */
  public ImgProcessorTextView() {
    this(System.out);
  }

  @Override
  public void addMessage(String message) throws IOException {
    this.output.append(message);
  }

  @Override
  public void makeUseme() throws IOException {
    this.output.append("Below are the valid commands for the program [your input]\n"
        + "\n"
        + "#load\n"
        + "load [image path] [name to save image]\n"
        + "\n"
        + "#save\n"
        + "save [image path.imgExt] [image name you want to save]\n"
        + "\n"
        + "#(transformation)\n"
        + "(transformation) [image name to transform] [name of image after transformation]\n"
        + "\n"
        + "Below is a list of all the currently possible transformations "
        + "(used in the same format as above) \n"
        + "red-component {get's only the red component of all pixes}\n"
        + "green-component {get's only the green component of all pixes}\n"
        + "blue-component {get's only the blue component of all pixes}\n"
        + "value-component {visualize the value of an Image}\n"
        + "luma-component {visualize the luma of an Image}\n"
        + "intensity-component {visualize the intensity of an Image}\n"
        + "horizontal-flip {flip the image horizontally}\n"
        + "vertical-flip {flip the image vertically}\n"
        + "brighten [positive value to brighten and negative value to darken] "
        + "{changes the brightness of the Image}\n");
  }
}
