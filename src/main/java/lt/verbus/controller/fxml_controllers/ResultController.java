package lt.verbus.controller.fxml_controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lt.verbus.controller.custom_panels.AnswerStatsPanel;
import lt.verbus.controller.custom_panels.OverallStatsPanel;
import lt.verbus.controller.stage_controllers.StageController;
import lt.verbus.domain.entity.Answer;
import lt.verbus.domain.entity.User;
import lt.verbus.service.CurrentUserService;
import lt.verbus.service.StatisticsService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultController implements Initializable {

    @FXML
    private VBox vboxFeed;

    @FXML
    private ScrollPane scrollFeed;

    @FXML
    private VBox vboxFeedTitle;

    @FXML
    private Text txtUserInfo;

    @FXML
    private Text txtUserPortrait;

    private CurrentUserService currentUserService;
    private StatisticsService statisticsService;

    private User user;
    private List<Answer> userAnswers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        injectServices();
        formatVisualElements();
        showResults();
        saveResults();
    }

    private void formatVisualElements() {
        user = currentUserService.getUser();
        userAnswers = user.getAnswers();
        txtUserInfo.setText(user.toString());
        scrollFeed.setContent(vboxFeed);
        scrollFeed.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollFeed.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vboxFeed.setStyle("-fx-background-color: #5e5363");
        vboxFeedTitle.setStyle("-fx-background-color: transparent");
        scrollFeed.setStyle("-fx-background-color: #5e5363");
    }

    private void injectServices() {
        currentUserService = CurrentUserService.getInstance();
        statisticsService = new StatisticsService();
    }

    private void showResults() {
        vboxFeedTitle.getChildren().add(new OverallStatsPanel());
        userAnswers.forEach(answer -> vboxFeed.getChildren().add(new AnswerStatsPanel(answer)));
        txtUserPortrait.setText(statisticsService.calculatePersonType(user).getLithuanianValue());
    }

    private void saveResults() {
        currentUserService.saveCurrentUser();
    }

    public void btNewClicked(){
        StageController.loadWelcomeScreen();
    }

    public void btExitClicked(){
        System.exit(1);
    }


}