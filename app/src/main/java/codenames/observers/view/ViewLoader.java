package codenames.controller.view;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public abstract class ViewLoader<T> {
    private T graphic;

    public void loadGraphic(FXMLLoader loader) throws IOException {
        this.graphic = loader.load();
    }

    public T getGraphic() {
        return this.graphic;
    }
}
