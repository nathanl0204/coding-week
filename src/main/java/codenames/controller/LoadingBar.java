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

        timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(clip.widthProperty(), 0)),
                new KeyFrame(Duration.seconds(seconds * 0.8), // Change la couleur à 80% du temps
                        event -> bar.setFill(Color.RED)),
                new KeyFrame(Duration.seconds(seconds), event -> {
                    isComplete = true;
                    stop();
                }, new KeyValue(clip.widthProperty(), background.getWidth()))
        );

        // Ajouter un Keyframe pour mettre à jour elapsedSeconds chaque seconde
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), event -> elapsedSeconds++, new KeyValue[0])
        );
        timeline.setCycleCount((int) seconds);

        timeline.play();
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
