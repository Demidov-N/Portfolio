import org.junit.Test;

import java.util.Random;

import model.image.Image;
import model.image.RGB;
import model.transformations.Sepia;
import model.transformations.Transformation;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Sepia transformations.
 */
public class SepiaTest {

  @Test
  public void doTo() {

    Transformation transformation = new Sepia();
    Image img = new MockImage(new Random(2));
    Image imgTransformed = transformation.doTo(img);
    double[][] matrix = {{0.399, 0.789, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};

    int imgWidth = img.getWidth();
    int imgHeight = img.getHeight();

    for (int x = 0; x < imgWidth; x++) {
      for (int y = 0; y < imgHeight; y++) {
        int red = img.getPixelAt(x, y).get("red");
        int green = img.getPixelAt(x, y).get("green");
        int blue = img.getPixelAt(x, y).get("blue");


        int newRed = (int) ((matrix[0][0] * red) + (matrix[0][1] * green) + (matrix[0][2] * blue));
        int newG = (int) ((matrix[1][0] * red) + (matrix[1][1] * green) + (matrix[1][2] * blue));
        int newBlue = (int) ((matrix[2][0] * red) + (matrix[2][1] * green) + (matrix[2][2] * blue));

        assertEquals(imgTransformed.getPixelAt(x, y),
                new RGB(fixRGB(newRed), fixRGB(newG), fixRGB(newBlue)));

      }
    }
  }

  private static int fixRGB(int val) {
    if (val < 0) {
      return 0;
    } else if (val > 255) {
      return 255;
    } else {
      return val;
    }
  }

}