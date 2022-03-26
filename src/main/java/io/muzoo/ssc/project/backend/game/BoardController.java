package io.muzoo.ssc.project.backend.game;

import io.muzoo.ssc.project.backend.SimpleResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BoardController {

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
}
