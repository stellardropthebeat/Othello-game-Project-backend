package io.muzoo.ssc.project.backend.socket.game;

import io.muzoo.ssc.project.backend.game.CalculateBoard;
import net.minidev.json.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class GameSocketController {

    @MessageMapping("/board")
    @SendTo("/topic/play")
    public JSONObject play(Map<String, Object> message) throws Exception {
        List<String> board = (List<String>) message.get("board");

        boolean isBlack = (boolean)message.get("isBlack");

        String color = getColor(isBlack);

        List<Integer> toFlip = (List<Integer>) message.get("toFlip");
        int n = board.size();
        List<String> retBoard = new ArrayList<>();
        for (int i=0; i<n; i++) {
            if (toFlip.contains(i)) {
                retBoard.add(color);
            } else {
                retBoard.add(board.get(i));
            }
        }

        isBlack = !isBlack;
        color = getColor(isBlack);
        CalculateBoard calculator = new CalculateBoard(color);
        Map<Integer, List<Integer>> possibleMoves = calculator.getPossibleMoves(retBoard);

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