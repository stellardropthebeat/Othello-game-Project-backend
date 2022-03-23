package io.muzoo.ssc.project.backend.whoami;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * A controller to retrieve logged-in user
 */
@RestController
public class WhoamiController {

    @Autowired
    UserRepository userRepository;

    /**
     * Make suer that all API paths begin with /api
     */
    @GetMapping("/api/whoami")
    public WhoamiDTO whoami(){
        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && principal instanceof org.springframework.security.core.userdetails.User){
                // user login
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
                User u = userRepository.findFirstByUsername(user.getUsername());

                return WhoamiDTO.builder()
                        .loggedIn(true)
                        .name(u.getUsername())
                        .role(u.getRole())
                        .username(u.getUsername())
                        .build();
            }
        } catch (Exception ignored) {
        }
        // user not login
        return WhoamiDTO.builder()
                .loggedIn(false)
                .build();
    }
}
