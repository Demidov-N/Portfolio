import org.junit.Test;

import java.util.Random;

import model.image.Image;
import model.transformations.Greyscale;
import model.transformations.Transformation;

import static org.junit.Assert.assertEquals;

/**
 * test class for Grayscale transformation.
 */
public class GreyscaleTest {

  /**
   * test constructor Exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void exception() {
    new Greyscale("purple");
  }

  /**
   * test doTo method.
   */
  @Test
  public void doTo() {
    String type = "red";
    Transformation grey = new Greyscale(type);
    Image imgInit = new MockImage(new Random(2));
    Image imgTr = grey.doTo(imgInit);

    for (int x = 0; x < imgInit.getWidth(); x++) {
      for (int y = 0; y < imgInit.getHeight(); y++) {
        int color = imgInit.getPixelAt(x, y).get(type);
        assertEquals(color, imgTr.getPixelAt(x, y).get("red"));
        assertEquals(color, imgTr.getPixelAt(x, y).get("green"));
        assertEquals(color, imgTr.getPixelAt(x, y).get("blue"));
      }
    }

    type = "green";
    grey = new Greyscale(type);
    imgInit = new MockImage(new Random(2));
    imgTr = grey.doTo(imgInit);

    for (int x = 0; x < imgInit.getWidth(); x++) {
      for (int y = 0; y < imgInit.getHeight(); y++) {
        int color = imgInit.getPixelAt(x, y).get(type);
        assertEquals(color, imgTr.getPixelAt(x, y).get("red"));
        assertEquals(color, imgTr.getPixelAt(x, y).get("green"));
        assertEquals(color, imgTr.getPixelAt(x, y).get("blue"));
      }
    }

    type = "blue";
    grey = new Greyscale(type);
    imgInit = new MockImage(new Random(2));
    imgTr = grey.doTo(imgInit);

    for (int x = 0; x < imgInit.getWidth(); x++) {
      for (int y = 0; y < imgInit.getHeight(); y++) {
        int color = imgInit.getPixelAt(x, y).get(type);
        assertEquals(color, imgTr.getPixelAt(x, y).get("red"));
        assertEquals(color, imgTr.getPixelAt(x, y).get("green"));
        assertEquals(color, imgTr.getPixelAt(x, y).get("blue"));
      }
    }


  }
}