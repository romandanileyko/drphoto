package ru.danileyko.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.danileyko.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danil on 24.02.2017.
 */
@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(User user) {
        openSession().save(user);
    }

    private Session openSession(){
        return sessionFactory.getCurrentSession();
    }
    @Override
    @SuppressWarnings("unchecked")
    public User getUser(String login) {
        List<User> userList = new ArrayList<User>();
        Query query = openSession().createQuery("from User where username=?");
        query.setParameter(0, login);
        userList = query.list();
        if (userList.size() > 0)
            return userList.get(0);
        else
            return null;
    }

}
