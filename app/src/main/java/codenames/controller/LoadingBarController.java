package codenames.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LoadingBarController extends Chronometer {

    @FXML private Rectangle background;
    @FXML private Rectangle bar;
    @FXML private StackPane stackPane;

    private GameController gameController;

    private Timeline timeline = new Timeline();
    private boolean isComplete;

    private int seconds;
    private double height;

    public LoadingBarController() {}

    public LoadingBarController(int seconds,double height) {
        this.height = height;
        this.seconds = seconds;
        isComplete = false;
    }


    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @FXML
    public void initialize() {
        background.widthProperty().bind(stackPane.widthProperty());
        background.setHeight(height);

        bar.widthProperty().bind(stackPane.widthProperty());
        bar.setHeight(height);

        Rectangle clip = new Rectangle(0, height);
        bar.setClip(clip);
    }

    public void start() {
        if (timeline != null) {
            timeline.stop();
        }

        isComplete = false;
        super.start();

        Rectangle clip = (Rectangle) bar.getClip();
        clip.setWidth(0);
        bar.setFill(Color.DODGERBLUE);

        timeline.getKeyFrames().clear();
        for (int i = 0; i <= seconds; i++) {
            double progress = (double) i / seconds;
            Color color = interpolateColor(Color.DODGERBLUE, Color.RED, progress);
            
            timeline.getKeyFrames().add(
                    new KeyFrame(
                            Duration.seconds(i),
                            new KeyValue(clip.widthProperty(), background.getWidth() * progress),
                            new KeyValue(bar.fillProperty(), color)));
        }

        timeline.setOnFinished(event -> {
            isComplete = true;
            gameController.handleTimerEnd();
        });

        timeline.play();
    }

    private Color interpolateColor(Color startColor, Color endColor, double progress) {
        double red = startColor.getRed() + (endColor.getRed() - startColor.getRed()) * progress;
        double green = startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * progress;
        double blue = startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * progress;
        return new Color(red, green, blue, 1.0);
    }

    public void stop() {
        if (!isComplete) {
            timeline.stop();
            super.stop();
        }
    }
}
