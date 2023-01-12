import java.io.FileWriter;
import java.io.IOException;
import org.junit.Test;
import view.ImgProcessorTextView;
import view.ImgProcessorView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for view, to see that it outputs the right thing.s
 */
public class ViewTest {

  @Test
  public void testMessage() throws IOException {
    StringBuilder builder = new StringBuilder();
    ImgProcessorView view = new ImgProcessorTextView(builder);

    view.addMessage("HelloWorld");
    assertEquals(builder.toString(), "HelloWorld");
  }

  @Test(expected = IOException.class)
  public void testInaccessibleOutput() throws IOException {
    FileWriter builder = new FileWriter("logFile.txt");
    ImgProcessorView view = new ImgProcessorTextView(builder);

    builder.close();
    view.addMessage("HelloWorld");
  }
}
