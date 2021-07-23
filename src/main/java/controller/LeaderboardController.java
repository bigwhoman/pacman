package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {
    public Button back;
    private ArrayList<Label> scores = new ArrayList<>();
    private ArrayList<Label> ranks = new ArrayList<>();
    private ArrayList<Label> usernames = new ArrayList<>();
    @FXML
    private Label user1;

    @FXML
    private Label user2;

    @FXML
    private Label user3;

    @FXML
    private Label user4;

    @FXML
    private Label user5;

    @FXML
    private Label user6;

    @FXML
    private Label user7;

    @FXML
    private Label user8;

    @FXML
    private Label user9;

    @FXML
    private Label user10;

    @FXML
    private Label score1;

    @FXML
    private Label score2;

    @FXML
    private Label score3;

    @FXML
    private Label score4;

    @FXML
    private Label score5;

    @FXML
    private Label score6;

    @FXML
    private Label score7;

    @FXML
    private Label score8;

    @FXML
    private Label score9;

    @FXML
    private Label score10;

    @FXML
    private Label rank1;

    @FXML
    private Label rank2;

    @FXML
    private Label rank3;

    @FXML
    private Label rank4;

    @FXML
    private Label rank5;

    @FXML
    private Label rank6;

    @FXML
    private Label rank7;

    @FXML
    private Label rank8;

    @FXML
    private Label rank9;

    @FXML
    private Label rank10;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelMaker();
        ArrayList<User> allUsers = User.getAllUsers();
        Collections.sort(allUsers);
        setup(allUsers);
    }

    private void labelMaker() {
        addLabels(scores, score1, score2, score3, score4, score5, scores
                , score6, score7, score8, score9, score10, ranks, rank1, rank2, rank3, rank4, rank5);
        addLabels(ranks, rank6, rank7, rank8, rank9, rank10, usernames
                , user1, user2, user3, user4, user5, usernames, user6, user7, user8, user9, user10);
    }

    private void addLabels(ArrayList<Label> ranks, Label rank6, Label rank7, Label rank8,
                           Label rank9, Label rank10, ArrayList<Label> usernames, Label user1,
                           Label user2, Label user3, Label user4, Label user5, ArrayList<Label> usernames2,
                           Label user6, Label user7, Label user8, Label user9, Label user10) {
        ranks.add(rank6);
        ranks.add(rank7);
        ranks.add(rank8);
        ranks.add(rank9);
        ranks.add(rank10);
        usernames.add(user1);
        usernames.add(user2);
        usernames.add(user3);
        usernames.add(user4);
        usernames.add(user5);
        usernames2.add(user6);
        usernames2.add(user7);
        usernames2.add(user8);
        usernames2.add(user9);
        usernames2.add(user10);
    }

    private void setup(ArrayList<User> allUsers) {

        int prevScore = allUsers.get(0).getScore();
        int rank = 1;
        if (allUsers.size() >= 10)
            for (int i = 0; i < 10; i++) {
                usernames.get(i).setText(allUsers.get(i).getUsername());
                scores.get(i).setText("" + allUsers.get(i).getScore());
                ranks.get(i).setText("" + rank);
                if (i != 9 && prevScore != allUsers.get(i + 1).getScore()) {
                    rank=i+2;
                    prevScore = allUsers.get(i + 1).getScore();
                }
                if (i == 9)
                    ranks.get(9).setText(rank + "");
            }
        else
            for (int i = 0; i < allUsers.size(); i++) {
                usernames.get(i).setText(allUsers.get(i).getUsername());
                scores.get(i).setText("" + allUsers.get(i).getScore());
                ranks.get(i).setText("" + rank);
                if (i < allUsers.size()-1 && prevScore != allUsers.get(i + 1).getScore()) {
                    rank=i+2;
                }
                if (i < allUsers.size()-1)
                    prevScore = allUsers.get(i + 1).getScore();
            }
    }

    public void toMain(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage window = (Stage) back.getScene().getWindow();
        window.setScene(new Scene(parent));
    }
}
