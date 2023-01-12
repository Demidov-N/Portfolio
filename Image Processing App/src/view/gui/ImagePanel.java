package view.gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Color;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

import model.image.ViewImage;

/**
 * An ImagePanel, that has the working image, on whom transformations are going to be applied.
 */
class ImagePanel extends JPanel {
  
  private final JLabel image;
  
  ImagePanel(ImageGUI mainPanel) {
    super();
    this.image = new JLabel("Nothing here now");
    add(this.image);
  }
  
  void showImage(ViewImage image) {
    this.image.setText("");
    BufferedImage buffImg = new BufferedImage(image.getWidth(),
            image.getHeight(),
            BufferedImage.TYPE_INT_ARGB);
    
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        buffImg.setRGB(i, j, image.getPixelAt(i, j).getRGBByte());
      }
      this.image.setIcon(new ImageIcon(buffImg));
      this.image.setBorder(BorderFactory.createLineBorder(Color.black, 3));
      this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }
  }
}
