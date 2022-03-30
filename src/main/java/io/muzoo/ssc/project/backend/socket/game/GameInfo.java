package io.muzoo.ssc.project.backend.socket.game;

import java.util.List;
import java.util.Map;

public class GameInfo {

    private List<String> board;

    private boolean isBlack;

    private int turn;

    private Map<Integer, List<Integer>> possibleMoves;

    public GameInfo(List<String> board, boolean isBlack, int turn) {
        this.board = board;
        this.isBlack = isBlack;
        this.turn = turn;
    }

    public List<String> getBoard() {
        return board;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public int getTurn() {
        return turn;
    }

    public Map<Integer, List<Integer>> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(Map<Integer, List<Integer>> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}
