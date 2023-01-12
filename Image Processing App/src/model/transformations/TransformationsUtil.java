package model.transformations;

import model.image.Image;
import model.image.RGB;

class TransformationsUtil {

  private static void validateKernel(double[][] kernel) {
    int width = kernel.length;
    if (width % 2 == 0) {
      throw new IllegalArgumentException("Even kernel");
    }
    for (double[] row : kernel) {
      if (row.length != width) {
        throw new IllegalArgumentException("Not Square");
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

  /**
   * applies a Kernel on an Img.
   *
   * @param kernel  Kernel must be odd and square
   * @param imgInit img
   * @return the Image with things applied to it
   * @throws IllegalArgumentException if Kernel is invalid
   */
  static Image applyKernel(double[][] kernel, Image imgInit) throws IllegalArgumentException {
    TransformationsUtil.validateKernel(kernel);
    Image img = imgInit.copy();
    int imgWidth = img.getWidth();
    int imgHeight = img.getHeight();
    int kernelMid = kernel.length / 2;


    for (int x = 0; x < imgWidth; x++) {
      for (int y = 0; y < imgHeight; y++) {
        //Going into each pixel
        int[] vals = new int[3];
        String[] channels = {"red", "green", "blue"};
        for (int i = 0; i < channels.length; i++) {
          //for every color channel in RGB
          double val = 0;
          for (int kx = 0; kx < kernel.length; kx++) {
            //go into each kernel val and make sure
            for (int ky = 0; ky < kernel.length; ky++) {
              //that it is within the image
              int distY = ky - kernelMid;
              int distX = kx - kernelMid;
              if ((x + distX < imgWidth) && (x + distX >= 0)
                      && (y + distY < imgHeight) && (y + distY >= 0)) { // pos is in image

                int channelColor = imgInit.getPixelAt(x + distX, y + distY).get(channels[i]);
                val += (channelColor * kernel[kx][ky]);
                //keep adding kernel vals
              }
            }
          }
          vals[i] = TransformationsUtil.fixRGB((int) val);
        }
        RGB rgb = new RGB(vals[0], vals[1], vals[2]);
        img.setPixelAt(x, y, rgb);
      }
    }
    return img;
  }

  /**
   * Applies a transformation to an Image using a color Matrix to apply to each RGB in an Image.
   *
   * @param matrix  the color matrix to apply to RGB
   * @param imgInit initial image
   * @return return a new copy of the image with the transformation applied
   */
  static Image applyMatrix(double[][] matrix, Image imgInit) {
    TransformationsUtil.validateMatrix(matrix);
    Image img = imgInit.copy();
    int imgWidth = imgInit.getWidth();
    int imgHeight = imgInit.getHeight();

    for (int x = 0; x < imgWidth; x++) {
      for (int y = 0; y < imgHeight; y++) {
        int red = img.getPixelAt(x, y).get("red");
        int green = img.getPixelAt(x, y).get("green");
        int blue = img.getPixelAt(x, y).get("blue");


        int newRed = (int) ((matrix[0][0] * red) + (matrix[0][1] * green) + (matrix[0][2] * blue));
        int newG = (int) ((matrix[1][0] * red) + (matrix[1][1] * green) + (matrix[1][2] * blue));
        int newBlue = (int) ((matrix[2][0] * red) + (matrix[2][1] * green) + (matrix[2][2] * blue));

        img.setPixelAt(x, y, new RGB(fixRGB(newRed), fixRGB(newG), fixRGB(newBlue)));
      }
    }
    return img;
  }


  private static void validateMatrix(double[][] matrix) {
    if (matrix.length != 3) {
      throw new IllegalArgumentException("size of matrix is not 3");
    }
    for (double[] col : matrix) {
      if (col.length != 3) {
        throw new IllegalArgumentException("size of matrix is not 3");
      }
    }
  }
}
