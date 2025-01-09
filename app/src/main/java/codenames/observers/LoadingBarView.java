package codenames.observers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LoadingBarView extends StackPane implements Observer {
    @FXML
    private Rectangle background;
    @FXML
    private Rectangle bar;

    private GameView gameView;

    private Timeline timeline = new Timeline();
    private boolean isComplete = false;
    private double elapsedSeconds;

    private double width;
    private double height;

    public LoadingBarView() {
    }

    public LoadingBarView(double width, double height) {
        this.height = height;
        this.width = width;

    }

    public void setGameController(GameView gameView) {
        this.gameView = gameView;
    }

    @FXML
    public void initialize() {
        background.setWidth(width);
        background.setHeight(height);

        bar.setWidth(width);
        bar.setHeight(height);

        // Initialize the clip for the bar
        Rectangle clip = new Rectangle(0, height);
        bar.setClip(clip);
    }

    public void start(int seconds) {
        if (timeline != null) {
            timeline.stop();
        }

        isComplete = false;
        elapsedSeconds = System.currentTimeMillis();

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
            gameView.handleTimerEnd();
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
        if (timeline != null && !isComplete) {
            timeline.stop();
        }
    }

    public void reset() {
        stop();
        isComplete = false;
        elapsedSeconds = 0;
        Rectangle clip = (Rectangle) bar.getClip();
        clip.setWidth(0);
        bar.setFill(Color.DODGERBLUE);
    }

    public boolean isComplete() {
        return isComplete;
    }

    public double getElapsedSeconds() {
        return ( System.currentTimeMillis() - elapsedSeconds)/1000.0;
    }

    @Override
    public void react() {

    }
}
