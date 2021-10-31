package ir.maktab.University.controllers;

import ir.maktab.University.entities.User;
import ir.maktab.University.service.UserService;
import ir.maktab.University.util.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class VerifyUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TeacherController teacherController;


    @PostMapping("/get-right-user")
    public User getRightUser(String username, String password){
        User user = userService.getUserByUsernameAndPassword(username,password);
        return user;
    }

    @PostMapping("/is-user-allowed")
    public String isUserAllow(String username){
        User user = userService.getUserByUserName(username);
        String result = "";
        if(user.getType().equals("Student")){
            if(studentController.isAllow(username)){
                result = "Ok";
            }
        }else if(user.getType().equals("Teacher")){
            if(teacherController.teacherAllow(username)){
                result = "Ok";
            }
        }else {
            result = "Ok";
        }
        return result;
    }
}
