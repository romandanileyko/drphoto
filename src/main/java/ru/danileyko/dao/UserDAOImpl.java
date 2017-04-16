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
    public void userLock(int id) {
        Query lock = openSession().createQuery("update from User set enabled='0' where id=?");
        lock.setParameter(0,id);
        lock.executeUpdate();
    }

    @Override
    public void userUnLock(int id) {
        Query unLock = openSession().createQuery("update from User set enabled='1' where id=?");
        unLock.setParameter(0,id);
        unLock.executeUpdate();
    }

    @Override
    public void delete(int id) {
        Query deleteQuery = openSession().createQuery("delete from User where id=?");
        deleteQuery.setParameter(0,id);
        deleteQuery.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> userList() {
        List<User> allUsers = new ArrayList<>();
        Query query = openSession().createQuery("from User ");
        allUsers = query.list();
        if (allUsers.size()>0)
            return allUsers;
        else
            return null;
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
