package lt.verbus.multithreading;

import javafx.application.Platform;
import lt.verbus.controller.stage_controllers.StageController;
import lt.verbus.domain.entity.User;
import lt.verbus.service.QuestionService;
import lt.verbus.service.UserService;
import lt.verbus.util.SessionFactoryUtil;
import org.hibernate.Session;

import java.util.List;

public class InitializationThread extends Thread {
    private Session session;
    private final UserService userService;
    private final int numberOfQuestions;

    public InitializationThread() {
        QuestionService questionService = new QuestionService();
        numberOfQuestions = questionService.findAll().size();
        userService = new UserService();
    }

    @Override
    public void run() {
        waitForSession();
        validateQuestions();
    }

    private void waitForSession() {
        while (session == null) {
            try {
                Thread.sleep(1000);
                session = SessionFactoryUtil.getSession();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void validateQuestions() {
        try {
            List<User> users = userService.findAll();
            if (users.get(0).getAnswers().size() != numberOfQuestions) {
                Platform.runLater(() -> {
                    StageController.popUpQuestionRepositoryException();
                });
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

}

