import org.junit.Test;

import java.util.Random;

import model.Histogram;
import model.image.Image;
import model.image.RGB;
import model.image.ViewImageImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Histogram.
 */
public class HistogramTest {

  Histogram histogram;
  Image img = new MockImage(new Random(2));

  private boolean test() {
    int maxVal = img.getMaxValue();
    double[] redExpect = new double[maxVal];
    double[] blueExpect = new double[maxVal];
    double[] greenExpect = new double[maxVal];
    double maxRed = 0;
    double maxGreen = 0;
    double maxBlue = 0;

    for (int i = 0; i < img.getMaxValue(); i++) {
      redExpect[i] = 0;
      greenExpect[i] = 0;
      blueExpect[i] = 0;
    }

    for (int x = 0; x < img.getWidth(); x++) {
      for (int y = 0; y < img.getHeight(); y++) {
        RGB rgb = img.getPixelAt(x, y);
        redExpect[rgb.get("red")]++;
        blueExpect[rgb.get("blue")]++;
        greenExpect[rgb.get("green")]++;
      }
    }
    for (int i = 0; i < histogram.getSize(); i++) {
      if (redExpect[i] > maxRed) {
        maxRed = redExpect[i];
      }
      if (blueExpect[i] > maxBlue) {
        maxBlue = blueExpect[i];
      }
      if (greenExpect[i] > maxGreen) {
        maxGreen = greenExpect[i];
      }
    }

    //make sure size is correct
    assertEquals(histogram.getSize(), img.getMaxValue());

    for (int i = 0; i < img.getMaxValue(); i++) {
      assertEquals(histogram.getValue(i, "red"),
              (int) Math.round((redExpect[i] / maxRed) * 100));
      assertEquals(histogram.getValue(i, "green"),
              (int) Math.round((greenExpect[i] / maxGreen) * 100));
      assertEquals(histogram.getValue(i, "blue"),
              (int) Math.round((blueExpect[i] / maxBlue) * 100));
    }
  
    return true;
  }


  @Test
  public void testGeneral() {
    histogram = new Histogram(img);
    assertTrue(test());
  }

  @Test
  public void testViewImage() {
    histogram = new Histogram(new ViewImageImpl(img));
    assertTrue(test());
  }
}