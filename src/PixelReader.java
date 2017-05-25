import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PixelReader {

  public static byte[][][] readImage(BufferedImage img) {
    byte[][][] pixel = new byte[img.getWidth()][img.getHeight()][3];
    for (int i = 0; i < img.getWidth(); i++) {
      for (int j = 0; j < img.getHeight(); j++) {
        int color = img.getRGB(i, j);
        pixel[i][j][0] = (byte) (color >> 16);
        pixel[i][j][1] = (byte) (color >> 8);
        pixel[i][j][2] = (byte) color;
      }
    }

    return pixel;
  }

  public static void setImage(byte[][][] pixel, BufferedImage dest) {
    int color;
    for (int i = 0; i < pixel.length; i++) {
      for (int j = 0; j < pixel[j].length; j++) {
        color = (pixel[i][j][0] << 16) + (pixel[i][j][1] << 8) + (pixel[i][j][2]);
        dest.setRGB(i, j, color);
      }
    }
  }

  public static void saveImage(BufferedImage dest, String filename){
    try {
      ImageIO.write(dest,"bmp",new File(filename+".bmp"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static byte[][][] encodeImage(byte[][][] cover, byte[][][] message) {
    byte[][][] stego = new byte[cover.length][cover[0].length][3];

    for (int i = 0; i < stego.length; i++) {
      for (int j = 0; j < stego[j].length; j++) {
        if (i < message.length && j < message[i].length) {
          System.out.println("ori :" + Integer.toBinaryString(cover[i][j][0]));
          System.out.println("Msg :" + Integer.toBinaryString(message[i][j][0]));
          if (message[i][j][0] != -1) {
            stego[i][j][0] = (byte)(((byte)(cover[i][j][0] >> 1) << 1));
            stego[i][j][1] = (byte)(((byte)(cover[i][j][1] >> 1) << 1));
            stego[i][j][2] = (byte)(((byte)(cover[i][j][2] >> 1) << 1));
          }
          else {
            stego[i][j][0] = (byte)(((byte)(cover[i][j][0] >> 1) << 1) + 1);
            stego[i][j][1] = (byte)(((byte)(cover[i][j][1] >> 1) << 1) + 1);
            stego[i][j][2] = (byte)(((byte)(cover[i][j][2] >> 1) << 1) + 1);
          }
          System.out.println("end :" + Integer.toBinaryString(stego[i][j][0]));
        }
        else {
          stego[i][j][0] = cover[i][j][0];
          stego[i][j][1] = cover[i][j][1];
          stego[i][j][2] = cover[i][j][2];
        }
      }
    }

    return stego;
  }

  public static byte[][][] decodeMessage(byte[][][] stego){
    byte[][][] message = new byte[stego.length][stego[0].length][3];

    for (int i = 0; i < stego.length; i++) {
      for (int j = 0; j < stego[j].length; j++) {
        if ((stego[i][j][0] & 0x01) == 1) {
          message[i][j][0] = 0;
          message[i][j][1] = 0;
          message[i][j][2] = 0;
        } else {
          message[i][j][0] = -1;
          message[i][j][1] = -1;
          message[i][j][2] = -1;
        }
      }
    }
    return message;
  }
}
