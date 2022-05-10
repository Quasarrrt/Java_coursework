package tea_shop.controllers;

import tea_shop.models.User;
import tea_shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result, @CookieValue("JSESSIONID") String cookie){

        User existing = userService.findByLogin(userDto.getLogin());
        if (existing != null){
            result.rejectValue("login", null,
                    "Этот логин занят");
        }

        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userDto, cookie);
        return "redirect:/registration?success";
    }

}
