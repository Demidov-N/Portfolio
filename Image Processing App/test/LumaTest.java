import org.junit.Test;

import java.util.Random;

import model.image.Image;
import model.image.RGB;
import model.transformations.Luma;
import model.transformations.Transformation;

import static org.junit.Assert.assertTrue;

/**
 * test class for Luma Transformation.
 */
public class LumaTest {

  /**
   * test doTo method.
   */
  @Test
  public void doTo() {
    Transformation luma = new Luma();
    Image imgInit = new MockImage(new Random(2));
    Image img = luma.doTo(imgInit);

    for (int x = 0; x < imgInit.getWidth(); x++) {
      for (int y = 0; y < imgInit.getHeight(); y++) {
        int red = imgInit.getPixelAt(x, y).get("red");
        int green = imgInit.getPixelAt(x, y).get("green");
        int blue = imgInit.getPixelAt(x, y).get("blue");
        int lumaVal = (int) ((0.2126 * red) + (0.7152 * green) + (0.0722 * blue));
        RGB rgb = new RGB(lumaVal, lumaVal, lumaVal);
        assertTrue(ImageTest.sameRGB(img.getPixelAt(x, y), rgb));
      }
    }
  }

}