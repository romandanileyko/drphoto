package ru.danileyko.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.danileyko.model.Comment;
import ru.danileyko.model.Photo;

import java.util.List;

/**
 * Created by danil on 25.04.2017.
 */
@Repository
public class CommentDAOImpl implements CommentDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> getAllCommentByPhotoId(Photo photo) {
        List<Comment> commentList;
        Query query = sessionFactory.openSession().createQuery("from Comment where id_photo=?");
        query.setParameter(0,photo);
        commentList = query.list();
        if (commentList.size()>0)
            return commentList;
        else
            return null;
    }

    @Override
    public void saveComment(Comment comment) {
        sessionFactory.openSession().save(comment);
    }
}
