package org.timomt;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ActionPanel extends JPanel {

    private static final String[] matrixOperations = {
            "empty", "mirrorX", "mirrorY", "rotate", "rotateX", "rotateY", "rotateZ"
    };

    private final JTextField[][] matrixFields = {
        { new JTextField("0.393"), new JTextField("0.769"), new JTextField("0.189") },
        { new JTextField("0.347"), new JTextField("0.686"), new JTextField("0.168") },
        { new JTextField("0.272"), new JTextField("0.534"), new JTextField("0.131") }
    };

    private final JComboBox<ImageTransformer.MATRIX_TARGETS> matrixTargetComboBox =
            new JComboBox<>(ImageTransformer.MATRIX_TARGETS.values());

    public ActionPanel(ImageLabGUI gui) {
        this.setLayout(new GridLayout(1, 3, 10, 10));
        this.setBorder(new MatteBorder(new Insets(2, 2, 2, 2), Color.DARK_GRAY));

        JPanel leftPanel = new JPanel(new GridLayout(2, 2));
        JLabel matrixTargetJLabel = new JLabel("Target property:");
        leftPanel.add(matrixTargetJLabel);

        leftPanel.add(matrixTargetComboBox);
        JLabel operationJLabel = new JLabel("Matrix preset: ");
        leftPanel.add(operationJLabel);
        JComboBox<String> operationComboBox = new JComboBox<>(matrixOperations);
        leftPanel.add(operationComboBox);

        JPanel rightPanel = new JPanel(new GridLayout(3, 4));
        rightPanel.add(new JLabel("X"));
        rightPanel.add(this.matrixFields[0][0]);
        rightPanel.add(this.matrixFields[0][1]);
        rightPanel.add(this.matrixFields[0][2]);
        rightPanel.add(new JLabel("Y"));
        rightPanel.add(this.matrixFields[1][0]);
        rightPanel.add(this.matrixFields[1][1]);
        rightPanel.add(this.matrixFields[1][2]);
        rightPanel.add(new JLabel("Z"));
        rightPanel.add(this.matrixFields[2][0]);
        rightPanel.add(this.matrixFields[2][1]);
        rightPanel.add(this.matrixFields[2][2]);

        JButton applyButton = new JButton("Apply Matrix");
        applyButton.addActionListener(e -> applyMatrix(gui));

        this.add(leftPanel);
        this.add(rightPanel);
        this.add(applyButton);
    }

    private void applyMatrix(ImageLabGUI gui) {
        BufferedImage image = gui.getImage();
        if (image == null) {
            ImageLabGUI.simpleMessageDialog("Image Lab: Apply Matrix", "You first need to load an image to apply operations to", "Okay");
        } else {
            double[][] matrix = new double[3][3];
            for (int n = 0; n < 3; n++) {
                for (int m = 0; m < 3; m++) {
                    matrix[n][m] = ImageTransformer.evaluate(this.matrixFields[n][m].getText());
                }
            }
            gui.setImage(ImageTransformer.transform(gui.getImage(), matrix, (ImageTransformer.MATRIX_TARGETS) Objects.requireNonNull(matrixTargetComboBox.getSelectedItem())));
        }
    }
}