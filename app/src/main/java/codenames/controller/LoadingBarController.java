package codenames.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LoadingBarController extends StackPane {
    @FXML private Rectangle background;
    @FXML private Rectangle bar;
    
    private Timeline timeline;
    private boolean isComplete = false;
    private int elapsedSeconds = 0;
    private int totalSeconds = 0;

    private double width;
    private double height;
    
    public LoadingBarController(){}

    public LoadingBarController(double width, double height) {
        this.height = height;
        this.width = width;
    }

    @FXML 
    public void initialize(){
        background.setWidth(width);
        background.setHeight(height);
        
        Rectangle clip = new Rectangle(0, height);
        bar.setClip(clip);
    }

    public void start(int seconds) {
        if (timeline != null) {
            timeline.stop();
        }

        isComplete = false;
        elapsedSeconds = 0;
        totalSeconds = seconds;
        Rectangle clip = (Rectangle) bar.getClip();
        clip.setWidth(0);
        bar.setFill(Color.DODGERBLUE);

        timeline = new Timeline();

        // Ajout des keyframes pour la progression et le changement de couleur
        for (int i = 0; i <= seconds; i++) {
            double progress = (double) i / seconds;
            Color color = interpolateColor(Color.DODGERBLUE, Color.RED, progress);

            timeline.getKeyFrames().add(
                    new KeyFrame(
                            Duration.seconds(i),
                            new KeyValue(clip.widthProperty(), background.getWidth() * progress),
                            new KeyValue(bar.fillProperty(), color)
                    )
            );
        }

        // Mise à jour du temps écoulé
        KeyFrame updateFrame = new KeyFrame(Duration.seconds(1), event -> {
            elapsedSeconds++;
            if (elapsedSeconds >= totalSeconds) {
                isComplete = true;
                stop();
            }
        });

        Timeline updateTimeline = new Timeline(updateFrame);
        updateTimeline.setCycleCount(seconds);
        updateTimeline.play();

        timeline.setOnFinished(event -> {
            isComplete = true;
            stop();
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

    public int getRemainingSeconds() {
        return totalSeconds - elapsedSeconds;
    }
}
