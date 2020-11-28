package lt.verbus.util;

import com.github.javafaker.Faker;
import lt.verbus.domain.entity.Answer;
import lt.verbus.domain.entity.User;
import lt.verbus.service.QuestionService;

public class UserFactoryUtil {
    public User buildFakeUser() {
        Faker faker = new Faker();
        User user = new User();
        user.setName(faker.name().fullName());
        user.setCity(faker.country().capital());
        user.setCountry(faker.country().name());

        QuestionService questionService = new QuestionService();

        questionService.findAll().forEach(question -> {
            Answer answer = new Answer();
            answer.setUser(user);
            answer.setQuestionIndex(question.getId());
            answer.setAnswer(Integer.parseInt(question.getCorrectAnswer()));
            user.getAnswers().add(answer);
        });
        return user;
    }
}
