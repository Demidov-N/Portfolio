package view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import controller.features.ControllerFeatures;

class ToolbarPanel extends JPanel implements ActionListener {
  private final Map<String, JPanel> panelMap;
  private final ImageProcessorGUIView main;
  private final JPanel transformationPanel;
  ControllerFeatures features;
  private final JComboBox<String> imagesSelect;
  private final JTextField newImageName;
  
  ToolbarPanel(ImageProcessorGUIView main) {
    
    super();
    this.main = main;
    // Setting up layout and the preferred size
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setPreferredSize(new Dimension(150, 600));
    
    // Add ImageSelection panel
    JPanel imTitle = new JPanel();
    imTitle.setMaximumSize(new Dimension(1000, 20));
    imTitle.add(new JLabel("Select Image"));
    this.imagesSelect = new JComboBox<>();
    this.imagesSelect.setMaximumSize(new Dimension(1000, 20));
    this.imagesSelect.setActionCommand("imageSelector");
    this.imagesSelect.addActionListener(this);
    
    
    // Add panel map to have references to this transformations
    this.panelMap = new HashMap<>();
    
    // Add transformation panel
    this.transformationPanel = new JPanel(new CardLayout());
    this.transformationPanel.setMaximumSize(new Dimension(1000, 200));
    
    // Adding new image name panel
    this.newImageName = this.makeNewNameField();
    
    
    // Add brighten panel
    this.addTransformation("Brighten");
    
    // Add blur panel
    this.addTransformation("Blur");
    
    // Add flip panel
    this.addTransformation("Flip");
    
    // Add greyscale panel
    this.addTransformation("Greyscale");
    
    // Add Greyscale panel
    this.addTransformation("Intensity");
    
    // Add Intensity panel
    this.addTransformation("Luma");
    
    // Add Sepia panel
    this.addTransformation("Sepia");
    
    // Add Sharpen panel
    this.addTransformation("Sharpen");
    
    // Add Value panel
    this.addTransformation("Value");
    
    // Add Component panel
    this.addTransformation("Component");
    
    
    String [] transformations = {"Brighten", "Blur", "Flip",
                                 "Greyscale", "Intensity", "Luma",
                                 "Sepia", "Sharpen", "Value", "Component"};
    
    // Adding Transformation selector
    JComboBox<String> box = new JComboBox<>(transformations);
    box.setActionCommand("selectTransformation");
    box.setSelectedItem("Brighten");
    box.addActionListener(this); // adding action listener for view-specific events
    box.setMaximumSize(new Dimension(1000, 20));
    
    // Add Transformation Title panel
    JPanel tTitle = new JPanel();
    tTitle.setMaximumSize(new Dimension(1000, 25));
    tTitle.add(new JLabel("Transformations", JLabel.LEFT));
    
    // Appending all the elements together
    add(imTitle);
    add(this.imagesSelect);
    add(tTitle);
    add(makeImageTitle());
    add(this.newImageName);
    add(box);
    add(this.transformationPanel);
    
    this.addTransformationPanels();
  }
  
  // Adds transformation with title and the border to the panel map.
  // By default, it is not visible
  private void addTransformation(String title) {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder(title));
    this.transformationPanel.add(panel, title);
    this.panelMap.put(title, panel);
  }
  
  // To handle VIEW SPECIFIC EVENTS, like panel changes of the transformations, or changing images
  // We have just selectors in this container
  @Override
  public void actionPerformed(ActionEvent event) {
    String eventName = event.getActionCommand();
    JComboBox<String> selector;
    String itemName;
    selector = (JComboBox<String>) event.getSource();
    if (eventName.equals("imageSelector")) {
      itemName = (String) selector.getSelectedItem();
      this.main.showImage(itemName);
      this.newImageName.setText(itemName);
    } else {
      itemName = (String) selector.getSelectedItem();
      CardLayout cl = (CardLayout) this.transformationPanel.getLayout();
      cl.show(this.transformationPanel, itemName);
    }
  }
  
  String getWorkingImage() {
    return (String) this.imagesSelect.getSelectedItem();
  }
  
  private void addTransformationPanels() {
    
    
    // Add all the possible transformations
    this.addBrightenPanel();
    this.makeSingleButton(
            this.panelMap.get("Blur"), (oldImage, newImage) ->
                    this.features.blur(oldImage, newImage));
    this.makeSingleButton(
            this.panelMap.get("Greyscale"), (oldImage, newImage) ->
                    this.features.greyscale(oldImage, newImage));
    this.makeSingleButton(
            this.panelMap.get("Intensity"), (oldImage, newImage) ->
                    this.features.intensity(oldImage, newImage));
    this.makeSingleButton(
            this.panelMap.get("Sepia"), (oldImage, newImage) ->
                    this.features.sepia(oldImage, newImage));
    this.makeSingleButton(
            this.panelMap.get("Sharpen"), (oldImage, newImage) ->
                    this.features.sharpen(oldImage, newImage));
    this.makeSingleButton(
            this.panelMap.get("Value"), (oldImage, newImage) ->
                    this.features.value(oldImage, newImage));
    this.makeSingleButton(
            this.panelMap.get("Luma"), (oldImage, newImage) ->
                    this.features.luma(oldImage, newImage));
    
    this.makeFlipPanel();
    this.makeComponentPanel();
  }
  
  void addFeature(ControllerFeatures features) {
    this.features = features;
  }
  
  // Make default grid bag constraint with every element size of 2
  private static GridBagConstraints makeDefaultGridConstraint() {
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.5;
    c.gridwidth = 2;
    c.insets = new Insets(2, 2, 2, 2);
    return c;
  }
  
  // Make a grid with a single button on the specified panel
  private void makeSingleButton(JPanel panel,
                                BiConsumer<String, String> action) {
    panel.setLayout(new GridBagLayout());
    GridBagConstraints c = makeDefaultGridConstraint();
    
    c.gridy++;
    JButton button = new JButton("Go");
    panel.add(button, c);
    
    button.addActionListener(actionEvent ->
            action.accept(this.getWorkingImage(), this.newImageName.getText()));
    
  }
  
  private JTextField makeNewNameField() {
    JTextField field = new JTextField();
    field.setMaximumSize(new Dimension(1000, 20));
    field.setActionCommand("nameChange");
    field.addActionListener(this);
    return field;
  }
  
  private static JPanel makeImageTitle() {
    JPanel titlePanel = new JPanel();
    titlePanel.add(new JLabel("<html>New Image Name</html>"));
    titlePanel.setMaximumSize(new Dimension(1000, 20));
    return titlePanel;
  }
  
  // Adds item to the image lists if not exist
  void addImageToList(String image) {
    boolean exist = false;
    for (int index = 0; index < this.imagesSelect.getItemCount() && !exist; index++) {
      if (image.equals(this.imagesSelect.getItemAt(index))) {
        exist = true;
      }
    }
    if (!exist) {
      this.imagesSelect.addItem(image);
    }
    this.imagesSelect.setSelectedItem(image);
  }
  
  // Make brighten panel
  private void addBrightenPanel() {
    // Make constraint for the GridBagLayout
    GridBagConstraints c = makeDefaultGridConstraint();
    
    
    // Adding brighten commands
    JPanel panel = this.panelMap.get("Brighten");
    panel.setLayout(new GridBagLayout());
    
    JButton brightenButton = new JButton("Go");
    
    c.gridwidth = 1;
    panel.add(new JLabel("Value"), c);
    
    // Add brighten text counter (1, 2)
    c.gridx++;
    JTextField brightenValue =
            new JTextField("10");
    panel.add(brightenValue, c);
    
    // Add go button, (0, 3)
    c.gridy++;
    c.gridx = 0;
    c.gridwidth++;
    panel.add(brightenButton, c);
    
    // Add action to the button
    brightenButton.addActionListener(actionEvent -> {
      try {
        int val = Integer.parseInt(brightenValue.getText());
        this.features.brighten(this.getWorkingImage(), this.newImageName.getText(), val);
      } catch (NumberFormatException e) {
        this.main.addError("Invalid brighten value, check if it is a number");
      }
    });
  }
  
  // Panel with two flip buttons
  private void makeFlipPanel() {
    GridBagConstraints c = makeDefaultGridConstraint();
    
    JPanel panel = this.panelMap.get("Flip");
    panel.setLayout(new GridBagLayout());
    
    panel.add(makeImageTitle(), c);
    
    
    c.gridy++;
    JButton vert = new JButton("Vertical");
    JButton hor = new JButton("Horizontal");
    panel.add(vert, c);
    c.gridy++;
    panel.add(hor, c);
    
    vert.addActionListener(actionEvent ->
            this.features.flip(
                    this.getWorkingImage(),
                    this.newImageName.getText(),
                    "vertical"));
    hor.addActionListener(actionEvent ->
            this.features.flip(
                    this.getWorkingImage(),
                    this.newImageName.getText(),
                    "horizontal"));
    
    
  }
  
  // Panel with component selector
  private void makeComponentPanel() {
    GridBagConstraints c = makeDefaultGridConstraint();
    
    
    // Adding brighten commands
    JPanel panel = this.panelMap.get("Component");
    panel.setLayout(new GridBagLayout());
    
    JComboBox<String> channel =
            new JComboBox<>(new String[]{"red", "blue", "green"});
    
    c.gridwidth = 1;
    panel.add(new JLabel("Channel"), c);
    
    c.gridx++;
    panel.add(channel, c);
    
    c.gridy++;
    c.gridx = 0;
    c.gridwidth = 2;
    c.insets = new Insets(0, 0, 0, 0);
    makeSingleButton(panel, (oldImage, newImage) ->
            this.features.component(oldImage, newImage,
                    (String) channel.getSelectedItem())
    );
  }
  
  String getNewTitleImage() {
    return this.newImageName.getText();
  }
}
