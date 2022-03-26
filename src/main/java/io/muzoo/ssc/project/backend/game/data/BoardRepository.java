package io.muzoo.ssc.project.backend.game.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends CrudRepository<BoardRecord, Long> {

    BoardRecord findBoardRecordById(long id);
    BoardRecord deleteBoardRecordById(long id);
}
