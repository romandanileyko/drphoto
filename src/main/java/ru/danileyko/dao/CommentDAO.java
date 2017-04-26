package ru.danileyko.dao;

import ru.danileyko.model.Comment;
import ru.danileyko.model.Photo;

import java.util.List;

/**
 * Created by danil on 25.04.2017.
 */
public interface CommentDAO {
    public List<Comment> getAllCommentByPhotoId(Photo photo);
    public void saveComment(Comment comment);
}
