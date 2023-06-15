package co.com.template.Controllers;

import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getUser() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUser());
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }


    @GetMapping("/user/{groupId}")
    public ResponseEntity<Object> getUserForGroup(@PathVariable Long groupId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserForGroup(groupId));
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }

     @GetMapping("/actives")
    public ResponseEntity<Object> getListUser() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getListActiveUser());
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
}



