package tea_shop.test.services;

import tea_shop.models.Tea;
import tea_shop.repositories.TeaRepository;
import tea_shop.services.TeaService;
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
public class TeaServiceTest {

    @MockBean
    private TeaRepository teaRepository;

    @MockBean
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<Tea> captor;

    private TeaService teaService;

    private final List<Tea> teas = new ArrayList<>();

    @Autowired
    public void setTeaService(TeaService teaService) {
        this.teaService = teaService;
    }

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
    public void getBySort() {
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
        teas.add(tea1);
        teas.add(tea2);
        when(teaRepository.findAllBySort("green")).thenReturn(teas);

        List<Tea> fetched = teaService.getBySort("green");

        verify(teaRepository, times(1)).findAllBySort("green");
        Assertions.assertEquals(teas.size(), fetched.size());
    }

    @Test
    public void getByName(){
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

        Assertions.assertEquals(teaService.getByName(basketService.getTeas(), captured.getName()).getName(),
                captured.getName());
    }
}
