package ru.danileyko.model;

import javax.persistence.*;

/**
 * Created by danil on 25.04.2017.
 */
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "id_photo")
    private Photo id_photo;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User id_user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Photo getId_photo() {
        return id_photo;
    }

    public void setId_photo(Photo id_photo) {
        this.id_photo = id_photo;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }
}
