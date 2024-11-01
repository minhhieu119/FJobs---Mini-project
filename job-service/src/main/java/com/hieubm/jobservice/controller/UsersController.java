package com.hieubm.jobservice.controller;

import com.hieubm.jobservice.entity.Users;
import com.hieubm.jobservice.entity.UsersType;
import com.hieubm.jobservice.service.UsersService;
import com.hieubm.jobservice.service.UsersTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UsersTypeService usersTypeService;

    private final UsersService usersService;

    @GetMapping("/register")
    public String showRegister (Model model) {
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("usersType", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegitration (@Valid Users users) {
        usersService.addUser(users);
        return "dashboard";
    }
}
