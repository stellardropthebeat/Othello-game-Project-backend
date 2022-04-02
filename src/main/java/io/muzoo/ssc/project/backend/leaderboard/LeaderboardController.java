package io.muzoo.ssc.project.backend.leaderboard;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class LeaderboardController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/leaderboard")
    public LeaderboardDTO getscore(){
        List<User> userList = userRepository.findAll();
        ArrayList<ArrayList<String> > scoreTable = new ArrayList<ArrayList<String> >();
        Collections.sort(userList);

        int topten = 1;
        for (User u: userList) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(u.getUsername());
            temp.add(String.valueOf(u.getScore()));
            scoreTable.add(temp);
            if (topten == 10) {
                break;
            }
            topten++;
        }
        return LeaderboardDTO
                .builder()
                .userList(scoreTable)
                .build();
    }
    // make a list
    // use .findAll to get username and score
    // put in to map
    // return map to frontend
}
