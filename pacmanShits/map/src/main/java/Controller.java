import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private int movement = 1;
    private int coinCollected = 0;
    private int coins = 0;
    private int pacmanLife = 4;
    public boolean canMove;
    @FXML
    GridPane gridPane;
    Pacman pacman = new Pacman(21, 4, 4, 4, "/ffff.gif");
    Ghost ghost1 = new Ghost(22, 1, 1, "/wobbles.gif");
    Ghost ghost2 = new Ghost(22, 19, 1, "/wobbles.gif");
    Ghost ghost3 = new Ghost(22, 1, 19, "/wobbles3.gif");
    Ghost ghost4 = new Ghost(22, 19, 19, "/wobbles3.gif");
    private Timeline ghostMoveTimeline;
    private Timeline pacmanMoveTimeline;
    private Cell[][] map;

    @FXML
    public void initialize() {
        map = Maze.getMaze().mazeInit();
        canMove =true;
        pacman.setMap(map);
        Glow blue = new Glow();
        blue.setLevel(20);
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    update();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (checkForEnd())
                    reset(this);
            }
        };
        KeyFrame pacmanFrame = new KeyFrame(Duration.millis(400), event -> {
            try {
                move();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        KeyFrame ghostFrame = new KeyFrame(Duration.millis(200), event -> {
            try {
                ghost1.moveGhosts(map);
                ghost2.moveGhosts(map);
                ghost3.moveGhosts(map);
                ghost4.moveGhosts(map);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        ghostMoveTimeline = new Timeline(ghostFrame);
        ghostMoveTimeline.setRate(0.4);
        ghostMoveTimeline.setCycleCount(Animation.INDEFINITE);
        ghostMoveTimeline.play();
        pacmanMoveTimeline = new Timeline(pacmanFrame);
        pacmanMoveTimeline.setCycleCount(Animation.INDEFINITE);
        pacmanMoveTimeline.play();
        animationTimer.start();
        for (int i = 0; i < 2 * 10 + 1; i++) {
            for (int j = 0; j < 2 * 10 + 1; j++) {
                StackPane newPane = new StackPane();
                if (map[i][j].getType().equals("0") || map[i][j].getType().equals("*")) {
                    Bloom bloom = new Bloom();
                    bloom.setThreshold(10);
                    Circle coin = new Circle(10, Color.GOLD);
                    coin.setEffect(bloom);
                    TranslateTransition transition = new TranslateTransition(Duration.millis(1000), coin);
                    newPane.getChildren().add(coin);
                    StackPane.setAlignment(coin, Pos.CENTER);
                    transition.setToY(5);
                    transition.setCycleCount(Animation.INDEFINITE);
                    transition.setAutoReverse(true);
                    transition.play();
                    newPane.setStyle("-fx-background-color:black");
                    coins++;
                } else if (map[i][j].getType().equals("1")) {
                    newPane.setStyle("-fx-background-color:#0c2d48");
                    newPane.setEffect(blue);
                }
                newPane.setPrefWidth(50);
                newPane.setPrefHeight(50);
                if (i == 4 && j == 5)
                    newPane.getChildren().clear();
                gridPane.add(newPane, j, i);
            }
        }
        gridPane.add(ghost1, 1, 1);
        gridPane.add(ghost2, 1, 19);
        gridPane.add(ghost3, 19, 1);
        gridPane.add(ghost4, 19, 19);
        gridPane.add(pacman, 4, 4);
        GridPane.setHalignment(pacman, HPos.CENTER);
        System.out.println(coins);
    }

    private void reset(AnimationTimer animationTimer) {
        animationTimer.stop();
        pacmanMoveTimeline.stop();
        ghostMoveTimeline.stop();
    }


    private void update() throws InterruptedException {
        KeyFrame keyframe = new KeyFrame(Duration.millis(11), event -> {
            checkForHit();
        });
        Timeline timeline = new Timeline(keyframe);
        timeline.play();
        StackPane node = (StackPane) getNodeByRowColumnIndex(pacman.getXPosition(), pacman.getYPosition(), gridPane);
        ObservableList<Node> children = node.getChildren();
        List<Circle> circles = children.stream()
                .filter(n -> n instanceof Circle).map(n -> (Circle) n).collect(Collectors.toList());
        for (Circle circle : circles) {
            if (pacman.getBoundsInParent().intersects(node.getBoundsInParent())) {
                coinCollected++;
                System.out.println(coinCollected);
                node.getChildren().clear();
            }
        }
    }

    private void checkForHit() {
        if (!pacman.isAttackble())
            return;
        if (pacman.getBoundsInParent().intersects(ghost1.getBoundsInParent())) {
            pacmanLife--;
            fixAttackTiming();
            System.out.println("sjsj");
        }
        if (pacman.getBoundsInParent().intersects(ghost2.getBoundsInParent())) {
            cantBeAttacked();
            System.out.println("shoso");
        }
        if (pacman.getBoundsInParent().intersects(ghost3.getBoundsInParent())) {
            cantBeAttacked();
            System.out.println("shim");
        }
        if (pacman.getBoundsInParent().intersects(ghost4.getBoundsInParent())) {
            cantBeAttacked();
            System.out.println("shoomb");
        }
    }

    private void cantBeAttacked() {
        fixAttackTiming();
        pacmanLife--;
    }

    private void fixAttackTiming() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1200), pacman);
        KeyFrame ghost = new KeyFrame(Duration.millis(10), event -> {
            pacman.setAttackble(false);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        });
        Timeline timeline = new Timeline(ghost);
        timeline.play();
        KeyFrame ss = new KeyFrame(Duration.millis(1000), event -> {
            pacman.setAttackble(true);
            fadeTransition.stop();
        });
        Timeline timeline1 = new Timeline(ss);
        timeline1.play();
    }


    private boolean checkForEnd() {
        if (pacmanLife == 0)
            return true;
        if (coinCollected != coins - 1)
            return false;
        System.out.println("game ended , going for next round");
        return true;
    }

    private void move() throws InterruptedException {
        if (movement == 1)
            pacman.moveRight();
        if (movement == 2)
            pacman.moveDown();
        if (movement == 3)
            pacman.moveLeft();
        if (movement == 4)
            pacman.moveUp();
    }

    public void listenFor() {
        pacman.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                pacman.rotateProperty().set(360);
                movement = 1;
            }
            if (event.getCode() == KeyCode.LEFT) {
                pacman.rotateProperty().set(180);
                movement = 3;
            }
            if (event.getCode() == KeyCode.UP) {
                pacman.rotateProperty().set(-90);
                movement = 4;
            }
            if (event.getCode() == KeyCode.DOWN) {
                pacman.rotateProperty().set(90);
                movement = 2;
            }
            if (event.getCode() == KeyCode.ESCAPE)
                pause();
        });
    }

    private void pause() {
    }


    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }
}
