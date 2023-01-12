import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import controller.ImgProcessorController;
import controller.ScriptController;
import controller.features.ControllerFeatures;
import controller.features.GUIController;
import model.ImageProcessor;
import model.ImageProcessorModel;
import model.ImageProcessorView;
import model.ImageProcessorViewImpl;
import view.ImgProcessorTextView;
import view.ImgProcessorView;
import view.gui.ImageProcessorGUIView;


/**
 * A main class of the Image Processor, the one that is being run.
 * Can specify:
 * -t to specify the file to record the results of the transformation commands
 * -f to specify the file to read the commands from
 * If none specified, then it is going to run in interactive environment, every command can
 * be written directly into the console.
 */
public class ImageEditor {

  /**
   * A main method for the image processor command editor.
   * @param args the arguments of the program, can be empty if nothing is specified.
   */
  public static void main(String[] args) {
    Iterator<String> it = Arrays.stream(args).iterator();
    Readable pathFrom = null;
    

    if (it.hasNext()) {
      String n = it.next();
      String from = "";
      String to = "";
      if (n.equals("-file")) {
        try {
          from = it.next();
          pathFrom = new FileReader(from);
          
        } catch (NoSuchElementException e) {
          System.err.println("The path is not specified");
          System.exit(1);
        } catch (FileNotFoundException e) {
          System.err.println("No file was found under the specified name: " + from);
          System.exit(1);
        }
      } else if (n.equals("-text")) {
        pathFrom = new InputStreamReader(System.in);
      } else {
        System.err.println("Invalid Command \"" + n + "\" try again");
        System.exit(1);
      }
    }
    
    if (it.hasNext()) {
      System.err.println("Too many arguments provided, try again");
      System.exit(1);
    }
    
    ImageProcessor processor = new ImageProcessorModel();
    

    if (args.length == 0) {
      ImageProcessor model = new ImageProcessorModel();
      ImageProcessorView modelView = new ImageProcessorViewImpl(model);

      ImageProcessorGUIView view = new ImageProcessorGUIView(modelView);
      ControllerFeatures features = new GUIController(model, view);

      view.addFeature(features);
      view.setVisible(true);
    } else {
      ImgProcessorView view;
      view = new ImgProcessorTextView();

      ImgProcessorController controller;
      controller = new ScriptController(pathFrom, processor, view);
      controller.runController();
    }
  }
}
