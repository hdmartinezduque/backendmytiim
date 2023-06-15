package co.com.template.Controllers;

import co.com.template.Repositories.dto.CreatePollQuestionDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.PollQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/poll-questions")
@RequiredArgsConstructor
public class PollQuestionController {

    private final PollQuestionService pollQuestionService;

    @PostMapping
    public ResponseEntity<Object> createQuestionOfPoll(@RequestBody CreatePollQuestionDTO request){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pollQuestionService.createQuestionOfPoll(request));
        }catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
    @GetMapping("/{pollTypeId}")
    public ResponseEntity<Object> getQuestionsOfPoll(@PathVariable Long pollTypeId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pollQuestionService.getQuestionsOfPoll(pollTypeId));
        }catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
    @DeleteMapping("/{pollQuestionId}")
    public ResponseEntity<Object> deleteQuestionOfPoll(@PathVariable Long pollQuestionId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pollQuestionService.deleteQuestionOfPoll(pollQuestionId));
        }catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(),null));
        }
    }

}
