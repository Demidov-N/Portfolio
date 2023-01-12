import org.junit.Test;

import model.image.RGB;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the RGB class.
 */
public class RGBTest {
  @Test
  public void testCreations() {
    RGB white = new RGB();
    RGB red = new RGB(255, 0, 0);
    RGB green = new RGB(0, 255, 0);
    RGB blue = new RGB(0, 0, 255);

    assertEquals(255, white.get("red"));
    assertEquals(255, white.get("blue"));
    assertEquals(255, white.get("green"));

    assertEquals(255, red.get("red"));
    assertEquals(0, red.get("blue"));
    assertEquals(0, red.get("green"));

    assertEquals(0, green.get("red"));
    assertEquals(0, green.get("blue"));
    assertEquals(255, green.get("green"));

    assertEquals(0, blue.get("red"));
    assertEquals(255, blue.get("blue"));
    assertEquals(0, blue.get("green"));
  }
}