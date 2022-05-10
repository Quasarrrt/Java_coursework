package tea_shop.controllers;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class UserRegistrationDto {

    @NotNull
    private String password;

    @NotNull
    private String login;

    @AssertTrue
    private Boolean terms;

}
