package tea_shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String getHome() {
        return "home";
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    @GetMapping("/stocks")
    public String getInfo() {
        return "info";
    }
    @GetMapping("/{undefind}")
    public String getError() {
        return "404";
    }
}
