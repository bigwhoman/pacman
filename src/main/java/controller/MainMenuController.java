package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private static User currentUser;


    public Button increaseLives;
    public Button decrease;
    public Label lives;
    public TextField numberLabel;
    public Label notifLabel;
    public Button start;
    public Button back;


    @FXML
    private Button play;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button leaderboard;

    @FXML
    private Button deleteAcc;

    @FXML
    private Button changePassword;

    @FXML
    private Button logout;

    public BorderPane pane;

    public void setCurrentUser(User user) {
        currentUser = user;
    }


    @FXML
    void changePassword(ActionEvent event) {
        if (usernameLabel.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "you are not logged in as a user", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Enter new password");
        textInputDialog.showAndWait();
        String newPassword = textInputDialog.getEditor().getText();
        if (!newPassword.matches("\\w+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Operation failed");
            alert.setContentText("invalid password format");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "are you sure?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println(currentUser.getUsername());
            currentUser.setPassword(newPassword);
            Alert confirmedAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmedAlert.setTitle("Operation successful");
            confirmedAlert.setContentText("password changed successfully");
            confirmedAlert.showAndWait();
        }
    }

    @FXML
    void deleteAccount(ActionEvent event) throws IOException {
        if (usernameLabel.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "you are not logged in as a user", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "do you want to delete your account?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            User.deleteUser(Datebase.getInstance().getUser());
            currentUser = null;
            Alert deletionConfirmedAlert = new Alert(Alert.AlertType.INFORMATION);
            deletionConfirmedAlert.setTitle("Deleted user");
            deletionConfirmedAlert.setContentText("User deleted,now redirecting to Welcome Menu");
            deletionConfirmedAlert.showAndWait();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/WelcomeMenu.fxml"));
            Stage window = (Stage) deleteAcc.getScene().getWindow();
            window.setScene(new Scene(parent));
        }

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        currentUser = null;
        Parent parent = FXMLLoader.load(getClass().getResource("/view/WelcomeMenu.fxml"));
        Stage window = (Stage) deleteAcc.getScene().getWindow();
        window.setScene(new Scene(parent));
    }

    @FXML
    void play(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/preGame.fxml"));
        Stage primaryStage = (Stage) play.getScene().getWindow();
        Parent parent = loader.load();
        primaryStage.setScene(new Scene(parent));
        ((PreGame) loader.getController()).setUser(currentUser);
    }

    @FXML
    void showLeaderBoard(ActionEvent event) throws IOException {
        if(User.getAllUsers().size()==0){
            Alert alert = new Alert(AlertType.ERROR,"no players exist",ButtonType.OK);
            alert.setHeaderText("Cant show board");
            alert.showAndWait();
            return;
        }
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Leaderboard.fxml"));
        Stage window = (Stage) deleteAcc.getScene().getWindow();
        window.setScene(new Scene(parent));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (currentUser == null) {
            logout.setText("back");
            return;
        }
        usernameLabel.setText(Datebase.getInstance().getUser().getUsername());
    }

}
