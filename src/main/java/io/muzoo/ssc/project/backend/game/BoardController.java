package io.muzoo.ssc.project.backend.game;

import io.muzoo.ssc.project.backend.SimpleResponseDTO;
import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
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

    @Autowired
    UserRepository userRepository;

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

        return BoardDTO.builder().success(true).possibleMoves(possibleMoves).build();
    }

    @PostMapping("/api/add-board-record")
    public SimpleResponseDTO addNewBoardRecord(@RequestBody Map<String, Object> payload) {
        BoardRecord record = new BoardRecord();
        record.setRoomId(Long.parseLong(String.valueOf(payload.get("roomId"))));
        record.setTurn((Integer) payload.get("turn"));
        record.setBoardRecord((List<String>) payload.get("boardRecord"));
        boardRepository.save(record);
        return SimpleResponseDTO.builder().success(true).message("new board record added").build();
    }


    @PostMapping("/api/latest-game")
    public SimpleResponseDTO addLatestGameRecord(@RequestBody Map<String, Object> payload) {
        long roomId = Long.parseLong(String.valueOf(payload.get("roomId")));
        String username = (String) payload.get("username");
        User user = userRepository.findFirstByUsername(username);
        user.setLatestGame(roomId);
        int prevScore = user.getScore();
        int gameScore = (int) payload.get("score");
        user.setScore(prevScore + gameScore);
        userRepository.save(user);
        return SimpleResponseDTO.builder().success(true).message("add latest game").build();
    }

    @PostMapping("/api/replay")
    public BoardDTO getReplayBoard(@RequestBody Map<String, Object> payload) {
        int turn = (Integer) payload.get("turn");
        String username = (String) payload.get("username");

        User user = userRepository.findFirstByUsername(username);
        long latestGame = user.getLatestGame();
        try {
            List<String> board = boardRepository.findBoardRecordByRoomIdAndTurn(latestGame, turn).getBoardRecord();
            return BoardDTO.builder().success(true).board(board).finished(false).build();
        } catch (NullPointerException e) {
            List<String> board = boardRepository.findBoardRecordByRoomIdAndTurn(latestGame, turn-1).getBoardRecord();
            return BoardDTO.builder().success(true).board(board).finished(true).build();
        }

    }

}
