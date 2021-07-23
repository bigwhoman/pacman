package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Cell;
import model.User;
import java.io.IOException;

public class PreGame {
    public static MediaPlayer player;
    private static Cell[][] map;

    private int pacmanLives = 2;
    private User currentUser;


    public BorderPane pane;
    public Label choose;
    public Button increaseLives;
    public Button decrease;
    public Label lives;
    public TextField numberLabel;
    public Label notifLabel;
    public Button start;
    public Button back;

    public static Cell[][] getMap() {
        return map;
    }
    public static void clearMap(){
        map=null;
    }


    public void back(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage window = (Stage) back.getScene().getWindow();
        window.setScene(new Scene(parent));
    }

    public void start(ActionEvent event) throws IOException {
        if (currentUser != null)
            if (!numberLabel.getText().isEmpty())
                if (currentUser.getMaps().size() >= Integer.parseInt(numberLabel.getText())
                        &&Integer.parseInt(numberLabel.getText())>=1)
                    if (currentUser.getMaps().get(Integer.parseInt(numberLabel.getText()) - 1) != null)
                        map = currentUser.getMaps().get(Integer.parseInt(numberLabel.getText()) - 1);
        loadGameMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/game.fxml"));
        Stage primaryStage = (Stage) start.getScene().getWindow();
        Parent parent = loader.load();
        primaryStage.setScene(new Scene(parent));
        ((GameController) loader.getController()).secondInit(pacmanLives);
        ((GameController) loader.getController()).setUser(currentUser);
        ((GameController) loader.getController()).listenFor();
    }

    public void decreaseLives(ActionEvent event) {
        if (pacmanLives == 2) {
            notifLabel.setText("Reached Min number of lives");
            return;
        }
        pacmanLives--;
        lives.setText(pacmanLives + "");
    }

    public void increaseLives(ActionEvent event) {
        if (pacmanLives == 5) {
            notifLabel.setText("Reached Max number of lives");
            return;
        }
        pacmanLives++;
        lives.setText(pacmanLives + "");
    }

    private void loadGameMusic() {
        WelcomeMenuController.mediaPlayer.stop();
        Media media = new Media(getClass().getResource("/music/zeplin.mp3").toExternalForm());
        player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setVolume(0.5);
        player.play();
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
        if (currentUser == null) {
            numberLabel.setStyle("-fx-background-color: Transparent");
            numberLabel.setPromptText(null);
            choose.setText(null);
            pane.getChildren().remove(numberLabel);
        }
    }

    public void initialize() {
        lives.setText(pacmanLives + "");
    }
}
