package lt.verbus.controller.fxml_controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lt.verbus.domain.model.Question;

import java.net.URL;
import java.util.ResourceBundle;

public class RemindQuestionsController implements Initializable {

    @FXML
    private Text txtQuestion;

    public void initData(Question question) {
        System.out.println(question.getText());
        txtQuestion.setText(question.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void btOkClicked(){
        Stage stage  = (Stage) txtQuestion.getScene().getWindow();
        stage.close();
    }
}
