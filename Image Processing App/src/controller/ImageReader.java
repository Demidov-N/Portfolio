package controller;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.image.BuffImage;
import model.image.Image;
import model.image.RGB;


/**
 * This class contains static utility methods for reading the files from the directory. It
 * should have methods for reading any type of image extensions, and also any utilities that might
 * support loading.
 */
public final class ImageReader {

  /**
   * Checks if the extension of the class instance is the same as the specified filename.
   *
   * @param path path to the expected name
   * @return True if the extension is the same type as the class instance.
   */
  public static boolean sameExtensionAs(String path, String extension) {
    if (!extension.equals("")) {
      return getExtension(path).equals(extension);
    } else {
      return false;
    }
  }

  // gets extension of the specific path.
  
  /**
   * Gets an extension of the specified string.
   * @param path the input string, the one to get extension from.
   * @return the extension of the file, or empty line if there is none.
   */
  public static String getExtension(String path) {
    int i = path.lastIndexOf('.');
    if (i > 0) {
      return (path.substring(i + 1));
    } else {
      return "";
    }
  }

  /**
   * Reads an image based on the path of an image in the project folder. The images extensions
   * supported: jpg, png, jpeg, img, gif, tif, ppm, png.
   *
   * @param filename The path to an Image.
   * @throws IOException              If the file is poorly formatted.
   * @throws IllegalArgumentException If the value specified in the
   */
  public static Image read(String filename) throws IOException,
      IllegalArgumentException {
    if (sameExtensionAs(filename, "ppm")) { // Checks if the image is ppm extension
      return readPPM(filename);
    } else {
      return readSupported(filename);
    }
  }

  // reads the files supported by ImageIO
  private static Image readSupported(String pathname) {
    try {
      File f = new File(pathname);
      BufferedImage img = ImageIO.read(f);
      if (isRGB(getExtension(pathname))) { // Checks if the extension is supporting RGB
        img = convertToARGB(img);
      }
      Image result = new BuffImage(img.getWidth(), img.getHeight(), 255);
      for (int i = 0; i < img.getWidth(); i++) {
        for (int j = 0; j < img.getHeight(); j++) {
          result.setPixelAt(i, j, new RGB(img.getRGB(i, j)));
        }
      }
      return result;

    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Error in reading file: " + pathname
          + " is a directory or " + "does not exist");
    } catch (IOException e) {
      throw new IllegalArgumentException("Error in reading file: " + pathname
          + " is inaccessible or broken, check values");
    }
  }

  // Reads a ppm image
  private static Image readPPM(String filename) throws IOException,
      IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
      if (!sameExtensionAs(filename, "ppm")) {
        throw new IllegalArgumentException("File name " + filename + " is not PPM file");
      }
    } catch (FileNotFoundException e) {
      throw new IOException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();

    //read the file line by line, and populate a string. This will throw away any exceptions
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;
    try {
      token = sc.next();
      if (!token.equals("P3")) {
        throw new IOException("Invalid PPM file: plain RAW file should begin with P3");
      }

      // Initialize width
      int width = sc.nextInt();

      // Initialize height
      int height = sc.nextInt();

      // Initialize maximum value of pixel
      int maxPixel = sc.nextInt();

      // Initialize image array
      Image img = new BuffImage(width, height, maxPixel);
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          if (r > maxPixel) {
            throw new IOException(
                "Red color value " + r + " at position (" + (j + 1) + ", " + (i + 1)
                    + " is greater " + "than specified max value " + maxPixel);
          }
          if (g > maxPixel) {
            throw new IOException(
                "Green color value " + g + " at position (" + (j + 1) + ", " + (i + 1)
                    + " is greater " + "than specified max value " + maxPixel);
          }
          if (b > maxPixel) {
            throw new IOException(
                "Blue color value " + b + " at position (" + (j + 1) + ", " + (i + 1)
                    + " is greater " + "than specified max value " + maxPixel);
          }
          RGB pix = new RGB(r, g, b);
          img.setPixelAt(j, i, pix);
        }
      }
      if (sc.hasNext()) {
        throw new IOException("There is some text left inside the file");
      }
      return img;
    } catch (InputMismatchException e) {
      throw new IOException("Value specified is not a pixel value: "
          + e.getLocalizedMessage());
    } catch (NoSuchElementException e) {
      throw new IOException("Not enough values specified in the .ppm file: "
          + e.getLocalizedMessage());
    }
  }

  // Mutates a buffered image, all pixels of the buffered image equals to the image pixels
  private static void mutateBuffered(Image image, BufferedImage result) {
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        result.setRGB(i, j, (image.getPixelAt(i, j).getRGBByte()));
      }
    }
  }

  /**
   * Saves an image in the specified path if it is possible. Any color conversions is made
   * if needed. If the format does not support alpha channel and there is transparent pixels,
   * alpha channel is ignored.
   * @param image An image to save.
   * @param path A path at which to save an image
   * @throws IOException if the path specified is inaccessible or broken
   * @throws IllegalArgumentException If the path specified is the directory.
   */
  public static void save(Image image, String path) throws IOException, IllegalArgumentException {
    try {
      File output = new File(path);
      output.mkdirs();
      String extension = getExtension(path);
      BufferedImage saved;
      if (extension.equals("ppm")) {
        savePPM(image, output);
      } else {
        if (extension.equals("")) {
          throw new IllegalArgumentException("This save format is not supported: it is a list or"
              + "there is no extension");
        }
        if (!isSupported(extension)) {
          throw new IllegalArgumentException("This save format is not supported: " + extension);
        }

        if (isRGB(extension)) {
          saved =
              new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        } else {
          saved =
              new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        mutateBuffered(image, saved);
        ImageIO.write(saved, extension, output);
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Error in reading file: " + path
          + " is a directory or " + "does not exist");
    } catch (IOException e) {
      throw new IOException("Unable to write out the file. Check if the path is accessible");
    }
  }


  // Checks if the file does not support alpha channel
  private static boolean isRGB(String extension) {
    List<String> extensions = new ArrayList<>(Arrays.asList("jpg", "jpeg", "wbmp", "bmp"));
    return extensions.contains(extension);
  }

  // Checks if the file is supported by ImageIO
  private static boolean isSupported(String extension) {
    List<String> extensions = new ArrayList<>(List.of(ImageIO.getWriterFormatNames()));
    return extensions.contains(extension);
  }


  // Convert to RGB with no alpha
  private static BufferedImage convertToRGB(BufferedImage initialBuff) {

    // Create new image with the RGB type
    BufferedImage newImg = new BufferedImage(initialBuff.getWidth(), initialBuff.getHeight(),
        BufferedImage.TYPE_3BYTE_BGR);

    // Put previous image on top of another with new graphics
    newImg.createGraphics().drawImage(initialBuff, 0, 0, Color.WHITE, null);

    return newImg;
  }

  // Converts to ARGB with alpha
  private static BufferedImage convertToARGB(BufferedImage initialBuff) {

    // Create new image with the RGB type
    BufferedImage newImg = new BufferedImage(initialBuff.getWidth(), initialBuff.getHeight(),
        BufferedImage.TYPE_INT_ARGB);

    // Put previous image on top of another with new graphics
    newImg.createGraphics().drawImage(initialBuff, 0, 0, null);

    return newImg;
  }

  // Save as ppm image
  private static void savePPM(Image image, File output) throws IOException {
    FileWriter file = new FileWriter(output);

    int height = image.getHeight();
    int width = image.getWidth();

    Appendable fileString = new StringWriter();
    fileString.append("P3\n")
        .append(String.format("%d %d\n", width, height))
        .append(image.getMaxValue() + "\n");

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGB pixel = image.getPixelAt(j, i);
        String pixRow = String.format("%d %d %d",
            (int) ((float) pixel.get("red") / 255 * image.getMaxValue()),
            (int) ((float) pixel.get("green") / 255 * image.getMaxValue()),
            (int) ((float) pixel.get("blue") / 255 * image.getMaxValue()));
        fileString.append(pixRow + "\n");
      }
    }
    file.append(fileString.toString());
    file.close();
  }
}

