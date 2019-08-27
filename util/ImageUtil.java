package util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import data.world.World;

public class ImageUtil {
	/**
	 * Get image for given world 
	 */
	public static BufferedImage createImage(World world) {
		if (world == null) {
			return new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_INDEXED);
		}
		
	    BufferedImage image = new BufferedImage(world.width(), world.height(), BufferedImage.TYPE_BYTE_INDEXED);
	    WritableRaster raster = image.getRaster();
	    
	    for(int column = 0; column < world.width(); column++) {
	        for(int row = 0; row < world.height(); row++) {
	        	// Image coords are reverse of world coords
	            raster.setSample(column, row, 0, world.getColorValue(row, column));
	        }
	    }
	    
	    return image;
	}
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
	    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, Image.SCALE_DEFAULT);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
	    g.dispose();

	    return resizedImage;
	}
}
