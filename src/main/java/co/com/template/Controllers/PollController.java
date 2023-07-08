package co.com.template.Controllers;

import co.com.template.Repositories.dto.AnswerPollDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
public class PollController {
    private final PollService pollService;

    @GetMapping("/{pollId}/questions")
    public ResponseEntity<Object> getActiveQuestions(@PathVariable Long pollId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pollService.getQuestionsOfPoll(pollId));
        }catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
    @PostMapping("/questions")
    public ResponseEntity<Object> saveAnswersPoll(@RequestBody AnswerPollDTO request){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pollService.saveAnswersPoll(request));
        }catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }

    @GetMapping("/to-do")
    public ResponseEntity<Object> getPollsToDo(@RequestHeader HttpHeaders headers){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pollService.getPollsToDo(headers));
        }catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
}
