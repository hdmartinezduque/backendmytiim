package co.com.template.Controllers;


import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.PeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/periods")
@RequiredArgsConstructor
public class PeriodController {

    private final PeriodService periodService;


    @GetMapping
    public ResponseEntity<Object> getPeriods(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(periodService.getPeriod());
        }catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
        }
    }
}



