package com.hieubm.jobservice.controller;

import com.hieubm.jobservice.entity.Users;
import com.hieubm.jobservice.entity.UsersType;
import com.hieubm.jobservice.service.UsersService;
import com.hieubm.jobservice.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

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
    public String userRegistration(@Valid Users users, BindingResult result, Model model) {

        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
        if (optionalUsers.isPresent()) {
            model.addAttribute("emailError", "Email đã tồn tại, vui lòng nhập email khác!");
            List<UsersType> usersTypes = usersTypeService.getAll();
            model.addAttribute("usersType", usersTypes);
            model.addAttribute("user", new Users());
            return "register";
        }

        usersService.addUser(users);
        return "redirect:/dashboard"; // Chuyển đến trang dashboard
    }

    @GetMapping("/login")
    public String showLogin () {
        return "login";
    }

    @GetMapping("/logout")
    public String logout (HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
