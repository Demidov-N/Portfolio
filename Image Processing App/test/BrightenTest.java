import org.junit.Test;

import java.util.Random;

import model.image.Image;
import model.image.RGB;
import model.transformations.Brighten;
import model.transformations.Transformation;

import static org.junit.Assert.assertTrue;

/**
 * Test for the Brighten transformation.
 */
public class BrightenTest {

  /**
   * testing that constructor throws error.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorError() {
    new Brighten(-1);
  }

  /**
   * testing the doTo method.
   */
  @Test
  public void BrightenTransformationTest() {
    int change = 10;
    Transformation brighten = new Brighten(change);
    Image initImg = new MockImage();
    Image finalImg = brighten.doTo(initImg);

    for (int x = 0; x < initImg.getWidth(); x++) {
      for (int y = 0; y < initImg.getHeight(); y++) {
        RGB val = initImg.getPixelAt(x, y);
        assertTrue(ImageTest.sameRGB(finalImg.getPixelAt(x, y),
                new RGB(
                        Integer.min(255, val.get("red") + change),
                        Integer.min(255, val.get("green") + change),
                        Integer.min(255, val.get("blue") + change))));
      }
    }

    change = 255; // should make all the images white
    brighten = new Brighten(change);
    initImg = new MockImage();
    finalImg = brighten.doTo(initImg);
    for (int x = 0; x < initImg.getWidth(); x++) {
      for (int y = 0; y < initImg.getHeight(); y++) {
        RGB val = initImg.getPixelAt(x, y);
        assertTrue(ImageTest.sameRGB(finalImg.getPixelAt(x, y),
                new RGB(
                        Integer.min(255, val.get("red") + change),
                        Integer.min(255, val.get("green") + change),
                        Integer.min(255, val.get("blue") + change))));
      }
    }

    change = 50;
    brighten = new Brighten(change);
    initImg = new MockImage(new Random(2)); // basically guarantees that some values will
    // be near max and others will be near 0
    finalImg = brighten.doTo(initImg);
    for (int x = 0; x < initImg.getWidth(); x++) {
      for (int y = 0; y < initImg.getHeight(); y++) {
        RGB val = initImg.getPixelAt(x, y);
        assertTrue(ImageTest.sameRGB(finalImg.getPixelAt(x, y),
                new RGB(
                        Integer.min(255, val.get("red") + change),
                        Integer.min(255, val.get("green") + change),
                        Integer.min(255, val.get("blue") + change))));
      }
    }

  }

}