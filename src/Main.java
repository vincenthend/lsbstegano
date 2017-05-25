import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

  public static void main(String[] args){
    BufferedImage srcImage = null;
    BufferedImage hiddenImage = null;

    try {
      srcImage = ImageIO.read(new File("src.bmp"));
      hiddenImage = ImageIO.read(new File("msg.bmp"));

      //Hiding
      System.out.println("Reading files");
      byte[][][] hidden = PixelReader.readImage(hiddenImage);
      byte[][][] source = PixelReader.readImage(srcImage);
      System.out.println("Encoding files");
      byte[][][] newPixel = PixelReader.encodeImage(source,hidden);
      System.out.println("Saving file");
      PixelReader.setImage(newPixel, srcImage);
      PixelReader.saveImage(srcImage, "out");
      System.out.println("");
      //Decoding
      System.out.println("Decoding files");
      byte[][][] stego = PixelReader.readImage(srcImage);
      byte[][][] message = PixelReader.decodeMessage(stego);
      System.out.println("Saving file");
      PixelReader.setImage(message, srcImage);
      PixelReader.saveImage(srcImage, "decode");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
