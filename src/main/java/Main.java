import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import controller.WelcomeMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomeMenu.fxml"));
        Parent root = loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Pacman");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        setupFiles();
        ((WelcomeMenuController)loader.getController()).music();
    }

    private void setupFiles() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("json.json")));
            ArrayList<User> users = new YaGson().fromJson(json,
                    new TypeToken<List<User>>() {
                    }.getType()
            );
            if (users != null)
                for (User user : users) {
                    if (user != null)
                        User.getAllUsers().add(user);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
