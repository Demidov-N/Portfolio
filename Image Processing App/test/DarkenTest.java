import org.junit.Test;

import java.util.Random;

import model.image.Image;
import model.image.RGB;
import model.transformations.Darken;
import model.transformations.Transformation;

import static org.junit.Assert.assertTrue;

/**
 * test for Darken transformation.
 */
public class DarkenTest {

  /**
   * testing constructor Exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorError() {
    new Darken(-1);
  }

  /**
   * testing doTo method.
   */
  @Test
  public void DarkenTransformationTest() {
    //Should do nothing to the image. It's already dark
    int change = 10;
    Transformation darken = new Darken(change);
    Image initImg = new MockImage();
    Image finalImg = darken.doTo(initImg);

    for (int x = 0; x < initImg.getWidth(); x++) {
      for (int y = 0; y < initImg.getHeight(); y++) {
        RGB val = initImg.getPixelAt(x, y);
        assertTrue(ImageTest.sameRGB(finalImg.getPixelAt(x, y),
                new RGB(
                        Integer.max(0, val.get("red") - change),
                        Integer.max(0, val.get("green") - change),
                        Integer.max(0, val.get("blue") - change))));
      }
    }

    change = 255; // should make all the images white
    darken = new Darken(change);
    initImg = new MockImage();
    finalImg = darken.doTo(initImg);
    for (int x = 0; x < initImg.getWidth(); x++) {
      for (int y = 0; y < initImg.getHeight(); y++) {
        RGB val = initImg.getPixelAt(x, y);
        assertTrue(ImageTest.sameRGB(finalImg.getPixelAt(x, y),
                new RGB(
                        Integer.max(0, val.get("red") - change),
                        Integer.max(0, val.get("green") - change),
                        Integer.max(0, val.get("blue") - change))));
      }
    }

    change = 50;
    darken = new Darken(change);
    initImg = new MockImage(new Random(2)); // basically guarantees that some values will
    // be near max and others will be near 0
    finalImg = darken.doTo(initImg);
    for (int x = 0; x < initImg.getWidth(); x++) {
      for (int y = 0; y < initImg.getHeight(); y++) {
        RGB val = initImg.getPixelAt(x, y);
        assertTrue(ImageTest.sameRGB(finalImg.getPixelAt(x, y),
                new RGB(
                        Integer.max(0, val.get("red") - change),
                        Integer.max(0, val.get("green") - change),
                        Integer.max(0, val.get("blue") - change))));
      }
    }
  }
}