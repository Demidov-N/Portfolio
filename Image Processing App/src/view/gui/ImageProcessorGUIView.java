package view.gui;

import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JOptionPane;


import controller.features.ControllerFeatures;
import model.ImageProcessorView;
import model.image.ViewImage;


/**
 * This class represents a GUI with 4 different panels:
 * - An image panel, where the working image is shown at.
 * - A toolbar panel, where the transformation tools are presented.
 * - An info panel, shows the histogram of images.
 * - A header, has options to load an image, save it under another name, and see the help.
 */
public class ImageProcessorGUIView extends JFrame implements ImageGUI {
  
  private final ImageProcessorView modelView;
  
  private final ImagePanel imagePanel; // Panel with images
  private final HeaderPanel headerPanel; // panel with header options
  private final ToolbarPanel toolPanel; // panel with image transformation tools
  private final InfoPanel infoPanel; // panel with info about an image
  private final JPanel main; // a main panel to work on
  
  /**
   * Creates a new view based on the model view presented.
   *
   * @param modelView The model view of what is going to be shown and interacted with
   */
  public ImageProcessorGUIView(ImageProcessorView modelView) {
    super();
    Objects.requireNonNull(modelView);
    this.modelView = modelView;
    setTitle("Ultimate Image Processor");
    setSize(400, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    // Creating all the panels, just the placehorlders right now.
    imagePanel = new ImagePanel(this);
    toolPanel = new ToolbarPanel(this);
    headerPanel = new HeaderPanel(this);
    infoPanel = new InfoPanel(this);
    
    main = new JPanel();
    main.setLayout(new BorderLayout());
    
    
    main.add(new JScrollPane(imagePanel));
    main.add(headerPanel, "North");
    main.add(infoPanel, "East");
    main.add(toolPanel, "West");
    add(main);
    setMinimumSize(new Dimension(800, 650));
    
    
  }
  
  @Override
  public void addFeature(ControllerFeatures feature) {
    this.toolPanel.addFeature(feature);
    this.headerPanel.addFeature(feature);
  }
  
  @Override
  public void addImage(String image) throws IllegalArgumentException {
    
    this.addImageToList(image);
    this.showImage(image);
    
  }
  
  
  @Override
  public void addError(String error) {
    JOptionPane.showMessageDialog(
            this.main, error, "ERROR!!", JOptionPane.ERROR_MESSAGE);
  }
  
  
  // Below are the methods that are used across the panel classes
  
  // InfoPanel
  
  /**
   * Updates histograms based on the new image.
   *
   * @param image based on which the histograms are going to be created on
   */
  void updateHistogram(ViewImage image) {
    this.infoPanel.updateHistogram(image);
  }
  
  
  // ImagePanel
  
  /**
   * Shows an image with the specified name.
   *
   * @param imageNow a name of an image
   */
  void showImage(String imageNow) {
    if (!imageNow.equals("")) {
      ViewImage image = this.modelView.getImage(imageNow);
      this.imagePanel.showImage(image);
      this.updateHistogram(image);
    }
  }
  
  // ToolbarPanel
  
  /**
   * Gets the name of the image shown right now.
   *
   * @return the image name from the controller.
   */
  String getWorkingImage() {
    return this.toolPanel.getWorkingImage();
  }
  
  /**
   * Adds an image name into the list.
   *
   * @param image the image name to be added.
   */
  void addImageToList(String image) {
    this.toolPanel.addImageToList(image);
  }
  
  /**
   * Gets the new title from the toolbar for the program to save the image to.
   *
   * @return the title.
   */
  String getNewTitleImage() {
    return this.toolPanel.getNewTitleImage();
  }
}