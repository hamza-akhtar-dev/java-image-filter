//Importing the required classes

import java.util.Scanner;
import java.io.File;
import javax.imageio.ImageIO;
import java.lang.reflect.Field;
import java.awt.image.BufferedImage;

public class Runner {
	public static void main(String[] args) throws Exception {
		System.out.println(ConsoleColour.BLUE_BRIGHT);
		System.out.println("***************************************************");
		System.out.println("*                                                 *");
		System.out.println("*                                                 *");
		System.out.println("*           Image Filtering System V0.1           *");
		System.out.println("*                                                 *");
		System.out.println("*                                                 *");
		System.out.println("***************************************************");

		//Options for the menu
		System.out.println("1) Enter Image Name");  
		System.out.println("2) Select a Filter");
		System.out.println("3) Apply all Filters");
		System.out.println("4) Quit");

		//Declaring the required variables
		int quit = 0, count = 0;
		String imageName = null;
		BufferedImage inputImage = null, outputImage = null;
		double[][] filter = null;
		Field[] attributes;

		Scanner in = new Scanner(System.in);

		//Starting UI
		while (quit != 1) {
			System.out.println("\nSelect Option [1-4]>");
			int choice = in.nextInt();
			in.nextLine();

			//Switching for the given choice
			switch (choice) {
				case 1:
					System.out.println("\nEnter Name of the Image File [imagename.extension]>");
					imageName = in.nextLine();
					try {
						inputImage = ImageIO.read(new File(imageName));
						System.out.println("Image File you Entered: " + imageName);
					} catch (Exception FileNotFoundException) {
						System.out.println("Can't Read Input File!");
					}
					break;

				case 2:
					attributes = Kernel.class.getDeclaredFields();
					count = 1;

					//Listing all the kernel attributes of Kernel class
					for (Field field : attributes) {
						System.out.println(count + " : " + field.getName());
						count++;
					}
					System.out.println("\nSelect from above Filters [1-11]>");
					int filterChoice = in.nextInt();
					try {
						filter = Convolution.selectFilter(filterChoice); //Picking the required kernel
						System.out.println("Applying " + attributes[filterChoice - 1].getName() + " ...");
						outputImage = Convolution.convolve(inputImage, filter); //Convolving kernel with the image
						ImageIO.write(outputImage, "png",
								new File(attributes[filterChoice - 1].getName() + "_"+imageName));
						System.out.println("Output Generated: " + attributes[filterChoice - 1].getName() + "_" + imageName);
					} catch (NullPointerException e) {
						System.out.println("Input Image File Not Specified!");
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Please Input Correct Option!");
					}
					break;

				case 3:
					System.out.println("Applying all Filters...");
					attributes = Kernel.class.getDeclaredFields();
					try {
						for (int i = 0; i < 11; i++) {
							filter = Convolution.selectFilter(i + 1);

							outputImage = Convolution.convolve(inputImage, filter);

							ImageIO.write(outputImage, "png", new File(attributes[i].getName() + "_" + imageName));
							System.out.println("Output Generated: " + attributes[i].getName() + "_" + imageName);
						}
					} catch (NullPointerException e) {
						System.out.println("Input Image File Not Specified!");
					}
					break;

				case 4:
					quit = 1;
					break;

				default:
					System.out.println("Please Input Correct Option!");
					break;
			}
		}
		in.close();
		System.out.println(ConsoleColour.RESET);
	}
}