package tea_shop.controllers;

import tea_shop.models.Tea;
import tea_shop.services.BasketService;
import tea_shop.services.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class TeaController {

    @Autowired
    private TeaService teaService;

    @Autowired
    private BasketService basketService;

    @GetMapping("/{sort}")
    @ResponseBody
    public ModelAndView getTeas(@PathVariable String sort){
        ModelAndView mav = new ModelAndView("shop");
        List<Tea> teas= teaService.getBySort(sort);
        mav.addObject("tea", teas);
        return mav;
    }

    @GetMapping("/{sort}/get/{name}")
    @ResponseBody
    public ModelAndView getTea(@PathVariable String sort, @PathVariable String name){
        List<Tea> teas = teaService.getBySort(sort);
        Tea tea = teaService.getByName(teas, name);
        ModelAndView mav = new ModelAndView("tea");
        mav.addObject("tea", tea);
        return mav;
    }

    @GetMapping("{sort}/add/{name}")
    @ResponseBody
    public ModelAndView addTea(@PathVariable String sort, @PathVariable String name){
        List<Tea> teas = teaService.getBySort(sort);
        Tea tea = teaService.getByName(teas, name);
        ModelAndView mav = new ModelAndView("shop");
        basketService.addTea(tea);
        mav.addObject("tea", teas);
        mav.addObject("product", tea.getName());
        mav.addObject("purchase", true);
        return mav;
    }

}
