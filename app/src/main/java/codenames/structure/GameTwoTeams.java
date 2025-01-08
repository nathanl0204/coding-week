package codenames.structure;

import java.awt.image.BufferedImage;
import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class GameTwoTeams extends Game {
    private Image QRCode;
    private List<PlayableCard> deck;

    public GameTwoTeams(List<PlayableCard> deck, int cols, int numberOfBlueCard, int numberOfRedCard){
        super(cols, numberOfBlueCard, numberOfRedCard);
        this.deck = deck;
        generateQRCode();
    }

    public List<PlayableCard> getDeck(){
        return deck;
    }

    public Image getQRCode() {
        return QRCode;
    }

    public void setQRCode(Image QRCode) {
        this.QRCode = QRCode;
    }


    public String generateColorsString() {
        StringBuilder colors = new StringBuilder();

        deck.forEach( card -> colors.append(card.getColorCode()));
            
        return colors.toString();
    }

    public String generateURL() {
        return "https://gibson-pages.telecomnancy.univ-lorraine.fr/grp05-851491?rows=" + deck.size() / getCols() + "&columns=" + getCols() + "&colors=" + generateColorsString();
    }

    public void generateQRCode() {
        String url = generateURL();
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, 300, 300);

            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

            //MatrixToImageWriter.writeToPath(matrix, "PNG", Path.of("qrcode.png")); IF YOU WANT TO SAVE THE QRCODE (FOR DEBUGGING PURPOSES) UNCOMMENT THIS ! THE QR CODE WILL BE SAVED IN app/qrcode.png
            setQRCode(SwingFXUtils.toFXImage(bufferedImage, null));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
