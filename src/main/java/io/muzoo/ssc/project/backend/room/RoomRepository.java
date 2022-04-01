package io.muzoo.ssc.project.backend.room;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    @Override
    Iterable<Room> findAll();

    @Override
    Optional<Room> findById(Long id);
}
