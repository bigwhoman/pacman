package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    public Controller2 controller2;
    Stage thisStage;
    @FXML
    public Circle circle;
    public void start() {
        Scene scene = circle.getScene();
        scene.setOnKeyPressed(event   -> {
            switch (event.getCode()) {
                case LEFT:
                    circle.translateXProperty().set(circle.getTranslateX() - 20);
                    break;
                case RIGHT:
                    circle.translateXProperty().set(circle.getTranslateX() + 20);
                    break;
                case DOWN:
                    circle.translateYProperty().set(circle.getTranslateY() + 20);
                    break;
                case UP:
                    circle.translateYProperty().set(circle.getTranslateY() - 20);
                    break;
                case N:
                    try {
                        nextPage();
                    } catch (IOException ignored) {

                    }
                    break;

                case SPACE:
                    break;
            }
        });
    }


    private void nextPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/2.fxml"));
        Parent parent = loader.load();
        ////////////// initialize///////////////
        Stage stage = (Stage) circle.getScene().getWindow();
        stage.setScene(new Scene(parent));
        stage.show();
        ((Controller2)loader.getController()).start();
    }


    public Controller2 getController2() {
        return controller2;
    }
}
