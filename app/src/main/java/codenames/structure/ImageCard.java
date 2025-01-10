package codenames.structure;

public class ImageCard implements Card {

    private static final long serialVersionUID = 1L;
    private String url;

    public ImageCard(String url){
        this.url = url;
    }

    public String getString(){
        return url;
    }
    
}
