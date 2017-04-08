package ru.danileyko.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by danil on 19.02.2017.
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
   // @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "role")
    private String role;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_role",
            joinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")}
    )
    private Set<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole(){
        return role;
    }

    public void setName(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
