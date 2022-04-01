package io.muzoo.ssc.project.backend.room;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_room")
public class Room {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String player1;

    private String player2;
}

