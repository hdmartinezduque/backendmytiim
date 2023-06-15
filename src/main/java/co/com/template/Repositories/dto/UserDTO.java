package co.com.template.Repositories.dto;

import co.com.template.Repositories.entities.*;
import co.com.template.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDTO implements Serializable {


    private Long userId;
    private String userName;
    private String user;
    private Status status;
    private String token;
    private List<Roll> roles;

    public UserDTO(User user) {
        this.userId= user.getUserId();
        this.userName = user.getUserName()+" "+user.getUserLastName();
        this.status = user.getStatus();
        this.user = user.getUser();
        roles = user.getRolls().stream().collect(Collectors.toList());
    }

}