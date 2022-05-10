package tea_shop.services;

import tea_shop.models.Tea;
import tea_shop.models.User;
import tea_shop.repositories.TeaRepository;
import tea_shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BasketService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeaRepository teaRepository;

    @Transactional
    public void addTea(Tea tea) {
        User user = getUser();
        if(tea.getQuantity() > 0){
            tea.setQuantity(tea.getQuantity()-1);
            user.addTea(tea);
            teaRepository.save(tea);
        }

    }

    @Transactional(readOnly = true)
    public List<Tea> getTeas() {
        User user = getUser();
        return user.getTeas();
    }

    public List<Tea> getSetTea(List<Tea> teas1) {
        Set<Tea> set = new HashSet<>(teas1);
        return new ArrayList<>(set);
    }

    public List<String> getCounts(List<Tea> tea1, List<Tea> tea2) {
        List<Integer> nums = new ArrayList<Integer>();
        for (Tea tea : tea1) {
            nums.add(Collections.frequency(tea2, tea));
        }
        List<String> str = new ArrayList<>();
        for (int i = 0; i <tea1.size(); i++) {
            str.add(nums.get(i) + " x " + tea1.get(i).getPrice());
        }
        return str;
    }

    public Double getPrice(List<Tea> teas) {
        double price = 0;
        for (Tea tea : teas) {
            price += tea.getPrice();
        }
        price -= price*getStock(teas) /100;
        return price;
    }

    public Double getStock(List<Tea> teas) {
        double discount = 0;
        if (teas.size() >=6){
            discount +=10;
        }
        List<String> sorts = new ArrayList<>();
        for (Tea tea : teas) {
            sorts.add(tea.getSort());
        }
        Set<String> stocks = new HashSet<>(sorts);
        if (stocks.size() >=2){
            discount +=5;
        }
        return discount;
    }

    @Transactional
    public void deleteTea(String name){
        User user = getUser();
        Tea tea = teaRepository.findByName(name);
        user.removeTea(tea);
        tea.setQuantity(tea.getQuantity()+1);
        userRepository.save(user);
        teaRepository.save(tea);
    }

    @Transactional
    public void buy(){
        User user = getUser();
        List<Tea> teas = user.getTeas();
        for (Tea tea : teas) {
            Set<User> users = tea.getBaskets();
            users.remove(user);
            tea.setBaskets(users);
            teaRepository.save(tea);
        }
        teas.clear();
        user.setTeas(teas);
        userRepository.save(user);
    }

    private User getUser(){
        String username = "";
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        } catch (NullPointerException e){
            username = "test";
        }

        return userRepository.findByLogin(username);
    }
}
