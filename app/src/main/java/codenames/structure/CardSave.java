package codenames.structure;

import java.io.Serializable;

public class CardSave implements Serializable {
    private CardType cardType;
    private boolean guessed;
    private String text; // Pour TextCard
    private String url; // Pour ImageCard
    private boolean isImageCard;

    public CardSave(Card card) {
        this.cardType = card.getCardType();
        this.guessed = card.isGuessed();

        if (card instanceof TextCard) {
            this.text = ((TextCard) card).getText();
            this.isImageCard = false;
        } else if (card instanceof ImageCard) {
            this.url = ((ImageCard) card).getUrl();
            this.isImageCard = true;
        }
    }

    public Card toCard() {
        Card card;
        if (isImageCard) {
            card = new ImageCard(cardType, url);
        } else {
            card = new TextCard(cardType, text);
        }
        if (guessed) {
            card.guessed();
        }
        return card;
    }
}
