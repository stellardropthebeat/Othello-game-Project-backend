package io.muzoo.ssc.project.backend.socket.room;

import io.muzoo.ssc.project.backend.room.Room;
import io.muzoo.ssc.project.backend.room.RoomRepository;
import io.muzoo.ssc.project.backend.room.RoomResponseDTO;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
public class RoomSocketController {

    @Autowired
    RoomRepository roomRepository;

    @MessageMapping("/room/{RoomId}")
    @SendTo("/topic/wait/{RoomId}")
    public JSONObject wait(@DestinationVariable("RoomId") String RoomId, Map<String,Object> payload) throws NoSuchElementException {

        Long roomId = Long.parseLong(String.valueOf(payload.get("roomId")));
        String player1 = null;
        String player2 = null;
        if(roomRepository.findById(roomId).isPresent()){
            Room room = roomRepository.findById(roomId).get();
            player1 = room.getPlayer1();
            player2 = room.getPlayer2();
        }

        JSONObject object = new JSONObject();
        object.put("player1", player1);
        object.put("player2", player2);
        System.out.println(player1);
        System.out.println(player2);

        return object;
    }
}
