package co.com.template.Controllers;

import co.com.template.Repositories.dto.LoginRequestDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<Object> getActiveQuestions(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(questionService.getActiveQuestions());
        }catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
}
