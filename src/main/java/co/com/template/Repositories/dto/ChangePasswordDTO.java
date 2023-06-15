package co.com.template.Repositories.dto;

import co.com.template.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChangePasswordDTO implements Serializable {

    @NotNull(message = Constants.USER_ID_REQUIRED)
    Long userId;

    @NotEmpty(message = Constants.PASSWORD_REQUIRED)
    String password;
}
