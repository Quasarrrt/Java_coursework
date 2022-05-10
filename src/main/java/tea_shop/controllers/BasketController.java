package tea_shop.controllers;

import tea_shop.models.Tea;
import tea_shop.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;

@Controller
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping("/basket")
    @ResponseBody
    public ModelAndView getBasket(){
        ModelAndView mav = new ModelAndView("basket");
        List<Tea> teas = basketService.getSetTea(basketService.getTeas());
        teas.sort(Comparator.comparing(Tea::getId));
        mav.addObject("tea", teas);
        mav.addObject("num", basketService.getCounts(teas, basketService.getTeas()));
        mav.addObject("price", basketService.getPrice(basketService.getTeas()));
        mav.addObject("discount", (int) Math.round(basketService.getStock(basketService.getTeas())));
        return mav;
    }
    @GetMapping("/basket/delete/{name}")
    public String deleteBasket(@PathVariable String name){
        basketService.deleteTea(name);
        return "redirect:/basket";
    }
    @GetMapping("/basket/buy")
    public String deleteBasket(){
        basketService.buy();
        return "redirect:/";
    }
}
