package ru.danileyko.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.danileyko.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danil on 09.04.2017.
 */
@Repository
public class CategoryDAOImpl implements CategoryDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Category> getAllCategoty() {
        List<Category> allCategory = new ArrayList<Category>();
        Query getAllCategory = sessionFactory.openSession().createQuery("from Category ");
        allCategory = getAllCategory.list();
        if(allCategory.size()>0)
            return  allCategory;
        else 
            return null;
    }
}
