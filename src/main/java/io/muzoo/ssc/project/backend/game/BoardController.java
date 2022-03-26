package io.muzoo.ssc.project.backend.game;

import io.muzoo.ssc.project.backend.SimpleResponseDTO;
import io.muzoo.ssc.project.backend.game.data.BoardRecord;
import io.muzoo.ssc.project.backend.game.data.BoardRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BoardController {

    @Autowired
    BoardRecordRepository boardRepository;

    @PostMapping("/api/post-board")
    public BoardDTO updateBoard(@RequestBody Map<String, Object> payload) {
        List<List<String>> board = (List<List<String>>) payload.get("board");
        String color;
        if ((boolean) payload.get("isBlack")) {
            color = "b";
        } else {
            color = "w";
        }
        CalculateBoard calculator = new CalculateBoard.CalculateBoardBuilder().color(color).board(board).possibleMoves(new HashMap<>()).build();
        Map<List<Integer>, List<List<Integer>>> possibleMoves = calculator.getPossibleMoves();
//        System.out.println(possibleMoves);

        return BoardDTO.builder().possibleMoves(possibleMoves).build();
    }

    @PostMapping("/api/add-board-record")
    public SimpleResponseDTO addNewBoardRecord(@RequestBody Map<String, Object> payload) {
        System.out.println(payload);
        BoardRecord record = new BoardRecord();
        record.setRoomId(Long.parseLong(String.valueOf(payload.get("roomId"))));
        record.setTurn((Integer) payload.get("turn"));
        record.setBoardRecord((List<String>) payload.get("board"));
        System.out.println(record);
        boardRepository.save(record);
        return SimpleResponseDTO.builder().success(true).message("new board record added").build();
    }


//    @GetMapping("/api/all-record")
//    public void printBoards() {
//        boardRepository.findAll().forEach(r -> System.out.println(r.getBoard()));
//    }

}
