package codenames.structure;

import java.io.Serializable;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Game implements Serializable {

    private int id;
    
    private Boolean onGoing;
    private Statistics blueStat,redStat;
    private ListCard cards;
    private Boolean blueTurn;
    private int remainingCardGuess;
    private int cols;
    private Image QRCode;


    public Game(int cols, ListCard cards,int numberOfBlueCard, int numberOfRedCard){
        this.cols = cols;
        blueStat = new Statistics(numberOfBlueCard);
        redStat = new Statistics(numberOfRedCard);
        this.cards = cards;
        blueTurn = true;
        generateQRCode();
        onGoing = true;
    }

    public Image getQRCode() {
        return QRCode;
    }

    public void setQRCode(Image QRCode) {
        this.QRCode = QRCode;
    }

    public int getCols(){
        return cols;
    }

    public Statistics getBlueStatistics(){
        return blueStat;
    }

    public Statistics getRedStatistics(){
        return redStat;
    }

    public int getRemainingCardGuess(){
        return remainingCardGuess;
    }

    public void setRemainingCardGuess(int remainingCardGuess){
        this.remainingCardGuess = remainingCardGuess;
    }

    public int getId(){
        return id;
    }

    public ListCard getListCard(){
        return cards;
    }

    public Boolean isBlueTurn(){
        return blueTurn;
    }

    public CardType getColorTurn(){
        if (blueTurn) return CardType.Blue;
        else return CardType.Red;
    }

    public void changeTurn(int remainingCardGuess){
        blueTurn = !blueTurn;
        if (blueTurn) blueStat.incrNumberOfTurns();
        else redStat.incrNumberOfTurns();
        this.remainingCardGuess = remainingCardGuess;
    }

    public void ends(){
        onGoing = false;
    } 

    public Boolean isOnGoing(){
        return onGoing && remainingCardGuess > 0;
    }

    public void correctGuess(){
        if (blueTurn) {
            blueStat.incrNumberOfCorrectGuess();
            blueStat.decrNumberOfRemainingCardsToFind();
        }
        else {
            redStat.incrNumberOfCorrectGuess();
            redStat.decrNumberOfRemainingCardsToFind();
        } 
        remainingCardGuess--;
        
    }

    public void wrongGuess(CardType cardType){
        remainingCardGuess = 0;
        if (blueTurn) {
            blueStat.incrNumberOfErrors();
            if (cardType == CardType.Red) redStat.decrNumberOfRemainingCardsToFind();
        }
        else {
            redStat.incrNumberOfErrors();
            if (cardType == CardType.Blue) blueStat.decrNumberOfRemainingCardsToFind();
        }
        // temps
    }

    public int getNumberOfOpponentRemainingCardsToFind() {
        if (blueTurn) return redStat.getNumberOfRemainingCardsToFind();
        else return blueStat.getNumberOfRemainingCardsToFind();
    }

    public int getNumberOfRemainingCardsToFind() {
        if (blueTurn) return blueStat.getNumberOfRemainingCardsToFind();
        else return redStat.getNumberOfRemainingCardsToFind();
    }

    public String generateColorsString() {
        StringBuilder colors = new StringBuilder();

        for (Card card : cards.getCards()) {
            colors.append(card.getColorCode());
        }

        return colors.toString();
    }

    public String generateURL() {
        return "https://gibson-pages.telecomnancy.univ-lorraine.fr/grp05-851491?rows=" + cards.getCards().size() / getCols() + "&columns=" + getCols() + "&colors=" + generateColorsString();
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

    /* 
    game.simuleOpponent(){
        // blueTurn = false;
        // temps artificiel
        // pick nb random de case a retourner
        // pick des cartes aleatoirement
        // blueTurn = true;
    }

    */

    /* 
    game.simuleCoequipier(){
        // changer la structure des mots
        // et renvoie un int (nb de carte) et un string (indice)
        parmi les differents liste de mots du jeu
    }
     
    */

    /* utilisation du pattern strategy pour different type d'ia


        creer nouvelle classe pour gameSoloController

     */
}
