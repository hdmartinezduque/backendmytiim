package co.com.template.Controllers;

import co.com.template.Repositories.dto.ChangePasswordDTO;
import co.com.template.Repositories.dto.LoginRequestDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequestDTO request){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(authenticationService.login(request));
        }catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
    @PutMapping("/password")
    public ResponseEntity<Object> updatePassword(@RequestBody @Valid ChangePasswordDTO request){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(authenticationService.updatePassword(request));
        }
        catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
}
