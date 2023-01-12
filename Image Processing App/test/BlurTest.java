import org.junit.Test;

import java.util.Random;

import model.image.Image;
import model.image.RGB;
import model.transformations.Blur;
import model.transformations.Transformation;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the blur transformations.
 */
public class BlurTest {

  @Test
  public void testDo() {

    Transformation transformation = new Blur();
    Image imgInit = new MockImage(new Random(2));
    double[][] kernel = {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};


    Image img = imgInit.copy();
    Image imgTransformed = transformation.doTo(img);
    int imgWidth = img.getWidth();
    int imgHeight = img.getHeight();
    int kernelMid = kernel.length / 2;


    for (int x = 0; x < imgWidth; x++) {
      for (int y = 0; y < imgHeight; y++) {
        //Going into each pixel
        int[] vals = new int[3];
        String[] channels = {"red", "green", "blue"};
        for (int i = 0; i < channels.length; i++) {
          //for every channel in RGB
          double val = 0;
          for (int kx = 0; kx < kernel.length; kx++) {
            //go into each kernel val and make sure
            for (int ky = 0; ky < kernel.length; ky++) {
              //that it is within the image
              int distY = ky - kernelMid;
              int distX = kx - kernelMid;
              if ((x + distX < imgWidth) && (x + distX >= 0)
                      && (y + distY < imgHeight) && (y + distY >= 0)) {
                // pos is in image
                int channelColor = imgInit.getPixelAt(x + distX, y + distY).get(channels[i]);
                val += (channelColor * kernel[kx][ky]);
                //keep adding kernel vals
              }
            }
          }
          if (val > 255) {
            val = 255;
          } else if (val < 0) {
            val = 0;
          }
          vals[i] = (int) val;
        }
        //checking that the pixel matches the tranformation
        assertEquals(imgTransformed.getPixelAt(x, y), new RGB(vals[0], vals[1], vals[2]));
      }
    }

  }


}