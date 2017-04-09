package ru.danileyko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.danileyko.dao.CategoryDAO;
import ru.danileyko.model.Category;

import java.util.List;

/**
 * Created by danil on 09.04.2017.
 */
@Repository
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;
    @Override
    public List<Category> getAllCategory() {
        return categoryDAO.getAllCategoty();
    }
}
