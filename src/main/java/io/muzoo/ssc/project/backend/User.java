package io.muzoo.ssc.project.backend;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_user")
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    private int score;

    private long latestGame;

    @Override
    public int compareTo(User o) {
        return Integer.compare(getScore(), o.getScore());
    }
}
