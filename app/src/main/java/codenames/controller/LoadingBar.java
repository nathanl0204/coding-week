package codenames.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LoadingBar extends StackPane {
    private final Rectangle background;
    private final Rectangle bar;
    private Timeline timeline;
    private boolean isComplete = false;
    private int elapsedSeconds = 0;
    private int totalSeconds = 0;

    public LoadingBar(double width, double height) {
        background = new Rectangle(width, height);
        background.setFill(Color.LIGHTGRAY);
        background.setArcWidth(15);
        background.setArcHeight(15);

        bar = new Rectangle(width, height);
        bar.setFill(Color.DODGERBLUE);
        bar.setArcWidth(15);
        bar.setArcHeight(15);

        Rectangle clip = new Rectangle(0, height);
        bar.setClip(clip);

        getChildren().addAll(background, bar);
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
