package lt.verbus.service;

import lt.verbus.domain.entity.Answer;
import lt.verbus.domain.entity.User;
import lt.verbus.dao.UserDao;

public class CurrentUserService {

    private User user;
    private static CurrentUserService currentUserService;
    private final UserDao userDao;

    private CurrentUserService() {
        userDao = new UserDao();
    }

    public static CurrentUserService getInstance() {
        if (currentUserService == null) {
            currentUserService = new CurrentUserService();
        }
        return currentUserService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addAnswer(Answer userAnswer) {
        userAnswer.setUser(user);
        user.getAnswers().add(userAnswer);
    }

    public User getUser() {
        return user;
    }

    public void saveCurrentUser() {
        userDao.save(user);
    }

}
