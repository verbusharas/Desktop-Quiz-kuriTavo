package lt.verbus.controller.custom_panels;

import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lt.verbus.controller.stage_controllers.StageController;
import lt.verbus.domain.entity.Answer;
import lt.verbus.domain.model.Question;
import lt.verbus.service.QuestionService;
import lt.verbus.service.StatisticsService;
import lt.verbus.util.StatsTextBuilder;

public class AnswerStatsPanel extends HBox {

    private final Answer userAnswer;

    private QuestionService questionService;
    private StatisticsService statisticsService;

    private Text txtQuestionNumber;
    private Text txtUserAnswer;
    private Text txtCorrectAnswer;
    private TextFlow txtStatisticsAgainstCorrect;
    private TextFlow txtStatisticsAgainstSingleAvg;
    private Hyperlink btRemindQuestion;
    private Question question;

    public AnswerStatsPanel(Answer userAnswer) {
        this.userAnswer = userAnswer;
        injectServices();
        injectNodes();
        setNodeValues();
        formatPanel();
        populatePanelWithNodes();
    }

    private void formatPanel() {
        setMinHeight(50);
        setSpacing(10);
        setPadding(new Insets(10,10,10,10));
        setStyle("-fx-background-color: transparent");
        txtQuestionNumber.setStyle("-fx-fill: white; -fx-font-weight: bold");
        txtUserAnswer.setStyle("-fx-fill: white");
        txtCorrectAnswer.setStyle("-fx-fill: white");
    }

    private void injectServices() {
        questionService = new QuestionService();
        statisticsService = new StatisticsService();
    }

    private void injectNodes() {
        txtQuestionNumber = new Text();
        btRemindQuestion = new Hyperlink();
        txtUserAnswer = new Text();
        txtCorrectAnswer = new Text();
    }

    private void setNodeValues() {
        int questionIndex = userAnswer.getQuestionIndex();
        question = questionService.getQuestionByIndex(questionIndex);

        txtQuestionNumber.setText("KLAUSIME NR. " + (questionIndex + 1));
        btRemindQuestion.setText("Priminti klausimą?");
        btRemindQuestion.setOnAction(e -> btRemindQuestionClicked());

        txtUserAnswer.setText("Pasirinkai: " + userAnswer.getAnswer());
        txtCorrectAnswer.setText("Mes prognozuojame: " + question.getCorrectAnswer());

        double ratioAgainstCorrect = statisticsService
                .compareUserAnswerToCorrectAnswer(userAnswer);
        double ratioAgainstSingleAvg = statisticsService
                .compareUserAnswerToSingleAvg(userAnswer);

        StatsTextBuilder builder = new StatsTextBuilder();

        builder.setRatio(ratioAgainstCorrect);
        builder.setSuffix("prognozuojama.");
        txtStatisticsAgainstCorrect = builder.build();

        builder.setRatio(ratioAgainstSingleAvg);
        builder.setSuffix("dauguma vartotojų.");
        txtStatisticsAgainstSingleAvg = builder.build();
    }

    private void populatePanelWithNodes() {
        VBox questionColumn = new VBox();
        VBox answerColumn = new VBox();
        VBox statsColumn = new VBox();

        questionColumn.getChildren().addAll(txtQuestionNumber, btRemindQuestion);
        answerColumn.getChildren().addAll(txtUserAnswer, txtCorrectAnswer);
        statsColumn.getChildren().addAll(txtStatisticsAgainstCorrect, txtStatisticsAgainstSingleAvg);

        getChildren().addAll(questionColumn, answerColumn, statsColumn);
    }

    private void btRemindQuestionClicked() {
        StageController.popUpQuestionReminder(question);
    }

}
