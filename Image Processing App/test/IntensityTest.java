import org.junit.Test;

import java.util.Random;

import model.image.Image;
import model.image.RGB;
import model.transformations.Intensity;
import model.transformations.Transformation;

import static org.junit.Assert.assertTrue;

/**
 * Tests for intensity transformation.
 */
public class IntensityTest {

  @Test
  public void testDo() {

    Transformation intensity = new Intensity();
    Image imgInit = new MockImage(new Random(2));
    Image img = intensity.doTo(imgInit);


    for (int x = 0; x < imgInit.getWidth(); x++) {
      for (int y = 0; y < imgInit.getHeight(); y++) {
        int red = imgInit.getPixelAt(x, y).get("red");
        int green = imgInit.getPixelAt(x, y).get("green");
        int blue = imgInit.getPixelAt(x, y).get("blue");
        int avg = (red + green + blue) / 3;
        RGB rgb = new RGB(avg, avg, avg);
        assertTrue(ImageTest.sameRGB(img.getPixelAt(x, y), rgb));
      }
    }
  }


}