package ru.danileyko.dao;

import ru.danileyko.model.Photo;
import ru.danileyko.model.User;

import java.util.List;

/**
 * Created by danil on 11.03.2017.
 */
public interface PhotoDAO {
    public void save(Photo photo);
    public void delete(int id);
    public List<Photo> getPhoto(User user);
    public List<Photo> getAllPhoto();
}
