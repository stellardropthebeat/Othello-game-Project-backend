package io.muzoo.ssc.project.backend.auth;

import io.muzoo.ssc.project.backend.SimpleResponseDTO;

import io.muzoo.ssc.project.backend.UserRepository;
import io.muzoo.ssc.project.backend.whoami.WhoamiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/api/test")
    public String test(){
        return "if this message is shown, login is successful cuz we didnt set to permit this path";
    }

    @PostMapping("/api/login")
    public SimpleResponseDTO login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        try{
            // check if there is a current user login, if so logout first
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && principal instanceof User){
                request.logout();
            }

            request.login(username, password);
            return SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("successfully log in")
                    .build();
        } catch (ServletException e) {
            return SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/api/logout")
    public SimpleResponseDTO logout(HttpServletRequest request){
        try {
            request.logout();
            return  SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("successfully log out")
                    .build();
        } catch (ServletException e) {
            return SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/api/signin")
    public SimpleResponseDTO signin(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String cpassword = request.getParameter("cpassword");

        String errorMessage = null;
        try{
            io.muzoo.ssc.project.backend.User newUser = userRepository.findFirstByUsername(username);

            if (newUser != null){
                errorMessage = String.format("Username %s has already been used.", username);
            } else if (!StringUtils.equals(password, cpassword)){
                errorMessage = "Confirmed password mismatches.";
            } else if (StringUtils.isEmpty(password)){
                errorMessage = "Password can't be blank.";
            } else if (StringUtils.isEmpty(username)){
                errorMessage = "Username can't be blank.";
            }

            if (errorMessage != null){
                return SimpleResponseDTO
                        .builder()
                        .success(false)
                        .message(errorMessage)
                        .build();
            } else {
                newUser = new io.muzoo.ssc.project.backend.User();
                newUser.setUsername(username);
                newUser.setPassword(passwordEncoder.encode(password));
                newUser.setRole("USER");
                userRepository.save(newUser);
                return SimpleResponseDTO
                        .builder()
                        .success(true)
                        .message(String.format("Username %s has been created", username))
                        .build();
            }
        } catch (Exception e) {
            return SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/api/change_pass")
    public SimpleResponseDTO changePass(HttpServletRequest request) {
        String username = request.getParameter("username");
        String newPassword = request.getParameter("password");
        String cpassword = request.getParameter("cpassword");

        String errorMessage = null;
        try{
            io.muzoo.ssc.project.backend.User user = userRepository.findFirstByUsername(username);

            if(user == null){
                errorMessage = String.format("User %s does not exist.", username);
            } else if(!StringUtils.equals(newPassword, cpassword)){
                errorMessage = "Confirmed password mismatches";
            } else if (StringUtils.isEmptyOrWhitespace(newPassword)){
                errorMessage = "Password can't be blank.";
            }

            if (errorMessage != null) {
                return SimpleResponseDTO
                        .builder()
                        .success(false)
                        .message(errorMessage)
                        .build();
            } else {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setRole("USER");
                userRepository.save(user);
                return SimpleResponseDTO
                        .builder()
                        .success(true)
                        .message("successfully change password")
                        .build();
            }
        } catch (Exception ignored) {
        }
        return SimpleResponseDTO
                .builder()
                .success(false)
                .message("fail to change password")
                .build();

    }

}

