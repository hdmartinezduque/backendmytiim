package co.com.template.Controllers;

import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.CommentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commentType")
public class CommentTypeController {

    @Autowired
    private CommentTypeService commentTypeService;

    @GetMapping
    public ResponseEntity<Object> getCommentType() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentTypeService.getCommentType());
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
}
