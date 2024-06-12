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

public class ImageLabGUI extends JFrame {
    private BufferedImage image = null;
    private final JLabel imageLabel = new JLabel();

    public ImageLabGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new MigLayout("fill"));

        mainPanel.add(new ImageLabMenuBar(this), "north");
        mainPanel.add(imageLabel, "center wrap");

        mainPanel.add(new ActionPanel(this), "south");

        this.add(mainPanel);
        this.setTitle("Image Lab");
        this.setPreferredSize(new Dimension(900, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        updateImage();
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public boolean saveImage(File f) {
        if (!f.exists()) return false;
        try {
            ImageIO.write(this.image, "PNG" , f);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ImageLabGUI.class.getName()).log(Level.INFO, null, ex);
        }
        return false;
    }
    private void updateImage() {
        this.imageLabel.setIcon(new ImageIcon(this.image));
    }

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