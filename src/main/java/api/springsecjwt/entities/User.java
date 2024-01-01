package api.springsecjwt.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import lombok.Data;


@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firsName;

    @Column(name = "last_name")
    private String lastName;

    private String username;

    private String password;

    @Column(name = "role_type")
    private Role roleType;

    private String email;


    
}
