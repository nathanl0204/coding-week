package codenames.structure;

public class ImageCard implements Card{
    
    private String url;

    public ImageCard(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
    
}
