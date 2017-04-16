package ru.danileyko.service;

import ru.danileyko.model.User;

import java.util.List;

/**
 * Created by danil on 24.02.2017.
 */
public interface UserService {
    public User getUser(String login);
    public List<User> getAllUsers();
    public void save(User user);
    public void delete(int id);
    public void userLock(int id);
    public void userUnLock(int id);
}
