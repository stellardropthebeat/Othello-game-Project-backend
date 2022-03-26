package io.muzoo.ssc.project.backend.game.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRecordRepository extends CrudRepository<BoardRecord, Long> {

    BoardRecord findBoardRecordById(long id);
    BoardRecord deleteBoardRecordById(long id);

    Iterable<BoardRecord> findAllByRoomId(long roomId);

    BoardRecord findBoardRecordByRoomIdAndTurn(long roomId, int turn);
}
