package org.timomt;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public final class ImageTransformer {
    private ImageTransformer() {}

    public enum MATRIX_TARGETS  {
            RGB, XYZ
    };

    public static BufferedImage transform(BufferedImage image, double[][] matrix, MATRIX_TARGETS target) {
        WritableRaster imageRaster = image.getRaster();
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        WritableRaster newImageRaster = newImage.getRaster();

        if (target == MATRIX_TARGETS.RGB) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int[] rgb = imageRaster.getPixel(x, y, (int[]) null);
                    int[] transformedRGB = new int[3];

                    for (int i = 0; i < 3; i++) {
                        transformedRGB[i] = (int) (
                                matrix[i][0] * rgb[0] +
                                        matrix[i][1] * rgb[1] +
                                        matrix[i][2] * rgb[2]
                        );
                        if (transformedRGB[i] > 255) transformedRGB[i] = 255;
                        if (transformedRGB[i] < 0) transformedRGB[i] = 0;
                    }
                    newImageRaster.setPixel(x, y, transformedRGB);
                }
            }
            newImage.setData(newImageRaster);
            return newImage;
        }
        return null;
    }

    public static double evaluate(String str) {
        return Double.parseDouble(str); // TODO: implement parser
    }
}