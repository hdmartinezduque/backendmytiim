package co.com.template.Controllers;


import co.com.template.Repositories.dto.CreateCommentFeedbackDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.CommentFeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commentFeedback")
public class CommentFeedbackController {

	@Autowired
	private CommentFeedbackService commentFeedbackService;


	@PostMapping
	public ResponseEntity<Object> setCommentFeedback(@Valid @RequestBody CreateCommentFeedbackDTO createCommentFeedbackDTO) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentFeedbackService.setCommentFeedback(createCommentFeedbackDTO));
		} catch (Exception err) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
		}
	}

	@GetMapping("/comment/{commentId}")
	public ResponseEntity<Object> getCommentFeedback(@PathVariable Long commentId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentFeedbackService.getCommentFeedback(commentId));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}


}