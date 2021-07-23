package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{
    private static Main main;
    private Controller controller;
    private Controller2 controller2;
    public static Main getMain() {
        return main;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root=loader.load();
        controller =  loader.getController();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        ((Controller)loader.getController()).start();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public Controller getController() throws IOException {
        return controller;
    }
    public Controller2 getcontroller2() throws IOException {
        return controller2;
    }

    public void setController2(Controller2 controller2) {
        this.controller2 = controller2;
    }

}
