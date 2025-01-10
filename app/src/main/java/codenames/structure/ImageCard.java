package codenames.structure;

import java.io.Serializable;

public class ImageCard implements Card, Serializable {

    private static final long serialVersionUID = 1L;
    private String url;

    public ImageCard(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
    
}
