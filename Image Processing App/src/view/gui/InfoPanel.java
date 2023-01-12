package view.gui;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.Color;

import model.image.ViewImage;

class InfoPanel extends JPanel {
  
  private final HistogramPanel histBlue;
  private final HistogramPanel histRed;
  private final HistogramPanel histGreen;
  private final HistogramPanel histIntensity;
  // private final HistogramPanel histComponent;
  private ImageProcessorGUIView main;
  
  InfoPanel(ImageProcessorGUIView main) {
    super();
    setPreferredSize(new Dimension(300, 450));
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.histBlue = new HistogramPanel(300, 150, "blue", Color.blue);
    this.histRed = new HistogramPanel(300, 150, "red", Color.red);
    this.histGreen = new HistogramPanel(300, 150, "green", Color.green);
    this.histIntensity = new HistogramPanel(300, 150, "intensity",
            Color.darkGray);
    
    add(this.histBlue);
    add(this.histRed);
    add(this.histGreen);
    add(this.histIntensity);
  }
  
  void updateHistogram(ViewImage image) {
    this.histBlue.paintImage(image);
    this.histRed.paintImage(image);
    this.histGreen.paintImage(image);
    this.histIntensity.paintImage(image);
  }
  
  
}
