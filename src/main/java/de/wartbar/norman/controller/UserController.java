package de.wartbar.norman.controller;

import de.wartbar.norman.data.WebDefaults;
import de.wartbar.norman.spring.data.persistence.SecurityService;
import de.wartbar.norman.spring.data.persistence.UserModel;
import de.wartbar.norman.spring.data.persistence.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        /*
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        */

        model.addAttribute("userModelForm", new UserModel());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userModelForm") UserModel userModelForm, BindingResult bindingResult) {
        userValidator.validate(userModelForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userModelForm);

        securityService.autoLogin(userModelForm.getUsername(), userModelForm.getPasswordConfirm());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        /*
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        */

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
            WebDefaults.logout();
        }

        return "login";
    }

    @GetMapping({"/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
}
