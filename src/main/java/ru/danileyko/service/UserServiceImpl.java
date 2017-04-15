package ru.danileyko.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danileyko.controller.IndexController;
import ru.danileyko.dao.RoleDAO;
import ru.danileyko.dao.UserDAO;
import ru.danileyko.model.Role;
import ru.danileyko.model.User;

import java.util.List;

/**
 * Created by danil on 24.02.2017.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    static Log log = LogFactory.getLog(UserServiceImpl.class.getName());

    @Override
    public User getUser(String login) {
        return userDAO.getUser(login);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.userList();
    }

    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleDAO.getRole(1));//id=1 for ROLE_USER
        user.setEnabled(true);
        userDAO.save(user);
    }

}
