package Game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class SpriteLoader {

    public BufferedImage[][] loadSprites(String path, int width, int height) {
        // Use a Try-With-Resources to automatically close the stream
        try (InputStream is = getClass().getResourceAsStream(path)) {
            
            // Safety check: if the stream is null, the file path is incorrect
            if (is == null) {
                System.err.println("CRITICAL ERROR: Could not find sprite sheet at path: " + path);
                System.err.println("Ensure the folder 'Assets' is marked as a Source/Resource folder.");
                return null;
            }

            BufferedImage sheet = ImageIO.read(is);
            
            // Dynamically calculate grid based on image size
            int rows = sheet.getHeight() / height;
            int cols = sheet.getWidth() / width;
            
            BufferedImage[][] sprites = new BufferedImage[rows][cols];
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                	BufferedImage sub = sheet.getSubimage(j * width, i * height, width, height);
                    // Extract the specific frame
                    sprites[i][j] = sheet.getSubimage(j * width, i * height, width, height);
                }
            }
            
            System.out.println("Successfully loaded sheet: " + path + " (" + rows + "x" + cols + " frames)");
            return sprites;
            
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error processing image data for: " + path);
            e.printStackTrace();
            return null;
        }
    }
}