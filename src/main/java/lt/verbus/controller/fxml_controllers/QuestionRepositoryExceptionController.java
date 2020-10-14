package lt.verbus.controller.fxml_controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import lt.verbus.service.UserService;
import lt.verbus.util.UserFactoryUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionRepositoryExceptionController implements Initializable {

    @FXML
    private Button btDeleteStatistics;

    @FXML
    private Button btExit;

    @FXML
    private CheckBox cbApproveDelete;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!cbApproveDelete.isSelected()) {
            btDeleteStatistics.setDisable(true);
        }
    }

    public void btDeleteStatisticsClicked() {
        UserService userService = new UserService();
        UserFactoryUtil factory = new UserFactoryUtil();

        userService.deleteAll();
        userService.save(factory.buildFakeUser());

        Stage stage  = (Stage) btDeleteStatistics.getScene().getWindow();
        stage.close();
    }

    public void btExitClicked() {
        System.exit(1);
    }


    public void cbApproveDeleteClicked() {
        if (!cbApproveDelete.isSelected()) {
            btDeleteStatistics.setDisable(true);
        }
        if (cbApproveDelete.isSelected()) {
            btDeleteStatistics.setDisable(false);
        }
    }

}
