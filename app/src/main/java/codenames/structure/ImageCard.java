package codenames.structure;

public class ImageCard extends Card{
    
    private String url;

    public ImageCard(CardType cardType, String url){
        super(cardType);
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
