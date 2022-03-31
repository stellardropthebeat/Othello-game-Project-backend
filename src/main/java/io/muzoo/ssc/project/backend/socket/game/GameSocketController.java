package io.muzoo.ssc.project.backend.socket.game;

import io.muzoo.ssc.project.backend.game.CalculateBoard;
import io.muzoo.ssc.project.backend.game.data.BoardRecord;
import io.muzoo.ssc.project.backend.game.data.BoardRecordRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class GameSocketController {

//    will extract unrelated code that shouldn't be here later
    @Autowired
    BoardRecordRepository boardRepository;

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

    private void addBoardRecord(long roomId, int turn, List<String> board) {
        BoardRecord record = new BoardRecord();
        record.setRoomId(roomId);
        record.setTurn(turn);
        record.setBoardRecord(board);
        boardRepository.save(record);
    }
}