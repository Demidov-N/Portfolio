import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import controller.ImageReader;
import controller.features.GUIController;
import model.ImageProcessor;
import model.ImageProcessorModel;
import model.image.Image;
import model.transformations.Blur;
import model.transformations.Brighten;
import model.transformations.Darken;
import model.transformations.FlipHorizontal;
import model.transformations.FlipVertical;
import model.transformations.Greyscale;
import model.transformations.Intensity;
import model.transformations.Luma;
import model.transformations.Sepia;
import model.transformations.Sharpen;
import model.transformations.Value;
import view.gui.ImageGUI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * test class for the GUIController.
 */
public class GUIControllerTest {

  ImageProcessor model;
  ImageGUI view;
  Appendable log;
  GUIController controller;
  Image imgK;

  private void init() {
    model = new ImageProcessorModel();
    log = new StringBuilder();
    view = new MockGUI(log);

    try {
      imgK = ImageReader.read("res/Koala.ppm");
    } catch (IOException e) {
      throw new IllegalArgumentException("Can't read Image");
    }

    model.addImage(imgK, "koala");

    controller = new GUIController(model, view);
  }

  @Test
  public void brighten() {
    init();
    int value = 10;
    Image expected = new Brighten(value).doTo(imgK);

    controller.brighten("koala", "koalaBrighten", value);
    Image actual = model.getImage("koalaBrighten");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaBrighten\n", log.toString());

    //for darkening
    init();
    value = 10;
    expected = new Darken(value).doTo(imgK);

    controller.brighten("koala", "koalaDarken", value * -1);
    actual = model.getImage("koalaDarken");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaDarken\n", log.toString());

  }

  @Test
  public void blur() {
    init();
    Image expected = new Blur().doTo(imgK);

    controller.blur("koala", "koalaNew");
    Image actual = model.getImage("koalaNew");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaNew\n", log.toString());
  }

  @Test
  public void flip() {
    init();
    Image expected = new FlipHorizontal().doTo(imgK);

    controller.flip("koala", "koalaNew", "horizontal");
    Image actual = model.getImage("koalaNew");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaNew\n", log.toString());

    ///The other direction
    init();
    expected = new FlipVertical().doTo(imgK);

    controller.flip("koala", "koalaNew", "vertical");
    actual = model.getImage("koalaNew");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaNew\n", log.toString());

  }

  @Test
  public void greyscale() {
    init();
    Image expected = new Luma().doTo(imgK);

    controller.greyscale("koala", "koalaNew");
    Image actual = model.getImage("koalaNew");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaNew\n", log.toString());
  }

  @Test
  public void intensity() {
    init();
    Image expected = new Intensity().doTo(imgK);

    controller.intensity("koala", "koalaNew");
    Image actual = model.getImage("koalaNew");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaNew\n", log.toString());
  }

  @Test
  public void luma() {
    init();
    Image expected = new Luma().doTo(imgK);

    controller.luma("koala", "koalaNew");
    Image actual = model.getImage("koalaNew");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaNew\n", log.toString());
  }

  @Test
  public void sepia() {
    init();
    Image expected = new Sepia().doTo(imgK);

    controller.sepia("koala", "koalaNew");
    Image actual = model.getImage("koalaNew");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaNew\n", log.toString());
  }

  @Test
  public void sharpen() {
    init();
    Image expected = new Sharpen().doTo(imgK);

    controller.sharpen("koala", "koalaNew");
    Image actual = model.getImage("koalaNew");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaNew\n", log.toString());
  }

  @Test
  public void value() {
    init();
    Image expected = new Value().doTo(imgK);

    controller.value("koala", "koalaNew");
    Image actual = model.getImage("koalaNew");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image koalaNew\n", log.toString());
  }

  @Test
  public void component() {
    String[] colors = new String[]{"red", "green", "blue"};
    for (String s : colors) {
      init();
      Image expected = new Greyscale(s).doTo(imgK);

      controller.component("koala", "koalaNew", s);
      Image actual = model.getImage("koalaNew");

      //model change
      assertTrue(ImageTest.sameImage(expected, actual));

      //view
      assertEquals("Features were added.\n" +
              "Added image koalaNew\n", log.toString());
    }
  }

  @Test
  public void load() {
    init();
    Image expected = null;
    try {
      expected = ImageReader.read("res/peacock.png");
    } catch (IOException e) {
      throw new IllegalArgumentException("can't read image");
    }

    controller.load("res/peacock.png", "peacock");
    Image actual = model.getImage("peacock");

    //model change
    assertTrue(ImageTest.sameImage(expected, actual));

    //view
    assertEquals("Features were added.\n" +
            "Added image peacock\n", log.toString());
  }

  @Test
  public void save() {
    init();
    try {
      //saves file, ensures it was saved and that it's the same as intended
      controller.save("res/koalaS.ppm", "koala");
      Image expected = model.getImage("koala");
      Image actual = ImageReader.read("res/koalaS.ppm");
      assertTrue(ImageTest.sameImage(expected, actual));

      //ensures path is there and then deletes it
      Path path = Paths.get("res/koalaS.ppm");
      assertTrue(Files.exists(path));
      new File("res/koalaS.ppm").delete();
    } catch (Exception e) {
      throw new IllegalArgumentException("Error saving the file");
    }
  }

  @Test
  public void testViewError() {
    init();
    controller.flip("koala", "koalaFlip", "diagonal");
    assertEquals("Features were added.\n" +
            "Error: The direction entered is not valid\n", log.toString());

    init();
    controller.blur("fakeImg", "newImg");
    assertEquals("Features were added.\n" +
            "Error: Make sure that the given image exists. " +
            "ERROR: This image name doesn't exist within the model\n", log.toString());
  }
}