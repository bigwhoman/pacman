package controller;

import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class GameController {
    private static User user;

    private int movement = 1;
    private int coinCollected = 0;
    private int coins = 0;
    private boolean isGameRunning;
    private boolean isMuted;
    private Cell[][] map;


    @FXML
    public Button mute;
    public GridPane gridPane;
    public Button quitButton;
    public Button saveMap;
    public Label player;
    public Label lives;
    public Label notifLabel;
    public Label points;
    private Pacman pacman;
    private Ghost ghost1;
    private Ghost ghost2;
    private Ghost ghost3;
    private Ghost ghost4;
    private Timeline ghostMoveTimeline;
    private Timeline pacmanMoveTimeline;
    private AnimationTimer animationTimer;


    @FXML
    public void initialize() {
        isGameRunning = true;
        map = PreGame.getMap();
        if (map == null)
            map = Map.getMap().mapInit();
        ghost1 = new Ghost(22, 1, 1, "/images/wobbles.gif", map);
        ghost2 = new Ghost(22, 19, 1, "/images/wobbles.gif", map);
        ghost3 = new Ghost(22, 1, 19, "/images/wobbles3.gif", map);
        ghost4 = new Ghost(22, 19, 19, "/images/wobbles3.gif", map);
        pacman = new Pacman(21, 4, 4, 5, "/images/ffff.gif");
        initMap();
        gridPane.add(ghost1, 1, 1);
        gridPane.add(ghost2, 1, 19);
        gridPane.add(ghost3, 19, 1);
        gridPane.add(ghost4, 19, 19);
        gridPane.add(pacman, 4, 4);
        pacman.setMap(map, 4, 4);
        GridPane.setHalignment(pacman, HPos.CENTER);
        timerFix();
    }

    public void secondInit(int pacmanLives) {
        pacman.setLives(pacmanLives);
        lives.setText(pacmanLives + "");
    }

    private void initMap() {
        Glow blue = new Glow();
        blue.setLevel(20);
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
    }

    private void timerFix() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    update();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

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
                ghost1.moveGhosts();
                ghost2.moveGhosts();
                ghost3.moveGhosts();
                ghost4.moveGhosts();
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
    }

    private void endWithDeath() throws IOException {
        animationTimer.stop();
        pacmanMoveTimeline.stop();
        ghostMoveTimeline.stop();
        Alert alert = new Alert
                (AlertType.INFORMATION, "Pacman DIED,redirecting to main menu!!", ButtonType.OK);
        alert.setHeaderText("DIED!!");
        alert.show();
        playAudio("/music/pacman_death.wav");
        KeyFrame newGameFrame = new KeyFrame(Duration.millis(2000), event -> {
            try {
                PreGame.player.stop();
                toMain();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Timeline newGameTimeline = new Timeline(newGameFrame);
        newGameTimeline.play();
    }


    private void update() throws InterruptedException, IOException {
        checkForEnd();
        lives.setText(pacman.getLives() + "");
        points.setText(5 * coinCollected + "");
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
                if (user != null)
                    user.setScore(user.getScore() + 5);
                node.getChildren().clear();
            }
        }
    }

    private void checkForHit() {
        if (!pacman.isAttackble())
            return;
        if (pacman.getBoundsInParent().intersects(ghost1.getBoundsInParent())) {
            playAudio("/music/death.mp3");
            cantBeAttacked();
        }
        if (pacman.getBoundsInParent().intersects(ghost2.getBoundsInParent())) {
            playAudio("/music/death.mp3");
            cantBeAttacked();
        }
        if (pacman.getBoundsInParent().intersects(ghost3.getBoundsInParent())) {
            playAudio("/music/death.mp3");
            cantBeAttacked();
        }
        if (pacman.getBoundsInParent().intersects(ghost4.getBoundsInParent())) {
            playAudio("/music/death.mp3");
            cantBeAttacked();
        }
    }

    private void cantBeAttacked() {
        fixAttackTiming();
        pacman.setLives(pacman.getLives() - 1);
    }

    private void playAudio(String place) {
        if(isMuted)
            return;
        URL audio = getClass().getResource(place);
        AudioClip audioClip = new AudioClip(audio.toString());
        audioClip.play();
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


    private void checkForEnd() throws IOException {
        if (pacman.getLives() == 0) {
            endWithDeath();
            return;
        }

        if (coinCollected == coins - 1) {
            pacmanMoveTimeline.stop();
            ghostMoveTimeline.stop();
            KeyFrame setCoinsFrame = new KeyFrame(Duration.millis(2000), event -> {
                pacmanMoveTimeline.play();
                ghostMoveTimeline.play();
            });
            Timeline setCoinsTimeline = new Timeline(setCoinsFrame);
            setCoinsTimeline.play();
            resetMap();
        }
    }

    private void resetMap() {
        setCoins();
    }

    private void setCoins() {
        for (int i = 0; i < 2 * 10 + 1; i++) {
            for (int j = 0; j < 2 * 10 + 1; j++) {
                if (map[i][j].getType().equals("0") || map[i][j].getType().equals("*")) {
                    StackPane pane = (StackPane) getNodeByRowColumnIndex(i, j, gridPane);
                    Bloom bloom = new Bloom();
                    bloom.setThreshold(10);
                    Circle coin = new Circle(10, Color.GOLD);
                    coin.setEffect(bloom);
                    TranslateTransition transition = new TranslateTransition(Duration.millis(1000), coin);
                    transition.setToY(5);
                    transition.setCycleCount(Animation.INDEFINITE);
                    transition.setAutoReverse(true);
                    transition.play();
                    pane.getChildren().add(coin);
                    coins++;
                }
            }
        }
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
        if (isGameRunning) {
            animationTimer.stop();
            pacmanMoveTimeline.stop();
            ghostMoveTimeline.stop();
            PreGame.player.stop();
            isGameRunning = false;
            Alert alert = new Alert(AlertType.INFORMATION, "Game is paused");
            alert.setHeaderText("Paused");
            alert.show();
            return;
        }
        animationTimer.start();
        pacmanMoveTimeline.play();
        ghostMoveTimeline.play();
        isGameRunning = true;
        PreGame.player.play();
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

    public void goToMainMenu(ActionEvent event) throws IOException {
        toMain();
    }

    private void toMain() throws IOException {
        PreGame.player.stop();
        pacmanMoveTimeline.stop();
        ghostMoveTimeline.stop();
        animationTimer.stop();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage window = (Stage) quitButton.getScene().getWindow();
        window.setScene(new Scene(parent));
        WelcomeMenuController.mediaPlayer.play();
    }

    public void saveMap(ActionEvent event) {
        if (user == null) {
            notifLabel.setTextFill(Color.RED);
            notifLabel.setText("you are not logged in, cant save map");
            return;
        }
        user.addToMaps(map);
        notifLabel.setTextFill(Color.GREEN);
        notifLabel.setText("map saved successfully");
    }

    public void setUser(User currentUser) {
        if (currentUser == null)
            player.setText("Free player");
        else {
            user = currentUser;
            player.setText(user.getUsername());
        }
    }

    public void mute(ActionEvent event) {
        if (isMuted) {
            PreGame.player.setMute(false);
            isMuted=false;
            mute.setText("Mute");
        } else {
            PreGame.player.setMute(true);
            isMuted=true;
            mute.setText("Unmute");
        }
    }
}
