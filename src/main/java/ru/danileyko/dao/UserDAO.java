package ru.danileyko.dao;

import ru.danileyko.model.User;

import java.util.List;

/**
 * Created by danil on 23.02.2017.
 */
public interface UserDAO {
    public User getUser(String login);
    public List<User> userList();
    public void save(User user);
    public void delete(int id);
    public void userLock(int id);
    public void userUnLock(int id);
    public User getUserById(int id);
}
