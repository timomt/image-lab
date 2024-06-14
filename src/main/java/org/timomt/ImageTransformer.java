package org.timomt;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public final class ImageTransformer {
    private ImageTransformer() {}

    public enum MATRIX_TARGETS  {
            RGB, XY
    };

    public static BufferedImage transform(BufferedImage image, double[][] matrix, MATRIX_TARGETS target) {
        WritableRaster imageRaster = image.getRaster();
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        WritableRaster newImageRaster = newImage.getRaster();

        if (target == MATRIX_TARGETS.RGB) {
            if (imageRaster.getPixel(0, 0, (int[]) null).length != matrix.length) {
                ImageLabGUI.simpleMessageDialog("Image Lab: Apply Matrix",
                        "Could not transform image due to color spectrum not represented as 3x1 matrix",
                        "Okay");
                return image;
            }
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int[] rgb = imageRaster.getPixel(x, y, (int[]) null);
                    int[] transformedRGB = new int[rgb.length];

                    for (int i = 0; i < transformedRGB.length; i++) {
                        transformedRGB[i] = (int) (
                                matrix[i][0] * rgb[0] + matrix[i][1] * rgb[1] + matrix[i][2] * rgb[2]
                        );
                        if (transformedRGB[i] > 255) transformedRGB[i] = 255;
                        if (transformedRGB[i] < 0) transformedRGB[i] = 0;
                    }
                    newImageRaster.setPixel(x, y, transformedRGB);
                }
            }
            newImage.setData(newImageRaster);
            return newImage;
        } else if (target == MATRIX_TARGETS.XY) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int[] rgb = imageRaster.getPixel(x, y, (int[]) null);

                    /* Apply center-offset, so the Image gets manipulated relative to the center */
                    int shiftedXCoord = x - (image.getWidth() / 2);
                    int shiftedYCoord = y - (image.getHeight() / 2);

                    /* Invert y-Coordinate because BufferedImage uses inverted y-Axis */
                    shiftedYCoord = -shiftedYCoord;

                    /* Apply actual matrix (and potential affine vector) */
                    int transformedXCoord = (int) Math.round(matrix[0][0] * shiftedXCoord
                            + matrix[0][1] * shiftedYCoord + matrix[0][2]);
                    int transformedYCoord = (int) Math.round(matrix[1][0] * shiftedXCoord
                            + matrix[1][1] * shiftedYCoord + matrix[1][2]);

                    /* Invert y-Coordinate again because to apply center-offset */
                    transformedYCoord = -transformedYCoord;
                    transformedXCoord += (image.getWidth() / 2);
                    transformedYCoord += (image.getHeight() / 2);

                    /* Check if vector is inside of image bounds */
                    if (transformedXCoord >= 0 && transformedXCoord < image.getWidth()
                            && transformedYCoord >= 0 && transformedYCoord < image.getHeight()) {
                        newImageRaster.setPixel(transformedXCoord, transformedYCoord, rgb);
                    }
                }
            }
            newImage.setData(newImageRaster);
            return newImage;
        }
        return null;
    }

    public static double evaluate(String str) {
        Expression expr = new ExpressionBuilder(str)
                .variables("pi", "e")
                .build().setVariable("pi", Math.PI)
                .setVariable("e", Math.E);
        return expr.evaluate();
    }
}