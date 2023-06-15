package co.com.template.services;

import co.com.template.Repositories.StatusRepository;
import co.com.template.Repositories.UserRepository;
import co.com.template.Repositories.dto.ChangePasswordDTO;
import co.com.template.Repositories.dto.LoginRequestDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.dto.UserDTO;
import co.com.template.Repositories.entities.Status;
import co.com.template.Repositories.entities.User;
import co.com.template.exception.CustomException;
import co.com.template.security.JwtUtils;
import co.com.template.utils.Constants;
import co.com.template.utils.StatusEnum;
import co.com.template.utils.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Log4j2
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    public ResponseDTO login(LoginRequestDTO request){
        try{
            User user=userRepository.findByUser(request.getUsername());
            if(Objects.nonNull(user) && Util.getEncoder().matches(request.getPassword(),  user.getUserPassword())){
                UserDTO response =new UserDTO(user);
                String jwt = jwtUtils.generateJwtToken(response);
                response.setToken(jwt);
                return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, response);
            }
            else
                return new ResponseDTO(HttpStatus.OK,Constants.LOGIN_ERROR, null);
        }catch(Exception err){
            log.error(err.getMessage(), err);
            throw new CustomException(err.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseDTO updatePassword(ChangePasswordDTO request){
        try{
            User user=userRepository.findByUserId(request.getUserId());
            if(Objects.nonNull(user)) {
                if(user.getStatus().getStatusId().equals(StatusEnum.ACTIVATION_PENDING_USER.getId()) ){
                    user.setUserPassword(Util.getEncoder().encode(request.getPassword()));
                    Status status = statusRepository.findByStatusId(StatusEnum.ACTIVE_USER.getId());
                    user.setStatus(status);
                    userRepository.save(user);
                    return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);
                }
                else
                    return new ResponseDTO(HttpStatus.OK,Constants.USER_NOT_ACTIVE_PENDING_ERROR, null);
            }
            else
                return new ResponseDTO(HttpStatus.OK,Constants.USER_NOT_EXISTS_ERROR, null);

        }catch(Exception err){
            log.error(err.getMessage(), err);
            throw new CustomException(err.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
