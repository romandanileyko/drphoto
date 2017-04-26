package ru.danileyko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danileyko.dao.CategoryDAO;
import ru.danileyko.dao.CommentDAO;
import ru.danileyko.model.Comment;
import ru.danileyko.model.Photo;

import java.util.List;

/**
 * Created by danil on 25.04.2017.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDAO commentDAO;
    @Override
    public List<Comment> getAllCommentByPhotoId(Photo id) {
        return commentDAO.getAllCommentByPhotoId(id);
    }

    @Override
    public void saveComment(Comment comment) {
        commentDAO.saveComment(comment);
    }
}
