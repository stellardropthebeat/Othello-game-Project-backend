package io.muzoo.ssc.project.backend.game;

import io.muzoo.ssc.project.backend.SimpleResponseDTO;
import io.muzoo.ssc.project.backend.game.data.BoardRecord;
import io.muzoo.ssc.project.backend.game.data.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

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
        BoardRecord b = new BoardRecord();
        b.setId(Long.parseLong(String.valueOf(payload.get("id"))));
        b.setTurn((Integer) payload.get("turn"));
        b.setBoard((List<String>) payload.get("board"));
        System.out.println(b);
//        boardRepository.save(b);
        return SimpleResponseDTO.builder().success(true).message("new board record added").build();
    }

    @GetMapping("/api/all-record")
    public void printBoards() {
        String all = boardRepository.findAll().toString();
        System.out.println(all);
    }
}
