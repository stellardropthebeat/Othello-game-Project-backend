package io.muzoo.ssc.project.backend.game;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BoardDTO {

    private boolean success;
    private Map<Integer, List<Integer>> possibleMoves;
    private List<String> board;
    private boolean finished;
}
