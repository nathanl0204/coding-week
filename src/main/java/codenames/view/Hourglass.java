package codenames.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Hourglass extends Pane {
    private final Path upperSand;
    private final Path lowerSand;
    private final Path glass;
    private final DoubleProperty sandLevel = new SimpleDoubleProperty(1.0);
    private final BooleanProperty isRunning = new SimpleBooleanProperty(false);
    private Timeline timeline;
    private double duration;

    public Hourglass(double width, double height, double durationInSeconds) {
        this.duration = durationInSeconds;

        // Configuration de base du composant
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);

        // Création du verre du sablier
        glass = new Path();
        glass.setStroke(Color.BLACK);
        glass.setStrokeWidth(2);
        glass.setFill(Color.TRANSPARENT);

        // Création du sable supérieur
        upperSand = new Path();
        upperSand.setFill(Color.SANDYBROWN);

        // Création du sable inférieur
        lowerSand = new Path();
        lowerSand.setFill(Color.SANDYBROWN);

        getChildren().addAll(lowerSand, upperSand, glass);

        // Initialisation des formes
        updateShapes();

        // Binding des animations au niveau de sable
        sandLevel.addListener((obs, oldVal, newVal) -> updateSandLevels());

        // Ajout d'un listener pour mettre à jour les formes quand la taille change
        widthProperty().addListener((obs, oldVal, newVal) -> updateShapes());
        heightProperty().addListener((obs, oldVal, newVal) -> updateShapes());
    }

    private void updateShapes() {
        initializeShapes();
        updateSandLevels();
    }

    private void initializeShapes() {
        double w = getWidth();
        double h = getHeight();

        if (w <= 0 || h <= 0) {
            w = getPrefWidth();
            h = getPrefHeight();
        }

        // Dessiner le contour du sablier
        glass.getElements().clear();
        glass.getElements().addAll(
                new MoveTo(0.2 * w, 0.1 * h),
                new LineTo(0.8 * w, 0.1 * h),
                new QuadCurveTo(0.9 * w, 0.1 * h,
                        0.8 * w, 0.3 * h),
                new LineTo(0.55 * w, 0.45 * h),
                new LineTo(0.55 * w, 0.55 * h),
                new LineTo(0.8 * w, 0.7 * h),
                new QuadCurveTo(0.9 * w, 0.9 * h,
                        0.8 * w, 0.9 * h),
                new LineTo(0.2 * w, 0.9 * h),
                new QuadCurveTo(0.1 * w, 0.9 * h,
                        0.2 * w, 0.7 * h),
                new LineTo(0.45 * w, 0.55 * h),
                new LineTo(0.45 * w, 0.45 * h),
                new LineTo(0.2 * w, 0.3 * h),
                new QuadCurveTo(0.1 * w, 0.1 * h,
                        0.2 * w, 0.1 * h),
                new LineTo(0.2 * w, 0.1 * h)
        );
    }

    private void updateSandLevels() {
        double w = getWidth();
        double h = getHeight();

        if (w <= 0 || h <= 0) {
            w = getPrefWidth();
            h = getPrefHeight();
        }

        double level = sandLevel.get();

        // Mise à jour du sable supérieur
        upperSand.getElements().clear();
        upperSand.getElements().addAll(
                new MoveTo(0.2 * w, 0.1 * h),
                new LineTo(0.8 * w, 0.1 * h),
                new QuadCurveTo(0.9 * w, 0.1 * h,
                        0.8 * w, 0.3 * h),
                new LineTo(0.55 * w, 0.45 * h),
                new LineTo(0.45 * w, 0.45 * h),
                new LineTo(0.2 * w, 0.3 * h),
                new QuadCurveTo(0.1 * w, 0.1 * h,
                        0.2 * w, 0.1 * h)
        );

        // Mise à jour du sable inférieur avec animation de chute
        lowerSand.getElements().clear();
        lowerSand.getElements().addAll(
                new MoveTo(0.45 * w, 0.55 * h),
                new LineTo(0.55 * w, 0.55 * h),
                new LineTo(0.8 * w, 0.7 * h),
                new QuadCurveTo(0.9 * w, 0.9 * h,
                        0.8 * w, 0.9 * h),
                new LineTo(0.2 * w, 0.9 * h),
                new QuadCurveTo(0.1 * w, 0.9 * h,
                        0.2 * w, 0.7 * h),
                new LineTo(0.45 * w, 0.55 * h)
        );

        // Ajout de l'effet de chute du sable
        double centerX = w / 2;
        lowerSand.getElements().add(
                new MoveTo(centerX - 2, 0.55 * h)
        );
        lowerSand.getElements().add(
                new LineTo(centerX + 2, 0.55 * h + (0.35 * h * (1 - level)))
        );
    }

    public void startTimer() {
        if (isRunning.get()) return;

        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(sandLevel, 1.0)),
                new KeyFrame(Duration.seconds(duration), new KeyValue(sandLevel, 0.0))
        );

        timeline.setOnFinished(e -> {
            isRunning.set(false);
            if (onTimeUpHandler != null) {
                onTimeUpHandler.run();
            }
        });

        isRunning.set(true);
        timeline.play();
    }

    public void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
            isRunning.set(false);
        }
    }

    public void resumeTimer() {
        if (timeline != null) {
            timeline.play();
            isRunning.set(true);
        }
    }

    public void resetTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        sandLevel.set(1.0);
        isRunning.set(false);
    }

    private Runnable onTimeUpHandler;

    public void setOnTimeUp(Runnable handler) {
        this.onTimeUpHandler = handler;
    }

    public BooleanProperty isRunningProperty() {
        return isRunning;
    }

    public DoubleProperty sandLevelProperty() {
        return sandLevel;
    }
}
