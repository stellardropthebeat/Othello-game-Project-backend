package io.muzoo.ssc.project.backend.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class RoomController {

    @Autowired
    RoomRepository roomRepository;

//    @GetMapping("/api/all-room")
//    public RoomResponseDTO getAllRoom() {
//        for (Room room : roomRepository.findAll()) {
//        }
//    }

    // if username is in player1 column, the user isBlack
    @PostMapping("/api/color")
    public boolean getPlayer1(@RequestBody Map<String, Object> payload) {
        long id = Long.parseLong(String.valueOf(payload.get("roomId")));
        String username = (String) payload.get("username");
        Room room = roomRepository.findById(id).get();
        return room.getPlayer1().equals(username);
    }

}
