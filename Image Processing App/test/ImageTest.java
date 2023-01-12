import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.ImageReader;
import model.image.Image;
import model.image.RGB;
import model.image.ViewImage;
import model.image.ViewImageImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * An abstract test class for images with the same methods.
 * - {@code getWidth}
 * - {@code getHeight}
 * - {@code getMaxValue}
 * - {@code getPixelAt}
 * - {@code copy}
 * - Test for similarity with the expected image grid
 * - Invalid width or height in getPixelAt or setPixelAt
 */
abstract class ImageTest {

  protected List<Image> examples;

  protected List<Integer> expHeight;

  protected List<Integer> expWidth;

  protected List<Integer> expMaxValue;

  protected List<RGB[][]> expImageArrays;

  protected int examplesNum;


  public ImageTest() {
    this.examplesNum = 0;
    this.examples = new ArrayList<>();
    this.expHeight = new ArrayList<>();
    this.expWidth = new ArrayList<>();
    this.expMaxValue = new ArrayList<>();
    this.expImageArrays = new ArrayList<>();
  }

  /**
   * Checks for equality of image grid and the specified grid. Throws AssertError if at least one
   * pixel is wrong.
   *
   * @param image      the image to be checked
   * @param imageArray the array to be compared with
   * @return true if all the values are equal.
   */

  protected static boolean assertImageGrid(Image image, RGB[][] imageArray) throws
      AssertionError {
    int width = image.getWidth();
    int height = image.getHeight();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        assertTrue("pixel at position (" + j + ", " + i + ") is incorrect: ",
            sameRGB(imageArray[i][j], image.getPixelAt(j, i)));
      }
    }
    return true;
  }

  // Tests the RGB difference
  protected static boolean notSameRGB(RGB p1, RGB p2) {
    return (p1.get("red") != p2.get("red") || p1.get("blue") != p2.get("blue")
        || p1.get("green") != p2.get("green"));
  }

  // Tests the RGB similarity
  protected static boolean sameRGB(RGB p1, RGB p2) {
    return (p1.get("red") == p2.get("red") && p1.get("blue") == p2.get("blue")
        && p1.get("green") == p2.get("green"));
  }

  // Tests the images similarity
  protected static boolean sameImage(Image image1, Image image2) {
    int height = image1.getHeight();
    int width = image1.getWidth();
    if (height == image2.getHeight() && width == image2.getWidth()
        && image1.getMaxValue() == image2.getMaxValue()) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (notSameRGB(image1.getPixelAt(j, i), image2.getPixelAt(j, i))) {
            return false;
          }
        }
      }
      return true;
    } else {
      return false;
    }
  }

  protected void addExample(Image example,
      int width, int height, int maxValue, RGB[][] imageArray) {
    this.examplesNum += 1;
    this.examples.add(example);
    this.expWidth.add(width);
    this.expHeight.add(height);
    this.expMaxValue.add(maxValue);
    this.expImageArrays.add(imageArray);
  }

  @Test
  public void testGetWidth() {
    for (int i = 0; i < this.examplesNum; i++) {
      Image img = this.examples.get(i);
      int width = this.expWidth.get(i);
      assertEquals(img.getWidth(), width);
    }
  }

  @Test
  public void testGetHeight() {
    for (int i = 0; i < this.examplesNum; i++) {
      Image img = this.examples.get(i);
      int height = this.expHeight.get(i);
      assertEquals(img.getHeight(), height);
    }
  }

  @Test
  public void testGetMaxValue() {
    for (int i = 0; i < this.examplesNum; i++) {
      Image img = this.examples.get(i);
      int maxValue = this.expWidth.get(i);
      assertEquals(img.getWidth(), maxValue);
    }
  }

  @Test
  public void testImageArrayAndGetPixel() {
    for (int i = 0; i < this.examplesNum; i++) {
      Image img = this.examples.get(i);
      RGB[][] array = this.expImageArrays.get(i);
      assertTrue(assertImageGrid(img, array));
    }
  }

  @Test
  public void testCopy() {
    for (int i = 0; i < this.examplesNum; i++) {
      Image initial = this.examples.get(i);
      Image copied = initial.copy();
      assertTrue(sameImage(initial, copied));
    }
  }



  @Test
  public void getPixelOutOfBounds() {
    for (int i = 0; i < this.examplesNum; i++) {
      Image img = this.examples.get(i);
      int height = this.expHeight.get(i);
      int width = this.expWidth.get(i);
      try {
        img.getPixelAt(width - 1, height);
        fail("Accepted index greater than height");
      } catch (IllegalArgumentException ignored) {
      }

      try {
        img.getPixelAt(width, height - 1);
        fail("Accepted index greater than width");
      } catch (IllegalArgumentException ignored) {
      }

      try {
        img.getPixelAt(width - 1, -1);
        fail("Accepted negative height");
      } catch (IllegalArgumentException ignored) {
      }

      try {
        img.getPixelAt(-1, height - 1);
        fail("Accepted negative width");
      } catch (IllegalArgumentException ignored) {
      }
    }
  }

  @Test
  public void setPixelOutOfBounds() {
    for (int i = 0; i < this.examplesNum; i++) {
      Image img = this.examples.get(i);
      int height = this.expHeight.get(i);
      int width = this.expWidth.get(i);
      try {
        img.setPixelAt(width - 1, height, new RGB(0, 0, 0));
        fail("Accepted index greater than height");
      } catch (IllegalArgumentException ignored) {
      }

      try {
        img.setPixelAt(width, height - 1, new RGB(0, 0, 0));
        fail("Accepted index greater than width");
      } catch (IllegalArgumentException ignored) {
      }

      try {
        img.setPixelAt(width - 1, -1, new RGB(0, 0, 0));
        fail("Accepted negative height");
      } catch (IllegalArgumentException ignored) {
      }

      try {
        img.setPixelAt(-1, height - 1, new RGB(0, 0, 0));
        fail("Accepted negative width");
      } catch (IllegalArgumentException ignored) {
      }
    }
  }

  @Test
  public void testImmutableCopy() {
    for (int i = 0; i < this.examplesNum; i++) {
      Image init = this.examples.get(i);
      Image initCopy = init.copy();
      assertTrue(sameImage(init, initCopy));

      initCopy.setPixelAt(0, 0, new RGB(0, 0, 0));

      assertFalse(sameImage(init, initCopy));
    }
  }


  /**
   * test class for PPMImage.
   */
  public static class TestPPMImage extends ImageTest {

    public TestPPMImage() throws IOException {
      Image img1 = ImageReader.read(
              "res/SampleImageVariations/simpleImage.ppm");

      RGB[][] coloredArray = new RGB[][]{
              {new RGB(255, 0, 0),
               new RGB(0, 255, 0),
               new RGB(0, 0, 255)},
              {new RGB(255, 255, 255),
               new RGB(255, 255, 255),
               new RGB(0, 0, 0)}};
      this.addExample(img1, 3, 2, 255, coloredArray);
    }

    @Test
    public void testSetPixelAtCorrect() {
      for (int i = 0; i < this.examplesNum; i++) {
        Image img = this.examples.get(i);
        int height = this.expHeight.get(i);
        int width = this.expWidth.get(i);
        int maxValue = this.expMaxValue.get(i);
        RGB[][] array = this.expImageArrays.get(i);
        for (int w = 0; w < width; w++) {
          for (int h = 0; h < height; h++) {
            RGB prev = img.getPixelAt(w, h);
            assertTrue(sameRGB(prev, array[h][w]));
            img.setPixelAt(w, h, new RGB(maxValue - 1, maxValue - 1, maxValue - 1));

            RGB newP = img.getPixelAt(w, h);
            assertTrue(sameRGB(newP,
                new RGB(maxValue - 1, maxValue - 1, maxValue - 1)));

            img.setPixelAt(w, h, prev);
            assertTrue(sameRGB(img.getPixelAt(w, h), prev));
          }
        }
      }
    }

    @Test(expected = IllegalStateException.class)
    public void testSetPixelWrongPixel() {
      Image img = this.examples.get(0);
      img.setPixelAt(0, 0, new RGB(256, 0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongFileName() throws IOException {
      ImageReader.read("res/");
    }

    @Test(expected = IOException.class)
    public void inaccessibleFile() throws IOException {
      FileWriter file = new FileWriter("res/KoalaNew.ppm");
      ImageReader.read("res/KoalaNew.ppm");
    }

    @Test
    public void testCustomFile() throws IOException {
      FileWriter test = new FileWriter("test.ppm");
      test.flush();
      test.append("P3\n")
          .append("1 1\n")
          .append("100\n")
          .append("1 1 1");
      test.close();
      Image image = ImageReader.read("test.ppm");

      assertEquals(image.getHeight(), 1);
      assertEquals(image.getWidth(), 1);
      assertEquals(image.getMaxValue(), 100);
      assertTrue(sameRGB(image.getPixelAt(0, 0), new RGB(1, 1, 1)));
    }

    @Test(expected = IOException.class)
    public void testWrongP3() throws IOException {
      FileWriter test = new FileWriter("test.ppm");
      test.flush();
      test.append("P4\n")
          .append("1 1\n")
          .append("100\n")
          .append("1 1 1");
      test.close();
      Image image = ImageReader.read("test.ppm");
    }

    @Test(expected = IOException.class)
    public void testWrongSize() throws IOException {
      FileWriter test = new FileWriter("test.ppm");
      test.flush();
      test.append("P3\n")
          .append("1 2\n")
          .append("100\n")
          .append("1 1 1");
      test.close();
      Image image = ImageReader.read("test.ppm");
    }

    @Test(expected = IOException.class)
    public void testTooSmallSize() throws IOException {
      FileWriter test = new FileWriter("test.ppm");
      test.flush();
      test.append("P3\n")
          .append("1 1\n")
          .append("100\n")
          .append("1 1 1 1 1 1");
      test.close();
      Image image = ImageReader.read("test.ppm");
    }

    @Test(expected = IOException.class)
    public void pixelIncorrectValue() throws IOException {
      FileWriter test = new FileWriter("test.ppm");
      test.flush();
      test.append("P3\n")
          .append("1 1\n")
          .append("100\n")
          .append("1 1 101");
      test.close();
      Image image = ImageReader.read("test.ppm");
    }
  }

  /**
   * Tests for jpg, png, bmp, gif images.
   */
  public static class TestGeneralImages extends ImageTest {
    public TestGeneralImages() throws IOException {
      RGB[][] array = {
              {new RGB(255, 0, 0),
               new RGB(255, 0, 0),
               new RGB(255, 0, 0),
               new RGB(255, 0, 0),
               new RGB(255, 0, 0),
               new RGB(255, 0, 0),
               new RGB(255, 0, 0),
               new RGB(255, 0, 0),
               new RGB(255, 0, 0),
               new RGB(255, 0, 0)},
              {new RGB(0, 255, 0),
               new RGB(0, 255, 0),
               new RGB(0, 255, 0),
               new RGB(0, 255, 0),
               new RGB(0, 255, 0),
               new RGB(0, 255, 0),
               new RGB(0, 255, 0),
               new RGB(0, 255, 0),
               new RGB(0, 255, 0),
               new RGB(0, 255, 0)},
              {new RGB(0, 0, 255),
               new RGB(0, 0, 255),
               new RGB(0, 0, 255),
               new RGB(0, 0, 255),
               new RGB(0, 0, 255),
               new RGB(0, 0, 255),
               new RGB(0, 0, 255),
               new RGB(0, 0, 255),
               new RGB(0, 0, 255),
               new RGB(0, 0, 255)},
              {new RGB(255, 255, 0),
               new RGB(255, 255, 0),
               new RGB(255, 255, 0),
               new RGB(255, 255, 0),
               new RGB(255, 255, 0),
               new RGB(255, 255, 0),
               new RGB(255, 255, 0),
               new RGB(255, 255, 0),
               new RGB(255, 255, 0),
               new RGB(255, 255, 0)},
              {new RGB(255, 255, 255),
               new RGB(255, 255, 255),
               new RGB(255, 255, 255),
               new RGB(255, 255, 255),
               new RGB(255, 255, 255),
               new RGB(255, 255, 255),
               new RGB(255, 255, 255),
               new RGB( 255, 255, 255),
               new RGB(255, 255, 255),
               new RGB(255, 255, 255)}};


      this.addExample(ImageReader.read("res/SampleImageVariations/ex2.ppm"), 10,
          5, 255, array);
      this.addExample(ImageReader.read("res/SampleImageVariations/ex2.png"), 10,
          5, 255, array);
      this.addExample(ImageReader.read("res/SampleImageVariations/ex2.tiff"), 10,
          5, 255, array);
      this.addExample(ImageReader.read("res/SampleImageVariations/ex2.bmp"), 10,
          5, 255, array);
    }
  }

  @Test
  public void testCorrectConversion() throws IOException {
    Image img1 = ImageReader.read("res/SampleImageVariations/ex2.png");
    ImageReader.save(img1, "res/SampleImageVariations/ex2.ppm");

    Image img2 = ImageReader.read("res/SampleImageVariations/ex2.ppm");
    assertTrue(sameImage(img1, img2));
  }
  
  @Test
  public void testImageView() {
    for (int i = 0; i < this.examplesNum; i++) {
      Image img1 = this.examples.get(i);
  
      ViewImage imageV = new ViewImageImpl(img1);
      
      assertEquals(img1.getWidth(), imageV.getWidth());
      assertEquals(img1.getHeight(), imageV.getHeight());
      
      for (int x = 0; x < img1.getWidth(); x++) {
        for (int y = 0; y < img1.getHeight(); y++) {
          assertTrue(sameRGB(img1.getPixelAt(x, y), imageV.getPixelAt(x, y)));
        }
      }
    }
  }
}
