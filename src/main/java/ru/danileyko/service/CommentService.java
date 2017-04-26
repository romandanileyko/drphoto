package ru.danileyko.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danileyko.model.Comment;
import ru.danileyko.model.Photo;

import java.util.List;

/**
 * Created by danil on 25.04.2017.
 */
public interface CommentService {
    public List<Comment> getAllCommentByPhotoId(Photo id);
    public void saveComment(Comment comment);
}
