package co.com.template.Controllers;

import co.com.template.Repositories.dto.*;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.com.template.Repositories.entities.Objective;
import co.com.template.services.ObjectiveService;


@Log4j2
@RestController
@RequestMapping("/objective")
public class ObjectiveController {

	@Autowired
	private ObjectiveService objectiveService;

	@GetMapping
	public ResponseEntity<Object> getObjective(){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.getObjective());
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}

	@PostMapping
	public ResponseEntity<Object> setObjective(@Valid @RequestBody CreateObjectiveDTO objective) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.setObjective(objective));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}

	@PutMapping("/{objectiveId}")
    public ResponseEntity<Object> updateObjective(@PathVariable Long objectiveId, @RequestBody ObjectiveEditionDTO objective) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.updateObjective(objectiveId, objective));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}


	@DeleteMapping("/{objective_id}")
	public ResponseEntity<Object> deleteObjective(@PathVariable Long objective_id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.deleteObjective(objective_id));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}

	@GetMapping("/{objectiveId}")
	@ResponseBody
	public ResponseEntity<Object> getObjectiveById(@PathVariable Long objectiveId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.getObjectiveById(objectiveId));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}

	@GetMapping("/group/{groupId}")
	@ResponseBody
	public ResponseEntity<Object> getObjectiveByGroup(@PathVariable Long groupId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.getObjectiveForGroup(groupId));
		} catch (Exception err) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}


	@GetMapping("/{groupId}/{userId}")
	@ResponseBody
	public ResponseEntity<Object> getObjectiveForGroupAndUser(@PathVariable Long groupId, @PathVariable Long userId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.getObjectiveForGroupAndForUser(groupId, userId));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}

	@GetMapping("/user/{userId}")
	@ResponseBody
	public ResponseEntity<Object> getObjectiveForUser(@PathVariable Long userId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.getObjectiveForUser(userId));
		} catch (Exception err) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
		}
	}


	@PutMapping("/{objectiveId}/close")
	public ResponseEntity<Object> closeObjective(@PathVariable Long objectiveId, @RequestBody CloseObjectiveDTO objective) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.closeObjective(objectiveId, objective));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<Object> getObjectivesByUser(@PathVariable Long userId){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.getObjectivesByUserId(userId));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}

	@PostMapping("/list")
	public ResponseEntity<Object> findObjectiveFilter(@Valid @RequestBody ObjectiveFilterDTO objectiveFilterDTO) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(objectiveService.findObjectiveFilter(objectiveFilterDTO));
		} catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}


}
