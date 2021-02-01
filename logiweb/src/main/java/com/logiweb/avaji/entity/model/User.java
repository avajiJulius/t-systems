package com.logiweb.avaji.entity.model;

import com.logiweb.avaji.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "User.findUserByEmail",
query = "select new com.logiweb.avaji.entity.model.User(" +
        "u.id, u.email, u.password, u.enable, u.role) from User u where u.email = :email")
@NamedQuery(name = "User.countUserByEmail",
        query = "select count(1) from User u where u.email = :email")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Version
    @Column(name = "version")
    private int version;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "enable")
    private boolean enable;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(long id, String email, String password, boolean enable, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.enable = enable;
        this.role = role;
    }
}
