package io.muzoo.ssc.project.backend.game;

import lombok.Builder;

import java.util.*;

@Builder
public class CalculateBoard {
    private String color;
    private List<List<String>> board;
    private Map<Integer, List<List<Integer>>> possibleMoves;

    public Map<List<Integer>, List<List<Integer>>> getPossibleMoves() {
//        System.out.println(color);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board.get(r).get(c).equals(color)) {
                    findAllDirections(r, c);
                }
            }
        }

        return to2DMap();
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
        List<List<Integer>> toFlip = new ArrayList<>();
        while (nextR >= 0 && nextR < 8 && nextC >= 0 && nextC < 8 && (!(board.get(nextR).get(nextC).equals("")) && (!(board.get(nextR).get(nextC).equals(color))))) {
            toFlip.add(Arrays.asList(nextR, nextC));
            nextR += dirR;
            nextC += dirC;
        }

        if (nextR >= 0 && nextR < 8 && nextC >= 0 && nextC < 8 && board.get(nextR).get(nextC).equals("") && !toFlip.isEmpty()) {
            Integer toPlace = to1D(nextR, nextC);
            List<List<Integer>> existingFlips = possibleMoves.getOrDefault(toPlace, new ArrayList<>());
            existingFlips.addAll(toFlip);
            possibleMoves.put(toPlace, existingFlips);
        }
    }

    private Integer to1D(Integer r, Integer c) {
        return r * 8 + c;
    }

    private List<Integer> to2D(Integer i) {
        return Arrays.asList(i / 8, i % 8);
    }

    private Map<List<Integer>, List<List<Integer>>> to2DMap() {
        Map<List<Integer>, List<List<Integer>>> ret = new HashMap<>();
        for (Integer move : possibleMoves.keySet()) {
            ret.put(to2D(move), possibleMoves.get(move));
        }
        return ret;
    }
}
