
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Convolution {
    public static BufferedImage convolve(BufferedImage inputImage, double[][] filter) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage;

        outputImage = new BufferedImage(width, height, inputImage.getType());

        for (int y = 0; y < height; y++) // Loop over the 2D image pixel-by-pixel
        {
            for (int x = 0; x < width; x++) {
                float accumR = 0f, accumG = 0f, accumB = 0f;

                for (int row = 0; row < filter.length; row++) {
                    for (int col = 0; col < filter[row].length; col++) {
                        int imageX = (x + col);
                        int imageY = (y + row);
                        try {
                            // This will cause an exception if we overrun the edges of the image
                            int element = inputImage.getRGB(imageX, imageY);

                            int elementR = (element >> 16) & 0xff;
                            int elementG = (element >> 8) & 0xff;
                            int elementB = element & 0xff;

                            accumR += (elementR) * filter[row][col];
                            accumG += (elementG) * filter[row][col];
                            accumB += (elementB) * filter[row][col];
                        } catch (Exception e) {
                            continue; // Ignore any exception and keep going. It’s good enough
                        }
                    }
                }

                int outR = Math.min(Math.max((int) (accumR), 0), 255);
                int outG = Math.min(Math.max((int) (accumG), 0), 255);
                int outB = Math.min(Math.max((int) (accumB), 0), 255);

                outputImage.setRGB(x, y, new Color(outR, outG, outB).getRGB());
            }
        }
        return outputImage;
    }

    public static double[][] selectFilter(int filterChoice) {

        double[][] filter = null;

        switch (filterChoice) {
            case 1:
                filter = Kernel.IDENTITY;
                break;
            case 2:
                filter = Kernel.EDGE_DETECTION_1;
                break;
            case 3:
                filter = Kernel.EDGE_DETECTION_2;
                break;
            case 4:
                filter = Kernel.LAPLACIAN;
                break;
            case 5:
                filter = Kernel.SHARPEN;
                break;
            case 6:
                filter = Kernel.VERTICAL_LINES;
                break;
            case 7:
                filter = Kernel.HORIZONTAL_LINES;
                break;
            case 8:
                filter = Kernel.DIAGONAL_45_LINES;
                break;
            case 9:
                filter = Kernel.BOX_BLUR;
                break;
            case 10:
                filter = Kernel.SOBEL_HORIZONTAL;
                break;
            case 11:
                filter = Kernel.SOBEL_VERTICAL;
                break;
            default:
                throw new IndexOutOfBoundsException();
        }
        return filter;
    }
}