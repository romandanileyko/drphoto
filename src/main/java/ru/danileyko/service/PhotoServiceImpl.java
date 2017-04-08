package ru.danileyko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danileyko.dao.PhotoDAO;
import ru.danileyko.dao.UserDAO;
import ru.danileyko.model.Photo;
import ru.danileyko.model.User;

import java.util.List;

/**
 * Created by danil on 11.03.2017.
 */
@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PhotoDAO photoDAO;
    @Override
    public void save(Photo photo) {
        photoDAO.save(photo);
    }

    @Override
    public List<Photo> getPhoto(User user) {
        return photoDAO.getPhoto(user);
    }

    @Override
    public List<Photo> getAllPhoto() {
        return photoDAO.getAllPhoto();
    }

    @Override
    public void deletePhotoById(int id) {
         photoDAO.delete(id);
    }
}
