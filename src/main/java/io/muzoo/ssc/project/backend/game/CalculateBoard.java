package io.muzoo.ssc.project.backend.game;

import lombok.Builder;

import java.util.*;

public class CalculateBoard {
    private String color;
    private List<List<String>> board2D;
    private Map<Integer, List<Integer>> possibleMoves;

    public CalculateBoard(String color) {
        this.color = color;
        this.possibleMoves = new HashMap<>();
    }

    public Map<Integer, List<Integer>> getPossibleMoves(List<String> board1D) {
//        System.out.println(color);

        this.board2D = to2DBoard(board1D);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board2D.get(r).get(c).equals(color)) {
                    findAllDirections(r, c);
                }
            }
        }

        return possibleMoves;
    }

    private void findAllDirections(int r, int c) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                findToFlip(r, c, i, j);
            }
        }
    }

    private void findToFlip(int r, int c, int dirR, int dirC) {
        int nextR = r + dirR;
        int nextC = c + dirC;
        List<Integer> toFlip = new ArrayList<>();
        while (nextR >= 0 && nextR < 8 && nextC >= 0 && nextC < 8 && (!(board2D.get(nextR).get(nextC).equals("")) && (!(board2D.get(nextR).get(nextC).equals(color))))) {
            toFlip.add(to1D(nextR, nextC));
            nextR += dirR;
            nextC += dirC;
        }

        if (nextR >= 0 && nextR < 8 && nextC >= 0 && nextC < 8 && board2D.get(nextR).get(nextC).equals("") && !toFlip.isEmpty()) {
            Integer toPlace = to1D(nextR, nextC);
            List<Integer> existingFlips = possibleMoves.getOrDefault(toPlace, new ArrayList<>());
            existingFlips.addAll(toFlip);
            possibleMoves.put(toPlace, existingFlips);
        }
    }

    private Integer to1D(Integer r, Integer c) {
        return r * 8 + c;
    }

    private List<List<String>> to2DBoard(List<String> board1D) {
        List<List<String>> ret = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            List<String> row = new ArrayList<>();
            for (int c = 0; c < 8; c++) {
                row.add(board1D.get(r * 8 + c));
            }
            ret.add(row);
        }
        return ret;
    }
}
