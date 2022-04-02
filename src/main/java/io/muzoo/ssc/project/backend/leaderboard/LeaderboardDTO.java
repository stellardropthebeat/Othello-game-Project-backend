package io.muzoo.ssc.project.backend.leaderboard;

import io.muzoo.ssc.project.backend.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class LeaderboardDTO {

    private ArrayList<ArrayList<String>> userList;
}
