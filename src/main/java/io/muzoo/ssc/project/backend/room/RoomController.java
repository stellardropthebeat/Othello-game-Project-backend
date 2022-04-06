package io.muzoo.ssc.project.backend.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RoomController {

    @Autowired
    RoomRepository roomRepository;

    @PostMapping("/api/post-room")
    public RoomResponseDTO getPlayers(@RequestBody Map<String, Object> payload){
        Long roomId = Long.parseLong(String.valueOf(payload.get("roomId")));
        String player1 = null;
        String player2 = null;
        if(roomRepository.findById(roomId).isPresent()){
            Room room = roomRepository.findById(roomId).get();
            player1 = room.getPlayer1();
            player2 = room.getPlayer2();
        }
        return RoomResponseDTO.builder().player1(player1).player2(player2).build();
    }

    @PostMapping("/api/join-room")
    public RoomResponseDTO getFirstAvailableRoom(@RequestBody Map<String,Object> payload) {
        for (Room room : roomRepository.findAll()) {
            if ((room.getPlayer1()!=null) && (room.getPlayer2()==null)){
                room.setPlayer2((String) payload.get("username"));
                roomRepository.save(room);
                return RoomResponseDTO.builder().availableRoom(room.getId()).build();
            }
        }
        //add room and create room
        Room newRoom = new Room();
        newRoom.setPlayer1((String) payload.get("username"));
        roomRepository.save(newRoom);
        return RoomResponseDTO.builder().availableRoom(newRoom.getId()).build();
    }

    @PostMapping("/api/leave-room")
    public RoomResponseDTO leaveRoom(@RequestBody Map<String,Object> payload){
        if (roomRepository.findById(Long.parseLong(String.valueOf(payload.get("roomId")))).isPresent()) {
            Room leaveRoom = roomRepository.findById(Long.parseLong(String.valueOf(payload.get("roomId")))).get();
            String username = (String) payload.get("username");
            if(leaveRoom.getPlayer1().equals(username)){
                roomRepository.deleteById(leaveRoom.getId());
                return RoomResponseDTO.builder().success(true).hostLeft(true).player1(null).player2(null).build();
            }
            // else if (leaveRoom.getPlayer2().equals(username)){
            //    leaveRoom.setPlayer2(null);
            //    roomRepository.save(leaveRoom);
            //    return RoomResponseDTO.builder().success(true).hostLeft(false).message("Player2 has left the room").build();
            //}
        }
        return null;
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