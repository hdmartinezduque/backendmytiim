package co.com.template.Repositories.dto;

import co.com.template.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
@Data
public class LoginRequestDTO implements Serializable{

    @NotBlank(message = Constants.USER_NAME_REQUIRED)
    String username;

    @NotEmpty(message = Constants.PASSWORD_REQUIRED)
    String password;

}
