package lt.verbus.controller.fxml_controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import lt.verbus.controller.stage_controllers.StageController;
import lt.verbus.domain.entity.Answer;
import lt.verbus.domain.model.Question;
import lt.verbus.service.QuestionService;
import lt.verbus.service.CurrentUserService;
import lt.verbus.util.PropertiesReader;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuizController implements Initializable {

    @FXML
    private Text txtQuestionNumber;

    @FXML
    private Text txtQuestion;

    @FXML
    private Slider yearSlider;

    @FXML
    private Text txtSliderIndicator;

    @FXML
    private Button btNextQuestion;

    private QuestionService questionService;
    private CurrentUserService userService;

    List<Question> questions;
    private int currentQuestionIndex;
    private int totalQuestions;

    private int MIN_YEAR;
    private int MAX_YEAR;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        injectServices();
        questions = questionService.findAll();
        currentQuestionIndex = 0;
        totalQuestions = questions.size();
        formatYearSlider();
        showNextQuestion();
    }

    private void injectServices() {
        questionService = new QuestionService();
        userService = CurrentUserService.getInstance();
    }

    private void formatYearSlider() {
        PropertiesReader properties = new PropertiesReader();
        MIN_YEAR = properties.getMinyear();
        MAX_YEAR = properties.getMaxYear();
        int increment = 1;
        yearSlider.valueProperty().addListener((obs, oldval, newVal) ->
                txtSliderIndicator.setText(String.valueOf(increment * Math.round(newVal.doubleValue() / increment))));
        yearSlider.minProperty().setValue(MIN_YEAR);
        yearSlider.maxProperty().setValue(MAX_YEAR);
        yearSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                return String.valueOf(n.intValue());
            }

            @Override
            public Double fromString(String s) {
                return Double.valueOf(s);
            }
        });
    }

    public void btNextQuestionClicked() {
        setUserAnswer(Integer.parseInt(txtSliderIndicator.getText()));
        boolean hasRemainingQuestions = ++currentQuestionIndex < totalQuestions;
        if (hasRemainingQuestions) {
            showNextQuestion();
        } else {
            btNextQuestion.setDisable(true);
            StageController.loadResultScreen();
        }
    }

    private void setUserAnswer(int userAnswerValue) {
        Answer userAnswer = new Answer();
        userAnswer.setQuestionIndex(currentQuestionIndex);
        userAnswer.setAnswer(userAnswerValue);
        userService.addAnswer(userAnswer);
    }

    private void showNextQuestion() {
        Question question = questions.get(currentQuestionIndex);
        txtQuestion.setText(question.getText());
        txtQuestionNumber.setText(String.format("Klausimas %d iš %d", currentQuestionIndex + 1, totalQuestions));
        yearSlider.setValue(MIN_YEAR);
    }
}
