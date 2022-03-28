package io.muzoo.ssc.project.backend.game.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tbl_board_record")
public class BoardRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int turn;

    private long roomId;

    @ElementCollection
    private List<String> boardRecord;

}
