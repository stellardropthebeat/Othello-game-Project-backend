package io.muzoo.ssc.project.backend.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

    @GetMapping
    public String test(){
        return "if this message is shown, login is successful cuz we didnt set to permit this path";
    }

    @PostMapping("/api/login")
    public String login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
            request.login(username, password);
            return "Login successful";
        } catch (ServletException e){ 
            return "Failed to login";
        }
    }

    @PostMapping("/apr/logout")
    public String logout(HttpServletRequest request){
        try{
            request.logout();
            return "Logout successful";
        } catch (ServletException e){
            return "Failed to logout";
        }
    }
}
