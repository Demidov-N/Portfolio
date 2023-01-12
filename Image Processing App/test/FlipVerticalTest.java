import org.junit.Test;

import java.util.Random;

import model.image.Image;
import model.transformations.FlipVertical;
import model.transformations.Transformation;

import static org.junit.Assert.assertTrue;

/**
 * Test class for Vertical Flip Transformation.
 */
public class FlipVerticalTest {

  /**
   * test doTo method.
   */
  @Test
  public void testFlipV() {

    Transformation flipV = new FlipVertical();
    Image imgInit = new MockImage(new Random(2));
    Image imgNew = flipV.doTo(imgInit);

    for (int y = 0; y < imgNew.getHeight(); y++) {
      for (int x = 0; x < imgNew.getWidth(); x++) {
        assertTrue(ImageTest.sameRGB(
                imgInit.getPixelAt(x, imgNew.getHeight() - 1 - y), imgNew.getPixelAt(x, y)));
      }
    }


  }

}