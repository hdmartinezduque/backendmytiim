package co.com.template.security;

import co.com.template.Repositories.UserRepository;
import co.com.template.Repositories.entities.User;
import co.com.template.exception.CustomException;
import co.com.template.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUser(username);
        if(Objects.isNull(user)){
            new CustomException(Constants.USER_NOT_EXISTS_ERROR, HttpStatus.OK);
        }
        return UserDetailsImpl.build(user);
    }
}
