package codenames.view;

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

    public LoadingBar(double width, double height) {
        background = new Rectangle(width, height);
        background.setFill(Color.LIGHTGRAY);
        background.setArcWidth(15);
        background.setArcHeight(15);

        bar = new Rectangle(0, height); // Initial width is 0
        bar.setFill(Color.DODGERBLUE);
        bar.setArcWidth(15);
        bar.setArcHeight(15);

        // Align the bar to the left
        bar.setTranslateX(-width / 2);

        getChildren().addAll(background, bar);
    }

    public void start(int seconds) {
        if (timeline != null) {
            timeline.stop();
        }

        isComplete = false;
        bar.setWidth(0); // Reset the width
        bar.setFill(Color.DODGERBLUE);

        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(bar.widthProperty(), 0)),
                new KeyFrame(Duration.seconds(seconds), event -> {
                    bar.setFill(Color.RED);
                    isComplete = true;
                    stop();
                }, new KeyValue(bar.widthProperty(), background.getWidth())) // Target full width
        );

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
        bar.setWidth(0);
        bar.setFill(Color.DODGERBLUE);
    }

    public boolean isComplete() {
        return isComplete;
    }
}
