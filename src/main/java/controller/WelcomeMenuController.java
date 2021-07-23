package controller;

import com.gilecode.yagson.YaGson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.User;
import java.io.FileWriter;
import java.io.IOException;

public class WelcomeMenuController {
    @FXML
    private Button exitButton;
    @FXML
    private Button enterButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private ImageView pacmanGif1;

    public static MediaPlayer mediaPlayer;

    public void exit(ActionEvent event) throws IOException {
        updateJson();
        ((Stage) exitButton.getScene().getWindow()).close();
    }

    private void updateJson() {
        try {
            FileWriter writer = new FileWriter("json.json");
            writer.write(new YaGson().toJson(User.getAllUsers()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterMenu.fxml"));
        Stage window = (Stage) registerButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void login(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginMenu.fxml"));
        Stage window = (Stage) registerButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void enter(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage window = (Stage) enterButton.getScene().getWindow();
        window.setScene(new Scene(parent));
    }

    public void music() {
        Media media = new Media(getClass().getResource("/music/cc.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
    }


}
