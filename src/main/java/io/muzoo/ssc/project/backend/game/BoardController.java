package io.muzoo.ssc.project.backend.game;

import io.muzoo.ssc.project.backend.SimpleResponseDTO;
import io.muzoo.ssc.project.backend.game.data.BoardRecord;
import io.muzoo.ssc.project.backend.game.data.BoardRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BoardController {

    @Autowired
    BoardRecordRepository boardRepository;

    @PostMapping("/api/post-board")
    public BoardDTO updateBoard(@RequestBody Map<String, Object> payload) {
        List<String> board = (List<String>) payload.get("board");
        String color;
        if ((boolean) payload.get("isBlack")) {
            color = "b";
        } else {
            color = "w";
        }
        CalculateBoard calculator = new CalculateBoard(color);
        Map<Integer, List<Integer>> possibleMoves = calculator.getPossibleMoves(board);

        return BoardDTO.builder().possibleMoves(possibleMoves).build();
    }

    @PostMapping("/api/add-board-record")
    public SimpleResponseDTO addNewBoardRecord(@RequestBody Map<String, Object> payload) {
//        System.out.println(payload);
        BoardRecord record = new BoardRecord();
        record.setRoomId(Long.parseLong(String.valueOf(payload.get("roomId"))));
        record.setTurn((Integer) payload.get("turn"));
        record.setBoardRecord((List<String>) payload.get("boardRecord"));
//        System.out.println(record);
        boardRepository.save(record);
        return SimpleResponseDTO.builder().success(true).message("new board record added").build();
    }


    @PostMapping("/api/all-record")
    public void printBoards(@RequestBody Map<String, Object> payload) {
//        boardRepository.deleteAll();
        long roomId = Long.parseLong(String.valueOf(payload.get("roomId")));
        int turn = (Integer) payload.get("turn");

        System.out.println(boardRepository.findBoardRecordByRoomIdAndTurn(roomId, turn));
    }

}
