package co.com.template.security;

import co.com.template.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Component
public class UtilsToken {

    @Autowired
    private JwtUtils jwtUtils;

    public Long getUserIdFromToken(HttpHeaders headers ){
        List<String> auth = headers.get(Constants.AUTHORIZATION_HEADER);
        if(Objects.nonNull(auth) && !CollectionUtils.isEmpty(auth)){
            String token = auth.get(Constants.ZERO_INDEX);
            return jwtUtils.getUserIdFromJwtToken(token);
        }
        return null;
    }
}
