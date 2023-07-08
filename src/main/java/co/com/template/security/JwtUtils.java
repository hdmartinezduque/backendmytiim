package co.com.template.security;

import co.com.template.Repositories.dto.UserDTO;
import co.com.template.exception.CustomException;
import co.com.template.utils.Constants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
@Getter
public class JwtUtils {
    @Value("${security.app.jwtSecret}")
    private String jwtSecret;

    @Value("${security.app.jwtExpirationMs}")
    private int jwtExpirationMs;


    public String generateJwtToken(UserDTO userDTO) {

        //UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Map<String, Object> claims= new HashMap<>();
        claims.put(Constants.USER_ID_TOKEN,userDTO.getUserId());
        claims.put(Constants.USER_NAME_TOKEN,userDTO.getUserName());
        claims.put(Constants.ROLES_TOKEN,userDTO.getRoles());

        return Jwts.builder()
                .setSubject((userDTO.getUser()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .addClaims(claims)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
    public Long getUserIdFromJwtToken(String token) {
        return Long.parseLong(Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().get(Constants.USER_ID_TOKEN).toString());
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new CustomException("JWT token is expired: "+ e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
