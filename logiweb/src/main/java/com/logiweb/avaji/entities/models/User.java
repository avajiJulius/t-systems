package com.logiweb.avaji.entities.models;

import com.logiweb.avaji.entities.enums.Role;
import com.logiweb.avaji.entities.models.utils.WorkDetails;
import com.logiweb.avaji.entities.models.utils.WorkShift;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries(value = {
        @NamedQuery(name = "User.findUserByEmail",
        query = "select u from User u where u.email = :email")
})
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

}
