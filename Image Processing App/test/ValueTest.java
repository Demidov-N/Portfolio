import org.junit.Test;

import java.util.Random;

import model.image.Image;
import model.image.RGB;
import model.transformations.Transformation;
import model.transformations.Value;

import static org.junit.Assert.assertTrue;


/**
 * Tests for value transformation.
 */
public class ValueTest {
  /**
   * test doTo method.
   */
  @Test
  public void doTo() {
    Transformation value = new Value();
    Image imgInit = new MockImage(new Random(2));
    Image img = value.doTo(imgInit);

    for (int x = 0; x < imgInit.getWidth(); x++) {
      for (int y = 0; y < imgInit.getHeight(); y++) {
        int red = imgInit.getPixelAt(x, y).get("red");
        int green = imgInit.getPixelAt(x, y).get("green");
        int blue = imgInit.getPixelAt(x, y).get("blue");
        int max = Integer.max(red, Integer.max(green, blue));
        assertTrue(ImageTest.sameRGB(
                img.getPixelAt(x, y), new RGB(max, max, max)));
      }
    }
  }
}