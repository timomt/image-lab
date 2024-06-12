package org.timomt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    private final JComboBox<String> operationComboBox = new JComboBox<>(matrixOperations);

    public ActionPanel(ImageLabGUI gui) {
        this.setLayout(new GridLayout(1, 3, 10, 10));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new GridLayout(2, 2));
        JLabel matrixTargetJLabel = new JLabel("Target property:");
        matrixTargetJLabel.setHorizontalAlignment(JLabel.CENTER);

        this.matrixTargetComboBox.addActionListener(e -> {
            if (matrixTargetComboBox.getSelectedItem() == ImageTransformer.MATRIX_TARGETS.XY) {
                matrixFields[2][0].setText("-");
                matrixFields[2][1].setText("-");
                matrixFields[2][2].setText("-");
                matrixFields[2][0].setEditable(false);
                matrixFields[2][1].setEditable(false);
                matrixFields[2][2].setEditable(false);
                matrixFields[0][2].setText("0");
                matrixFields[1][2].setText("0");
                matrixFields[0][2].setEditable(false);
                matrixFields[1][2].setEditable(false);
                operationComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"linear", "affine"}));
            } else {
                matrixFields[2][0].setText("0");
                matrixFields[2][1].setText("0");
                matrixFields[2][2].setText("0");
                matrixFields[2][0].setEditable(true);
                matrixFields[2][1].setEditable(true);
                matrixFields[2][2].setEditable(true);
                matrixFields[0][2].setEditable(true);
                matrixFields[1][2].setEditable(true);
                operationComboBox.setModel(new DefaultComboBoxModel<>(matrixOperations));
            }
        });

        leftPanel.add(matrixTargetJLabel);
        leftPanel.add(this.matrixTargetComboBox);

        JLabel operationJLabel = new JLabel("Matrix preset: ");
        operationJLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(operationJLabel);
        leftPanel.add(operationComboBox);

        this.operationComboBox.addActionListener(e -> {
            switch ((String)this.operationComboBox.getSelectedItem()) {
                case "linear":
                    matrixFields[0][2].setText("0");
                    matrixFields[1][2].setText("0");
                    matrixFields[0][2].setEditable(false);
                    matrixFields[1][2].setEditable(false);
                    break;
                case "affine":
                    matrixFields[0][2].setEditable(true);
                    matrixFields[1][2].setEditable(true);
                    break;
                case null:
                default:
                    break;
            }
        });

        JPanel rightPanel = new JPanel(new GridLayout(3, 4));
        rightPanel.add(new JLabel("R / X", JLabel.CENTER));
        rightPanel.add(this.matrixFields[0][0]);
        rightPanel.add(this.matrixFields[0][1]);
        rightPanel.add(this.matrixFields[0][2]);
        rightPanel.add(new JLabel("G / Y", JLabel.CENTER));
        rightPanel.add(this.matrixFields[1][0]);
        rightPanel.add(this.matrixFields[1][1]);
        rightPanel.add(this.matrixFields[1][2]);
        rightPanel.add(new JLabel("B / -", JLabel.CENTER));
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
            BufferedImage newImage = ImageTransformer.transform(gui.getImage(), matrix, (ImageTransformer.MATRIX_TARGETS) Objects.requireNonNull(matrixTargetComboBox.getSelectedItem()));
            if (newImage == null) {
                //TODO implement
                System.out.println("error");
            } else {
                gui.setImage(newImage);
            }
        }
    }
}