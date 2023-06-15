package co.com.template.Controllers;

import co.com.template.Repositories.dto.*;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.CommitmentService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequestMapping("/commitment")
public class CommitmentController {

    @Autowired
    private CommitmentService commitmentService;

    @GetMapping("/objectives/{objectiveId}")
    public ResponseEntity<Object> getCommitmentByObjectiveId (@PathVariable Long objectiveId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commitmentService.getCommitmentByObjectiveId(objectiveId));
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }

    @PostMapping
    public ResponseEntity<Object> setCommitment(@Valid @RequestBody CreateCommitmentDTO createCommitmentDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commitmentService.SetCommitment(createCommitmentDTO));
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateCommitment(@RequestBody CommitmentEditionDTO commitment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commitmentService.updateCommitment(commitment));
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }

    }
    @PostMapping("/advance")
    public ResponseEntity<Object> advanceCommitment(@RequestBody AdvanceCommitmentDTO advance) {
        log.info("Leyendo1");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commitmentService.advanceCommitment(advance));
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }

    }



}
