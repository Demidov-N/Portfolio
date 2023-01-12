package view.gui;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import model.Histogram;
import model.image.ViewImage;

/**
 * A single histogram panel with the specified channel name.
 */
class HistogramPanel extends JPanel {
  private final int height; // height of the panel
  
  // Helper fields
  private final int marginX; // Margin from the left
  private final int marginY; // Margin from the right
  private final int cWidth; // Width of the histogram area
  private final int cHeight; // Height of the histogram area
  
  private final String channel;
  private final Color color;
  private ViewImage image;
  
  HistogramPanel(int width, int height, String channel, Color color) {
    super();
    setSize(width, height);
    // General use fields
    // width of the panel
    this.height = height;
    
    this.color = color;
    this.channel = channel;
    
    this.marginX = 20;
    this.marginY = 20;
    
    this.cWidth = width - 2 * this.marginX;
    this.cHeight = height - 2 * this.marginY;
    
  }
  
  // Makes an image histogram
  void paintImage(ViewImage image) {
    // The image to work on
    this.image = image;
    this.repaint();
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    if (this.image != null) {
      this.makeAxes(g);
      this.makeAxesTitles(g);
      Histogram hist = new Histogram(this.image);
      
      this.paintChannel(g, hist, this.channel, this.color);
      
      //this.paintChannel(g, hist, "blue", Color.blue);
    }
  }
  
  // Gets an absolute graphic coordinate based on the
  // container coordinate
  private int yScale(int y) {
    return this.height - (y) - this.marginY;
    //
  }
  
  // Gets an absolute graphic coordinate based on the
  // container coordinate
  private int xScale(int x) {
    return x + this.marginX;
    //* this.cWidth/this.maxX
  }
  
  private void makeAxes(Graphics g) {
    // Vertical
    g.drawLine(xScale(0), yScale(0),
            xScale(0), yScale(this.cHeight));
    
    
    // Horizontal
    g.drawLine(xScale(0), yScale(0),
            xScale(this.cWidth), yScale(0));
  }
  
  
  private void makeAxesTitles(Graphics g) {
    String x = "Channel Values";
    g.drawChars(x.toCharArray(), 0, x.length(),
            xScale(this.cWidth / 2 - 50), yScale(-this.marginY / 2 - 2));
    
    String tt = "Channel: " + this.channel;
    g.drawChars(tt.toCharArray(), 0, tt.length(),
            xScale(this.cWidth / 2 - 50), this.marginY / 2);
    
  }
  
  // make a linechart of a specific
  private void paintChannel(
          Graphics g, Histogram hist,
          String channel, Color channelColor) {
    
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(channelColor);
    for (int i = 0; i <= hist.getSize() - 1; i++) {
      Shape line = new Line2D.Double(xScale(i), yScale(hist.getValue(i, channel)),
              xScale(i + 1), yScale(hist.getValue(i + 1, channel)));
      g2.draw(line);
    }
    g2.setColor(Color.black);
  }
}
