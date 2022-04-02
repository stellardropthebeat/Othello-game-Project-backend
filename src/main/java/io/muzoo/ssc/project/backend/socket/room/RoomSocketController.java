package io.muzoo.ssc.project.backend.socket.room;

import io.muzoo.ssc.project.backend.room.Room;
import io.muzoo.ssc.project.backend.room.RoomRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class RoomSocketController {

    @Autowired
    RoomRepository roomRepository;

    @MessageMapping("/room/{RoomId}")
    @SendTo("/topic/wait/{RoomId}")
    public JSONObject wait(@DestinationVariable("RoomId") String RoomId, Map<String,Object> payload) throws Exception{

        long roomId = Long.parseLong(String.valueOf(payload.get("roomId")));
        Room room = roomRepository.findById(roomId).get();
        String player1 = room.getPlayer1();
        String player2 = room.getPlayer2();

        JSONObject object = new JSONObject();
        object.put("player1", player1);
        object.put("player2", player2);
        System.out.println(player1);
        System.out.println(player2);

        return object;
    }
}
