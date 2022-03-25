package io.muzoo.ssc.project.backend.game;

import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
public class CalculateBoard {
    private List<List<String>> board;
    private Map<Integer, List<List<Integer>>> possibilities = new HashMap<>();

    public Map<Integer, List<List<Integer>>> AllPossibilities() {
        return possibilities;
    }
}
