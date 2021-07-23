package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.util.Objects;

public class LoginMenuController {
    @FXML
    private TextField enterUsername;
    @FXML
    private PasswordField enterPassword;
    @FXML
    private Button login;
    @FXML
    private Button back;
    @FXML
    private Label notifLabel;

    public void back(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/WelcomeMenu.fxml"));
        Stage window = (Stage) back.getScene().getWindow();
        window.setScene(new Scene(parent, 774, 584));
    }

    public void login(ActionEvent event) throws IOException{
        if (enterUsername.getText().isEmpty() || enterPassword.getText().isEmpty()) {
            notifLabel.setText("fill required fields!");
            return;
        }
        if (!enterUsername.getText().matches("\\w+")) {
            notifLabel.setText("invalid username format");
            return;
        }
        if (!enterPassword.getText().matches("\\w+")) {
            notifLabel.setText("invalid password format");
            return;
        }
        if (User.getUserByUsername(enterUsername.getText()) == null
                || !Objects.requireNonNull(User.getUserByUsername
                (enterUsername.getText())).getPassword().equals(enterPassword.getText())) {
            notifLabel.setText("invalid username or password");
            return;
        }
        Datebase.getInstance().setUser(User.getUserByUsername(enterUsername.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setContentText("Logged in successfully");
        alert.showAndWait();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/view/MainMenu.fxml"
                )
        );
        Pane pane = (Pane) loader.load();
        MainMenuController controller = loader.getController();
        controller.setCurrentUser
                (Objects.requireNonNull(User.getUserByUsername(enterUsername.getText())));
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage window = (Stage) back.getScene().getWindow();
        window.setScene(new Scene(parent, 774, 584));


    }

}
