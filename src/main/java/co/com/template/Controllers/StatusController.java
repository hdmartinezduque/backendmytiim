package co.com.template.Controllers;

import co.com.template.Repositories.entities.Status;
import co.com.template.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<Object> getStatus() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(statusService.getStatus());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/{statusId}")
    @ResponseBody
    public ResponseEntity<Object> updateStatus(@PathVariable Long statusId, @RequestBody Status status) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(statusService.updateStatus(statusId, status));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @GetMapping("/{statusType}")
    public ResponseEntity<Object> getStatusByType(@PathVariable String statusType) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(statusService.getStatusByType(statusType));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }



}