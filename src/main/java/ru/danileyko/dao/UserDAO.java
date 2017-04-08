package ru.danileyko.dao;

import ru.danileyko.model.User;

/**
 * Created by danil on 23.02.2017.
 */
public interface UserDAO {
    public User getUser(String login);
    public void save(User user);
}
