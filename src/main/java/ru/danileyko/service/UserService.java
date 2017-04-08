package ru.danileyko.service;

import ru.danileyko.model.User;

/**
 * Created by danil on 24.02.2017.
 */
public interface UserService {
    public User getUser(String login);
    public void save(User user);
}
