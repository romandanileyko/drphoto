package ru.danileyko.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by danil on 09.04.2017.
 */
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
   @OneToMany(mappedBy = "category_id")
    private List<Photo> photo_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(List<Photo> photo_id) {
        this.photo_id = photo_id;
    }
}
