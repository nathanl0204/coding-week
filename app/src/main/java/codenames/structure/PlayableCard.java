package codenames.structure;

import java.io.Serializable;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class PlayableCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private Card card;
    private CardType cardType;
    private Boolean guessed;
    private transient StackPane stackPane;

    public PlayableCard(Card card, CardType cardType){
        this.card = card;
        this.cardType = cardType;
        this.guessed = false;
    }

    public void setStackPane(StackPane stackPane){
        this.stackPane = stackPane;
    }

    public StackPane getStackPane() {
        return stackPane;
    }
    
    public Card getCard(){
        return card;
    }

    public void guessed(){
        guessed = true;
    }

    public Boolean isGuessed(){
        return guessed;
    }

    public CardType getCardType(){
        return cardType;
    }

    public Color getColor(){
        switch (cardType) {
            case Black:
                return Color.BLACK;
            case White:
                return Color.GREY;
            case Red:
                return Color.RED;
            case Blue:
                return Color.BLUE;
            default:
                return null;
        }
    }

    public char getColorCode() {
        switch (cardType) {
            case Black:
                return 'a';
            case White:
                return 'w';
            case Red:
                return 'r';
            case Blue:
                return 'b';
            default:
                return 'w';
        }
    }

    // Méthode pour recréer le StackPane après désérialisation
    public void recreateStackPane() {
        if (this.stackPane == null) {
            this.stackPane = new StackPane();
            // Recréer l'apparence visuelle de la carte
            if (card instanceof TextCard) {
                Label label = new Label(((TextCard) card).getText());
                label.setTextFill(getColor());
                label.setPadding(new Insets(35, 0, 0, 0));
                String resourcePath = "/card_back.jpg";
                Image backgroundImage = new Image(getClass().getResource(resourcePath).toString());
                ImageView background = new ImageView(backgroundImage);
                background.setFitWidth(100);
                background.setFitHeight(80);
                stackPane.getChildren().addAll(background, label);
            } else if (card instanceof ImageCard) {
                ImageView imgView = new ImageView(((ImageCard) card).getUrl());
                stackPane.getChildren().add(imgView);
            }

            if (guessed) {
                Rectangle transparency = new Rectangle(100, 80);
                transparency.setFill(getColor().deriveColor(0, 1, 1, 0.5));
                stackPane.getChildren().add(transparency);
            }
        }
    }
}
