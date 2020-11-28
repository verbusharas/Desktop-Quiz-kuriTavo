package lt.verbus.controller.custom_panels;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;
import lt.verbus.service.CurrentUserService;
import lt.verbus.service.StatisticsService;
import lt.verbus.service.UserService;
import lt.verbus.util.StatsTextBuilder;

public class OverallStatsPanel extends HBox {

    private StatisticsService statisticsService;
    private CurrentUserService currentUserService;
    private UserService userService;

    private TextFlow txtStatisticsAgainstOverallAvg;

    public OverallStatsPanel() {
        injectServices();
        setNodeValues();
        formatPanel();
        populatePanelWithNodes();
    }

    private void injectServices() {
        statisticsService = new StatisticsService();
        currentUserService = CurrentUserService.getInstance();
        userService = new UserService();
    }

    private void setNodeValues() {
        double ratioAgainstOverAllAvg = statisticsService
                .compareUserAvgToOverallAvg(currentUserService.getUser());

        int numberOfPreviousUsers = userService.findAll().size();
        StatsTextBuilder builder = new StatsTextBuilder();
        builder.setPrefix("Tavo atsakymų vidurkis rodo, kad rinkaisi ");
        builder.setRatio(ratioAgainstOverAllAvg);
        builder.setSuffix("kiti apklausos dalyviai ( " + numberOfPreviousUsers + " )");

        txtStatisticsAgainstOverallAvg = builder.build();
    }

    private void formatPanel() {
        setMinHeight(25);
        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 10));
        setStyle("-fx-background-color: transparent;");
    }

    private void populatePanelWithNodes() {
        getChildren().add(txtStatisticsAgainstOverallAvg);
    }
}
