package lt.verbus;

import javafx.application.Application;
import javafx.stage.Stage;
import lt.verbus.controller.stage_controllers.StageController;
import lt.verbus.multithreading.InitializationThread;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        StageController.setPrimaryStage(stage);
        StageController.loadWelcomeScreen();
        StageController.getPrimaryStage().show();

        //Opens session and validates repositories in the background
        new InitializationThread().start();
    }
}

