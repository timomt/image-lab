package org.timomt;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLabMenuBar extends JMenuBar {
    public ImageLabMenuBar(ImageLabGUI gui) {
        this.setLayout(new BorderLayout());

        JMenu fileMenu = new JMenu("Image");

        JMenuItem loadImage = new JMenuItem("Load Image ...");  // TODO: not supporting png, only jpg
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
                    return f.isDirectory() || f.getName().endsWith(".png");
                }

                @Override
                public String getDescription() {
                    return "PNG files (*.png)";
                }
            });
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
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
