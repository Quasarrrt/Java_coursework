package tea_shop.services;

import tea_shop.controllers.UserRegistrationDto;
import tea_shop.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByLogin(String login);

    void save(UserRegistrationDto registration, String cookie);
}
