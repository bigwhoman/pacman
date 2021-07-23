package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;


public class RegisterMenuController {
    @FXML
    private TextField enterUsernameField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private PasswordField retypePasswordField;
    @FXML
    private Button registerButton;
    @FXML
    private Button backButton;
    @FXML
    private Label notifField;
    @FXML
    private Label notifFieldGreen;

    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/WelcomeMenu.fxml"));
        Stage window = (Stage) backButton.getScene().getWindow();
        window.setScene(new Scene(root, 774, 584));
    }

    public void register(ActionEvent event) throws IOException {
        if (enterUsernameField.getText().isEmpty()
                || enterPasswordField.getText().isEmpty()
                || retypePasswordField.getText().isEmpty()) {
            notifField.setText("Fill required fields!");
            return;
        }
        if (!enterUsernameField.getText().matches("\\w+")) {
            notifField.setText("invalid username format");
            return;
        }
        if (!enterPasswordField.getText().matches("\\w+")) {
            notifField.setText("invalid password format");
            return;
        }
        if (!retypePasswordField.getText().equals(enterPasswordField.getText())) {
            notifField.setText("passwords are not the same");
            return;
        }
        if (User.getUserByUsername(enterUsernameField.getText()) != null) {
            notifField.setText("this user already exists");
            return;
        }
        new User(enterUsernameField.getText(), enterPasswordField.getText());
        notifField.setText("");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register");
        alert.setContentText("User created successfully");
        alert.showAndWait();
        Parent root = FXMLLoader.load(getClass().getResource("/view/WelcomeMenu.fxml"));
        Stage window = (Stage) backButton.getScene().getWindow();
        window.setScene(new Scene(root, 774, 584));
    }
}
