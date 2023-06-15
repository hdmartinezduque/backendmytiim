package co.com.template.Controllers;

import co.com.template.Repositories.dto.CreateCommentDTO;
import co.com.template.Repositories.dto.CreateRecognitionDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.entities.Comment;
import co.com.template.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping("/objectives/{objectiveId}")
	public ResponseEntity<Object> getComment(@PathVariable Long objectiveId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(objectiveId));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}

	@PostMapping
	public ResponseEntity<Object> setComment(@Valid @RequestBody CreateCommentDTO createCommentDTO) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.setComment(createCommentDTO));
		} catch (Exception err) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
		}
	}

	@PostMapping("/recognition")
	public ResponseEntity<Object> createCommentRecognition(@Valid @RequestBody CreateRecognitionDTO createRecognitionDTO) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.createCommentRecognition(createRecognitionDTO));
		} catch (Exception err) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
		}
	}

	@GetMapping("/recognition/list")
	public ResponseEntity<Object> getCommentRecognition() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentRecognition());
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}

}