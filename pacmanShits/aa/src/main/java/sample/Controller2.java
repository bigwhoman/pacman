package sample;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Controller2 {

    public Circle circle;
    public Rectangle[] rectangles;
    public AnchorPane pane;
    public Circle circle2 = new Circle(150, 200, 40, Color.CORAL);
    @FXML
    public void initialize() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        animationTimer.start();

        rectangles = new Rectangle[3];
        for (int i = 0; i < 3; i++) {
            rectangles[i] = new Rectangle();
            rectangles[i].setHeight(22);
            rectangles[i].setWidth(23);
            rectangles[i].setFill(Color.BLUE);
            rectangles[i].setX(50 * (i + 2));
            rectangles[i].setY(50 * (i + 2));
            pane.getChildren().add(rectangles[i]);
        }
        pane.getChildren().add(circle2);
        setRectangleAnimation();

    }

    private void setRectangleAnimation() {
        for (Rectangle rectangle : rectangles) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), rectangle);
            transition.setByX(50);
            transition.setToY(20);
            transition.setAutoReverse(true);
            transition.setCycleCount(Animation.INDEFINITE);
            transition.play();
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1200),rectangle);
            scaleTransition.setToY(2);
            scaleTransition.setAutoReverse(true);
            scaleTransition.setCycleCount(Animation.INDEFINITE);
            scaleTransition.play();
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(500),rectangle);
            rotateTransition.setByAngle(180);
            rotateTransition.setCycleCount(Animation.INDEFINITE);
            rotateTransition.setAutoReverse(true);
            rotateTransition.play();

        }
    }

    public void start() {

        Scene scene = circle.getScene();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    moveLeft();
                    break;
                case RIGHT:
                    moveRight();
                    break;
                case DOWN:
                    moveDown();
                    break;
                case UP:
                    moveUp();
                    break;
                case SPACE:
                    break;
            }
        });

    }

    private void moveUp() {
       circle.setTranslateY(circle.getTranslateY()-10);
        if(circle2.getBoundsInParent().intersects(circle.getBoundsInParent()))
            circle.translateYProperty().set(circle.getTranslateY()-10);
    }

    private void moveDown() {
        circle.translateYProperty().set(circle.getTranslateY() + 10);
        if(circle2.getBoundsInParent().intersects(circle.getBoundsInParent()))
            circle.translateYProperty().set(circle.getTranslateY()-10);
    }

    private void moveRight() {
        circle.translateXProperty().set(circle.getTranslateX() + 10);
        if(circle2.getBoundsInParent().intersects(circle.getBoundsInParent()))
            circle.translateXProperty().set(circle.getTranslateX()-10);
    }

    private void moveLeft() {
        circle.translateXProperty().set(circle.getTranslateX() - 10);
        if(circle2.getBoundsInParent().intersects(circle.getBoundsInParent()))
            circle.translateXProperty().set(circle.getTranslateX()+10);
    }

    private void update() {
        for (Rectangle rectangle : rectangles) {
            if(circle.getBoundsInParent().intersects(rectangle.getBoundsInParent()))
                rectangle.setFill(Color.TRANSPARENT);
        }
    }
}
