package io.muzoo.ssc.project.backend.game.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRecordRepository extends CrudRepository<BoardRecord, Long> {

    Iterable<BoardRecord> findAllByRoomId(long roomId);
    BoardRecord findBoardRecordByRoomIdAndTurn(long roomId, int turn);


}
