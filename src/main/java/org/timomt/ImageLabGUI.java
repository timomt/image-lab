package org.timomt;

import net.miginfocom.swing.MigLayout;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ImageLabGui:
 * JFrame this application is built on.
 */
public class ImageLabGUI extends JFrame {
    /**
     * image:
     * the image to be displayed and altered.
     */
    private BufferedImage image = null;

    /**
     * imageLabel:
     * the container of this.image.
     */
    private final JLabel imageLabel = new JLabel();

    /**
     * ImageLabGUI:
     * default constructor.
     */
    public ImageLabGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new MigLayout("fill"));

        mainPanel.add(new ImageLabMenuBar(this), "north");
        mainPanel.add(imageLabel, "center wrap");

        mainPanel.add(new ActionPanel(this), "south");

        this.add(mainPanel);
        this.setTitle("Image Lab");
        this.setPreferredSize(new Dimension(900, 900));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    /**
     * setImage:
     * Replaces the current image and updates the UI.
     * @param image the new image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        updateImage();
    }

    /**
     * getImage
     * @return this.image - the current image.
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * saveImage
     * @param f the file to be saved to
     * @return true if the process was successful (currently only in JPG format).
     */
    public boolean saveImage(File f) {
        if (!f.exists()) return false;
        try {
            ImageIO.write(this.image, "jpg" , f);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ImageLabGUI.class.getName()).log(Level.INFO, null, ex);
        }
        return false;
    }

    /**
     * updateImage:
     * updates the display image.
     */
    private void updateImage() {
        this.imageLabel.setIcon(new ImageIcon(this.image));
    }

    /**
     * simpleMessageDialog
     * a JDialog preset for consistent message dialogs.
     * @param title the title of the window.
     * @param message the message to be displayed.
     * @param buttonText the text of the closing button.
     */
    public static void simpleMessageDialog(String title, String message, String buttonText) {
        JDialog jDialog = new JDialog();
        jDialog.setTitle(title);
        jDialog.setSize(new Dimension(400, 130));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel label = new JLabel(message);
        label.setBorder(new EmptyBorder(10, 0, 20, 0));
        label.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label);

        JButton okButton = new JButton(buttonText);
        okButton.addActionListener(x -> jDialog.dispose());
        okButton.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(okButton);

        jDialog.add(panel);
        jDialog.setVisible(true);
    }
}