package lt.verbus.service;

import lt.verbus.dao.UserDao;
import lt.verbus.domain.entity.User;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(){
        userDao = new UserDao();
    }

    public List<User> findAll(){
        return userDao.findAll();
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void deleteAll(){
        userDao.deleteAll();
    }

}
