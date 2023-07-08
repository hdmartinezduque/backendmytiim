package co.com.template.Controllers;

import co.com.template.Repositories.dto.*;
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

	@PostMapping("/recognition/list")
	public ResponseEntity<Object> getCommentRecognition(@Valid @RequestBody RecognitionFilterDTO recognitionFilterDTO) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentRecognition(recognitionFilterDTO));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}



}