import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import controller.ImgProcessorController;
import controller.ScriptController;
import model.ImageProcessor;
import model.ImageProcessorModel;
import model.image.Image;
import controller.ImageReader;
import model.transformations.Brighten;
import model.transformations.Darken;
import model.transformations.FlipHorizontal;
import model.transformations.Greyscale;
import model.transformations.Transformation;
import view.ImgProcessorTextView;
import view.ImgProcessorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the ScriptController class.
 */
public class ScriptControllerTest {


  @Test
  public void testLoad() {
    ImageProcessor processor = new ImageProcessorModel();

    //check that koala doesn't exist
    try {
      processor.getImage("koala");
    } catch (IllegalArgumentException e) {
      assertEquals("This image name doesn't exist within the model", e.getMessage());
    }

    Appendable output = new StringBuilder();
    ImgProcessorView view = new ImgProcessorTextView(output);

    Readable file = new StringReader("load res/Koala.ppm koala\n");
    ImgProcessorController controller = new ScriptController(file, processor, view);
    controller.runController();

    //check that koala now exists - shouldn't trow error
    Image img = processor.getImage("koala");

    //now check it's the same image
    try {
      assertTrue(ImageTest.sameImage(
              img, ImageReader.read("res/Koala.ppm")));
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  @Test
  public void testSave() {
    ImageProcessor processor = new ImageProcessorModel();

    Appendable output = new StringBuilder();
    ImgProcessorView view = new ImgProcessorTextView(output);

    //loads file then saves it
    Readable commands = new StringReader("load res/Koala.ppm koala\n" +
            "save res/KoalaNew.ppm koala");
    ImgProcessorController controller = new ScriptController(commands, processor, view);

    //check that nothing exists in this path
    try {
      ImageReader.read("res/KoalaNew.ppm");
    } catch (IOException e) { //will only go here firs time test is ran
      assertEquals("File res/KoalaNew.ppm not found!", e.getMessage());
    }

    //should run the command that saves the file
    controller.runController();

    //now check that the path exist and that it saved the right Image
    try {
      Image imgRetrieve = ImageReader.read("res/KoalaNew.ppm");
      Image imgModel = processor.getImage("koala");
      assertTrue(ImageTest.sameImage(imgModel, imgRetrieve));

    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  @Test
  public void transformations() {
    ImageProcessor processor = new ImageProcessorModel();

    Appendable output = new StringBuilder();
    ImgProcessorView view = new ImgProcessorTextView(output);

    //loads file then saves it
    Readable commands = new StringReader("load res/Koala.ppm koala\n" +
            "brighten 20 koala koala-brighter\n" +
            "red-component koala koala-red\n" +
            "brighten -89 koala koala-darker\n" +
            "horizontal-flip koala koala-flip-h\n");
    ImgProcessorController controller = new ScriptController(commands, processor, view);
    controller.runController();

    //check that each transformation is actually applied
    Image imgE = processor.getImage("koala");
    Image expected;
    Transformation transform;
    Image actual;

    //Brighten
    transform = new Brighten(20);
    expected = transform.doTo(imgE);
    actual = processor.getImage("koala-brighter");
    assertTrue(ImageTest.sameImage(actual, expected));

    //darken
    transform = new Darken(89);
    expected = transform.doTo(imgE);
    actual = processor.getImage("koala-darker");
    assertTrue(ImageTest.sameImage(actual, expected));

    //red-component
    transform = new Greyscale("red");
    expected = transform.doTo(imgE);
    actual = processor.getImage("koala-red");
    assertTrue(ImageTest.sameImage(actual, expected));

    //horizontal-flip
    transform = new FlipHorizontal();
    expected = transform.doTo(imgE);
    actual = processor.getImage("koala-flip-h");
    assertTrue(ImageTest.sameImage(actual, expected));

  }

  @Test
  public void testError() {
    ImageProcessor processor = new ImageProcessorModel();

    Appendable output = new StringBuilder();
    ImgProcessorView view = new ImgProcessorTextView(output);

    //loads file then saves it
    Readable commands = new StringReader("load res/Koala.ppm koala\n" +
            "invCommand kaoal");
    ImgProcessorController controller = new ScriptController(commands, processor, view);
    controller.runController();

    String expected = "Successfully executed command load\n" +
            "ERROR: Unknown command invCommand check the inputs.\n" +
            "ERROR: Unknown command kaoal check the inputs.\n";
    assertEquals(expected, output.toString());

  }


}