package io.muzoo.ssc.project.backend.socket.game;

import io.muzoo.ssc.project.backend.game.CalculateBoard;
import net.minidev.json.JSONObject;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class GameSocketController {

    @MessageMapping("/board/{RoomId}")
    @SendTo("/topic/play/{RoomId}")
    public JSONObject play(@DestinationVariable("RoomId") String RoomId, Map<String, Object> message) throws Exception {

        List<String> board = (List<String>) message.get("board");
        List<Integer> toFlip = (List<Integer>) message.get("toFlip");
        boolean isBlack = (boolean)message.get("isBlack");
        int turn = (int) message.get("turn") + 1;

        // flipping
        String color = getColor(isBlack);
        int n = board.size();
        List<String> retBoard = new ArrayList<>();
        for (int i=0; i<n; i++) {
            if (toFlip.contains(i)) {
                retBoard.add(color);
            } else {
                retBoard.add(board.get(i));
            }
        }

        // calculate possible moves for next turn
        isBlack = !isBlack;
        color = getColor(isBlack);
        CalculateBoard calculator = new CalculateBoard(color);
        Map<Integer, List<Integer>> possibleMoves = calculator.getPossibleMoves(retBoard);

        // if there is no possible move, the turn will be passes and the other player get to play
        if (possibleMoves.isEmpty()) {
            isBlack = !isBlack;
            color = getColor(isBlack);
            calculator = new CalculateBoard(color);
            possibleMoves = calculator.getPossibleMoves(retBoard);
        }

        int blacks = (int) retBoard.stream().filter(d -> d.equals("b")).count();
        int whites= (int) retBoard.stream().filter(d -> d.equals("w")).count();

        JSONObject o = new JSONObject();
        o.put("board", retBoard);
        o.put("possibleMoves", possibleMoves);
        o.put("blacks", blacks);
        o.put("whites", whites);
        o.put("isBlack", isBlack);
        o.put("turn", turn);

        return  o;
    }

    private String getColor(boolean isBlack) {
        if (isBlack) {
            return "b";
        } else {
            return "w";
        }
    }
}