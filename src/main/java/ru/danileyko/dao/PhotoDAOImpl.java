package ru.danileyko.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.danileyko.model.Photo;
import ru.danileyko.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by danil on 11.03.2017.
 */
@Repository
public class PhotoDAOImpl implements PhotoDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void save(Photo photo) {
        openSession().save(photo);
    }
    private Session openSession(){
        return sessionFactory.getCurrentSession();
    }

    //Get logget user's photo.
    @Override
    @SuppressWarnings("unchecked")
    public List<Photo> getPhoto(User user) {
        List<Photo> photoList = new ArrayList<Photo>();
        Query query = openSession().createQuery("from Photo where user_id=?");
        query.setParameter(0,user);
        photoList=query.list();
        if(photoList.size()>0)
            return photoList;
        else
            return null;
    }

    @Override
    public Photo getPhotoById(int id) {
        List<Photo> onePhoto = new ArrayList<>();
        Query query = openSession().createQuery("from Photo where id=?");
        query.setParameter(0,id);
         onePhoto=query.list();
         if(onePhoto.size()>0)
             return onePhoto.get(0);
         else
            return null;
    }

    //Get All photo
    @Override
    @SuppressWarnings("unchecked")
    public List<Photo> getAllPhoto() {
        List<Photo> allPhotos = new ArrayList<Photo>();
        Query queryAllPhoto = openSession().createQuery("from Photo");
        queryAllPhoto.setMaxResults(10);
        allPhotos=queryAllPhoto.list();
        if(allPhotos.size()>0)
            return allPhotos;
        else
            return null;
    }

    @Override
    public void delete(int id) {
        Query deleteQuery = openSession().createQuery("delete from Photo where id=?");
        deleteQuery.setParameter(0,id);
        deleteQuery.executeUpdate();
    }
}
