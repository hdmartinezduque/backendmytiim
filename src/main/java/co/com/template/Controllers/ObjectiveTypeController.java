package co.com.template.Controllers;

import co.com.template.Repositories.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.com.template.services.ObjectiveTypeService;

@RestController
@RequestMapping("/objectiveType")
public class ObjectiveTypeController {

	@Autowired
	private ObjectiveTypeService objectiveTypeService;

	@GetMapping
	public ResponseEntity<Object> getObjective(){
		try{
			return ResponseEntity.status(HttpStatus.OK).body(objectiveTypeService.getObjectiveType());
		}catch(Exception err){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
		}
	}
}
