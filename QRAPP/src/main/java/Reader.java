import com.google.zxing.BinaryBitmap;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class Reader {
    public static void main(String[] Args) throws Exception{
        try{
            String path = "D:\\qr_code\\wiki.jpeg";
            BufferedImage bf = ImageIO.read(new FileInputStream(path));
            QRCodeReader decoder = new QRCodeReader();
            Result decodedResult = decoder.decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf))));
            String decodedText = decodedResult.getText();
            System.out.println(decodedText);
        }
        catch(Exception e){
           System.out.println("Couldn't decode");
        }
    }
}
