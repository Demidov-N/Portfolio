package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import jdk.jshell.spi.ExecutionControl.StoppedException;
import model.ImageProcessor;
import model.transformations.Blur;
import model.transformations.FlipHorizontal;
import model.transformations.FlipVertical;
import model.transformations.Greyscale;
import model.transformations.Intensity;
import model.transformations.Luma;
import model.transformations.Sepia;
import model.transformations.Sharpen;
import model.transformations.Value;
import view.ImgProcessorView;

/**
 * A simple terminal controller, which has a specific Readable can be specified that is going
 * to execute the sequence of the actions from it until it encounters "stop" word or until the
 * sequence of actions finishes. Can work in two modes:
 * - If the input readable was not specified, then it is going to work through terminal and
 *   execute commands directly as soon as enter was pressed. If there is an error, it is going to
 *   show where the error is, but the execution continues. It stops if "stop" word is encountered.
 * - If the input readable was specified, then it is going to execute all the code from the readable
 *   until it encounters the "stop" word there, or until the file is empty.
 */
public final class ScriptController implements ImgProcessorController {

  private final ImageProcessor processor;
  private final ImgProcessorView output;
  private final Scanner scan;
  private final Map<String, Function<Scanner, ImgProcessorCommand>> knownCommands;


  /**
   * Creates a synchronous controller, that is awaiting inputs from the command line. Stops only
   * when it encounters the stop word.
   * @param processor The processor to be used in the controller, the one that is going to execute
   *                  commands.
   * @param view The view to be used in the controller as output
   */
  public ScriptController(ImageProcessor processor, ImgProcessorView view) {
    this(new InputStreamReader(System.in), processor, view);
  }

  /**
   * Creates an asynchronous controller, that is not awaiting inputs from the command line and
   * going to stop executing if there will be no more lines in the input, of if the stop word is
   * encountered before.
   * @param input the input from where the commands will be written from
   * @param processor The processor to be used in the controller, the one that is going to execute
   *                  commands.
   * @param view The view to be used in the controller as output
   */
  public ScriptController(Readable input, ImageProcessor processor,
      ImgProcessorView view) {
    this.processor = processor;
    this.output = view;
    this.scan = new Scanner(input);
    this.knownCommands = new HashMap<>();

    this.knownCommands.put("load", (s) -> new LoadCommand(s.next(), s.next()));
    this.knownCommands.put("save", (s) -> new SaveCommand(s.next(), s.next()));
    this.knownCommands.put("red-component", (s) -> new TransformationCommand(s.next(), s.next(),
        new Greyscale("red")));
    this.knownCommands.put("green-component", (s) -> new TransformationCommand(s.next(), s.next(),
        new Greyscale("green")));
    this.knownCommands.put("blue-component", (s) -> new TransformationCommand(s.next(), s.next(),
        new Greyscale("blue")));
    this.knownCommands.put("value-component", (s) -> new TransformationCommand(s.next(), s.next(),
        new Value()));
    this.knownCommands.put("luma-component", (s) -> new TransformationCommand(s.next(), s.next(),
        new Luma()));
    this.knownCommands.put("blur", (s) -> new TransformationCommand(s.next(), s.next(),
        new Blur()));
    this.knownCommands.put("sharpen", (s) -> new TransformationCommand(s.next(), s.next(),
        new Sharpen()));
    this.knownCommands.put("greyscale", (s) -> new TransformationCommand(s.next(), s.next(),
        new Luma()));
    this.knownCommands.put("sepia", (s) -> new TransformationCommand(s.next(), s.next(),
        new Sepia()));
    this.knownCommands.put("intensity-component",
        (s) -> new TransformationCommand(s.next(), s.next(), new Intensity()));
    this.knownCommands.put("horizontal-flip", (s) -> new TransformationCommand(s.next(), s.next(),
        new FlipHorizontal()));
    this.knownCommands.put("vertical-flip", (s) -> new TransformationCommand(s.next(), s.next(),
        new FlipVertical()));
    this.knownCommands.put("brighten",
        (s) -> new BrightenCommand(s.nextInt(), s.next(), s.next()));
    this.knownCommands.put("help",
        (s) -> new HelpCommand(this.output));
  }


  /**
   * The main go method for the controller.
   */
  @Override
  public void runController() throws IllegalStateException {
    while (scan.hasNext()) {
      ImgProcessorCommand c;
      try {
        try {
          String in = this.scannerNext();
          Function<Scanner, ImgProcessorCommand> cmd =
              knownCommands.getOrDefault(in, null);
          if (cmd == null) {
            this.output.addMessage("ERROR: Unknown command " + in + " check the inputs.\n");
          } else {
            try {
              c = cmd.apply(scan);
              c.runCommand(this.processor);
              if (!in.equals("help")) {
                this.output.addMessage(
                        "Successfully executed command " + in + "\n");
              }
            } catch (Exception e) {
              this.output.addMessage("ERROR: " + e.getMessage() + "\n");
            }
          }
        } catch (StoppedException e) {
          this.output.addMessage("Execution stopped, encountered stop word\n");
          return;
        } catch (NoSuchElementException e) {
          this.output.addMessage("ERROR: Not enough inputs specified for the command\n");
        }
      } catch (IOException e) {
        throw new IllegalStateException("The input or output became inaccessible.\n");
      }
    }
  }

  /**
   * Returns the next value of the scanner, or throws an exception if encountered the "stop" word.
   * @return the next value of the scanner if it is not the "stop" word
   * @throws StoppedException if the execution was stopped by encountering "stop" word
   * @throws NoSuchElementException if there is no more elements in the scanner left, but the
   *                                scanner expects any
   */
  private String scannerNext() throws StoppedException, NoSuchElementException {
    String word = this.scan.next();
    if (word.equals("stop")) {
      throw new StoppedException();
    } else {
      return word;
    }
  }
}
