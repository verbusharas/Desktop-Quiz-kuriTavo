package lt.verbus.controller.fxml_controllers;

import com.github.javafaker.Faker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lt.verbus.controller.stage_controllers.StageController;
import lt.verbus.exception.EmptyFieldException;
import lt.verbus.domain.entity.User;
import lt.verbus.service.CurrentUserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    private Label lblException;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfCountry;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblException.setVisible(false);
    }

    public void btStartQuizClicked() throws IOException {
        try {
            validateTextFields();
            loginUser();
            StageController.loadQuizScreen();
        } catch (EmptyFieldException e) {
            lblException.setText(e.getMessage());
            lblException.setVisible(true);
        }
    }

    private void loginUser() {
        User currentUser = new User();
        currentUser.setName(tfName.getText());
        currentUser.setCity(tfCity.getText());
        currentUser.setCountry(tfCountry.getText());
        CurrentUserService currentUserService = CurrentUserService.getInstance();
        currentUserService.setUser(currentUser);
    }

    private void validateTextFields() throws EmptyFieldException {
        if (tfName.getText().length() < 1
                || tfCity.getText().length() < 1
                || tfCountry.getText().length() < 1) {
            throw new EmptyFieldException("Neskubėki. Būtina užpildyti visus laukelius");
        }
    }

    public void btFakeLoginClicked() {
        Faker faker = new Faker();
        tfName.setText(faker.gameOfThrones().character());
        tfCity.setText(faker.gameOfThrones().city());
        tfCountry.setText("Westeros");
    }

}
