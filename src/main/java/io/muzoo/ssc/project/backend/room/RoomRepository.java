package io.muzoo.ssc.project.backend.room;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    @Override
    Iterable<Room> findAll();
}
