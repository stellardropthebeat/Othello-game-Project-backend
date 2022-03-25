package io.muzoo.ssc.project.backend.game;

import io.muzoo.ssc.project.backend.SimpleResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BoardPost {

    @PostMapping("/api/post-board")
    public SimpleResponseDTO updateBoard(@RequestBody List<List<String>> payload) {
        System.out.println(payload);
        CalculateBoard calculator = new CalculateBoard.CalculateBoardBuilder().board(payload).build();
        Map<Integer, List<List<Integer>>> possibilities = calculator.AllPossibilities();
        return SimpleResponseDTO
                .builder()
                .success(true)
                .message("Board is updated to backend to computation for next round")
                .build();
    }
}
