package io.muzoo.ssc.project.backend.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @PostMapping("/api/join-room")
    public RoomResponseDTO getFirstAvailableRoom(@RequestBody Map<String,Object> payload) {
        for (Room room : roomRepository.findAll()) {
            if ((room.getPlayer1()!=null) && (room.getPlayer2()==null)){
                room.setPlayer2((String) payload.get("username"));
                return RoomResponseDTO.builder().availableRoom(room.getId()).build();
            }
        }
        //add room and create room
        Room newRoom = new Room();
        newRoom.setPlayer1((String) payload.get("username"));
        roomRepository.save(newRoom);
        return RoomResponseDTO.builder().availableRoom(newRoom.getId()).build();
    }

    // if username is in player1 column, the user isBlack
    @PostMapping("/api/color")
    public RoomResponseDTO getPlayer1(@RequestBody Map<String, Object> payload) {
        long id = Long.parseLong(String.valueOf(payload.get("roomId")));
        String username = (String) payload.get("username");
        Room room = roomRepository.findById(id).get();
        if (room.getPlayer1().equals(username)) {
            return RoomResponseDTO.builder().success(true).color("b").build();
        } else {
            return RoomResponseDTO.builder().success(true).color("w").build();
        }
    }

}
