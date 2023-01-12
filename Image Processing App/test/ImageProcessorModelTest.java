import org.junit.Test;

import java.io.IOException;

import controller.ImageReader;
import model.ImageProcessor;
import model.ImageProcessorModel;
import model.image.Image;
import model.transformations.Brighten;
import model.transformations.Darken;
import model.transformations.FlipHorizontal;
import model.transformations.FlipVertical;
import model.transformations.Greyscale;
import model.transformations.Transformation;

import static org.junit.Assert.assertTrue;

/**
 * Tests for the main Image processor model.
 */
public class ImageProcessorModelTest {


  /**
   * test the transform method for the model.
   */
  @Test
  public void transform() {
    ImageProcessor model = new ImageProcessorModel();

    try {
      Image img = ImageReader.read("res/Koala.ppm");
      model.addImage(img, "koala");
    } catch (Exception e) {
      throw new IllegalArgumentException("Model can't find the image");
    }


    Transformation[] transformations = {new Brighten(20),
        new Darken(89), new FlipHorizontal(), new FlipVertical(),
        new Greyscale("red")};


    for (Transformation tr : transformations) {
      Image expected = tr.doTo(model.getImage("koala"));
      model.transform(tr, "koala", "koalaTR");
      Image actual = model.getImage("koalaTR");
      assertTrue(ImageTest.sameImage(expected, actual));

    }
  }

  /**
   * test the getImage method for the model.
   */
  @Test
  public void getImage() throws IOException {
    ImageProcessor model = new ImageProcessorModel();
    Image expected;
    Image actual;
    expected = ImageReader.read("res/Koala.ppm");
    model.addImage(expected, "koala");

    actual = model.getImage("koala");
    assertTrue(ImageTest.sameImage(actual, expected));
  }

  @Test
  public void addImage() throws IOException {
    ImageProcessor model = new ImageProcessorModel();
    Image expected = ImageReader.read("res/Koala.ppm");
    model.addImage(expected, "koala");

    Image actual = model.getImage("koala");
    assertTrue(ImageTest.sameImage(actual, expected));
  }
}