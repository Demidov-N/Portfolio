import org.junit.Test;

import java.util.Random;

import model.image.Image;
import model.transformations.FlipHorizontal;
import model.transformations.Transformation;

import static org.junit.Assert.assertTrue;

/**
 * Test class for Horizontal Flip transformation.
 */
public class FlipHorizontalTest {

  /**
   * testing doTo method.
   */
  @Test
  public void testFlipH() {

    Transformation flipH = new FlipHorizontal();
    Image imgInit = new MockImage(new Random(2));
    Image imgNew = flipH.doTo(imgInit);
    for (int y = 0; y < imgNew.getHeight(); y++) {
      for (int x = 0; x < imgNew.getWidth(); x++) {
        assertTrue(ImageTest.sameRGB(imgInit.getPixelAt(
                imgNew.getWidth() - 1 - x, y), imgNew.getPixelAt(x, y)));
      }
    }


  }

}