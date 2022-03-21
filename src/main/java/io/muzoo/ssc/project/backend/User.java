package io.muzoo.ssc.project.backend;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_user")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;
}
