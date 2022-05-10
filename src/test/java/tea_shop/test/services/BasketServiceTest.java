package tea_shop.test.services;

import tea_shop.models.Tea;
import tea_shop.repositories.TeaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tea_shop.models.User;
import tea_shop.repositories.UserRepository;
import tea_shop.services.BasketService;

import java.util.*;

import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class BasketServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TeaRepository teaRepository;

    @Captor
    private ArgumentCaptor<Tea> captor;

    private BasketService basketService;

    @Autowired
    public void setBasketService(BasketService basketService) {
        this.basketService = basketService;
    }

    @BeforeEach
    public void init(){
        User user = new User();
        user.setLogin("test");
        user.setPassword("password");
        user.setId(1L);
        user.setTeas(new ArrayList<>());

        when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
    }

    @Test
    public void getTeas() {
        Tea tea1 = new Tea();
        tea1.setId(1);
        tea1.setName("Чай");
        tea1.setQuantity(5);
        tea1.setPrice(158);
        tea1.setSort("green");
        tea1.setWeight(200);

        Tea tea2 = new Tea();
        tea2.setId(1);
        tea2.setName("Чай");
        tea2.setQuantity(5);
        tea2.setPrice(158);
        tea2.setSort("green");
        tea2.setWeight(200);

        basketService.addTea(tea1);
        basketService.addTea(tea2);

        List<Tea> fetched = basketService.getTeas();

        User user = userRepository.findByLogin("test");

        Assertions.assertEquals(user.getTeas().size(), fetched.size());
    }

    @Test
    public void addTea() {
        Tea tea = new Tea();
        tea.setId(1);
        tea.setName("Чай");
        tea.setQuantity(5);
        tea.setPrice(158);
        tea.setSort("green");
        tea.setWeight(200);

        basketService.addTea(tea);

        verify(teaRepository).save(captor.capture());
        Tea captured = captor.getValue();
        Assertions.assertEquals(tea.getId(), captured.getId());
        Assertions.assertEquals(tea.getName(), captured.getName());
        Assertions.assertEquals(tea.getQuantity(), captured.getQuantity());
        Assertions.assertEquals(tea.getPrice(), captured.getPrice());
        Assertions.assertEquals(tea.getSort(), captured.getSort());
        Assertions.assertEquals(tea.getWeight(), captured.getWeight());

        User user = userRepository.findByLogin("test");
        Assertions.assertNotEquals(user.getTeas().indexOf(tea),-1);
    }

    @Test
    public void deleteTea(){
        Tea tea = new Tea();
        tea.setId(1);
        tea.setName("Чай");
        tea.setQuantity(5);
        tea.setPrice(158);
        tea.setSort("green");
        tea.setWeight(200);

        basketService.addTea(tea);

        verify(teaRepository).save(captor.capture());
        Tea captured = captor.getValue();

        when(teaRepository.findByName(tea.getName()))
                .thenReturn(tea);

        basketService.deleteTea(tea.getName());

        Assertions.assertEquals(basketService.getTeas().indexOf(captured), -1);
    }

    @Test
    public void buy(){
        Tea tea = new Tea();
        tea.setId(1);
        tea.setName("Чай");
        tea.setQuantity(5);
        tea.setPrice(158);
        tea.setSort("green");
        tea.setWeight(200);

        basketService.addTea(tea);

        verify(teaRepository).save(captor.capture());
        Tea captured = captor.getValue();

        basketService.buy();

        User user = userRepository.findByLogin("test");

        Assertions.assertEquals(basketService.getTeas().indexOf(captured), -1);
        Assertions.assertFalse(tea.getBaskets().contains(user));
    }


}
