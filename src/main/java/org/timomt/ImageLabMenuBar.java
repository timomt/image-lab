package org.timomt;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ImageLabMenuBar:
 * the main JMenuBar of the application
 */
public class ImageLabMenuBar extends JMenuBar {
    /**
     * ImageLabMenuBar:
     * constructor to initialize ImageLabMenuBar.
     * @param gui the parent container.
     */
    public ImageLabMenuBar(ImageLabGUI gui) {
        this.setLayout(new BorderLayout());

        JMenu fileMenu = new JMenu("Image");

        JMenuItem loadImage = new JMenuItem("Load Image ...");
        loadImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            int retVal = fileChooser.showOpenDialog(loadImage);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                try {
                    BufferedImage image = ImageIO.read(f);
                    gui.setImage(image);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JMenuItem saveImage = new JMenuItem("Save Image ...");
        saveImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int retVal = fileChooser.showSaveDialog(saveImage);
            fileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().endsWith(".jpg");
                }

                @Override
                public String getDescription() {
                    return "JPG files (*.jpg)";
                }
            });
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                if (!f.exists()) {
                    try {
                        f.createNewFile();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (!gui.saveImage(f)) {
                    ImageLabGUI.simpleMessageDialog("Image Lab: Save Image",
                            String.format("Could not save Image to file \"%s\"", f.getName()), "Okay");
                } else {
                    ImageLabGUI.simpleMessageDialog("Image Lab: Save Image",
                            String.format("Successfully saved Image to file \"%s\"", f.getName()), "Great!");
                }
            }
        });
        fileMenu.add(loadImage);
        fileMenu.add(saveImage);
        this.add(fileMenu);
    }
}