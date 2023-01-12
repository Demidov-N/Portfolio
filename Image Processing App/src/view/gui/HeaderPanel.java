package view.gui;

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ImageReader;
import controller.features.ControllerFeatures;

/**
 * A header panel, with load, save and help.
 */
class HeaderPanel extends JPanel implements ActionListener {
  private ControllerFeatures features;
  private final JFileChooser fileChooser;
  private final ImageProcessorGUIView main;
  
  /**
   * Creates a header panel with all the components.
   *
   * @param main the main gui class
   */
  HeaderPanel(ImageProcessorGUIView main) {
    super(new FlowLayout(FlowLayout.LEFT));
    setPreferredSize(new Dimension(800, 30));
    this.main = main;
    
    this.fileChooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images: *.jpg, *.gif, *.jpeg, *.png, *.bmp, *.ppm, *.gif",
            "jpg", "gif", "jpeg", "png", "bmp", "ppm", "gif");
    this.fileChooser.setFileFilter(filter);
  
  
    JButton save = new JButton("Save");
    
    save.setActionCommand("save");
    save.addActionListener(this);
  
    JButton load = new JButton("Load");
    load.addActionListener(this);
    
    load.setActionCommand("load");
    this.add(save);
    this.add(load);
  
    JButton help = new JButton("Help");
    this.add(help);
    help.setActionCommand("help");
    help.addActionListener(this);
    
  }
  
  void addFeature(ControllerFeatures features) {
    this.features = features;
  }
  
  
  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    File f;
    switch (command) {
      case "save": // when button save is pressed, save it under the specified name
        String name = this.main.getNewTitleImage();
        String newName = "";
        if (!name.equals("")) {
          newName = name;
          if (ImageReader.getExtension(name).equals("")) {
            newName += ".jpg";
          }
        }
        this.fileChooser.setSelectedFile(new File(newName));
        
        f = this.savePathOrNull();
        if (f != null) {
          this.features.save(f.getPath(), this.main.getWorkingImage());
        }
        break;
      case "load": // when button load is pressed, save it under specified name with no extension
        f = this.openPathOrNull();
        if (f != null) {
          this.fileChooser.setCurrentDirectory(f);
          
          int dPos = f.getName().lastIndexOf(".");
          if (dPos > 0) {
            this.features.load(f.getPath(), f.getName().substring(0, dPos));
          } else {
            this.main.addError("ERROR: Unsupported name of the file");
          }
        }
        break;
      case "help": // when help is pressed, show instructions
        JOptionPane.showMessageDialog(this.main,
                "This is an Image Processing program. \n\n"
                        + "It is capable of loading an image, operating numerous transformations\n"
                        + "on it, and saving the image after being transformed. \n\n"
                        + "To load an image, click on the load button above and pick an image to\n"
                        + "load into the program (.jpg, .ppm, .gif and .png are supported).\n"
                        + " Once the image is loaded, it will show up on the center. \n"
                        + "You may load multiple images at once.\n\n"
                        + " Chose the image you would like to transform, "
                        + "give it a new name in the\n"
                        + "\"Transformation Name\" text field, then pick the type "
                        + "of transformation\n"
                        + " you would like to do from the transformations drop down.\n"
                        + "If there are extra parameters, you must fill them, then press go and \n"
                        + "the transformation will be applied. \n"
                        + "\n\nIf you would like to save the \n"
                        + "image, click the save button above, type a name for the image, \n"
                        + "pick a destination and then click save\n", "How to Use",
                JOptionPane.INFORMATION_MESSAGE);
        break;
      default:
        throw new IllegalArgumentException("Unknown action");
    }
  }
  
  // Opens the file folder for load and returns the path, null if it was closed.
  private File openPathOrNull() {
    int retvalue = this.fileChooser.showOpenDialog(this);
    
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      return this.fileChooser.getSelectedFile();
    } else {
      return null;
    }
  }
  
  // Opens the file folder for load and returns the path, null if it was closed.
  private File savePathOrNull() {
    int retvalue = this.fileChooser.showSaveDialog(this);
    
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      return this.fileChooser.getSelectedFile();
    } else {
      return null;
    }
  }
}
