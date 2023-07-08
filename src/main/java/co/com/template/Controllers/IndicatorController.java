package co.com.template.Controllers;

import co.com.template.Repositories.dto.EmployeeData;
import co.com.template.Repositories.dto.EmployeeDataCommitment;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.IndicatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/indicators")
public class IndicatorController {

    private final IndicatorService indicatorService;


    @GetMapping("/objectives/{periodId}")
    public ResponseEntity<Object> getIndicatorsObjective(@PathVariable Long periodId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(indicatorService.getIndicatorObjective(periodId));
        }catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
        }
    }


    @GetMapping("/downloadObjective-csv/{periodId}")
    public ResponseEntity<ByteArrayResource> downloadObjectiveCSVFile(@PathVariable Long periodId) {
        List<EmployeeData> employeeDataList = indicatorService.generateCSVFile(periodId);

        if (employeeDataList != null && !employeeDataList.isEmpty()) {
            String csvContent = indicatorService.generateCSVContent(employeeDataList);

            byte[] csvBytes = csvContent.getBytes(StandardCharsets.UTF_8);

            ByteArrayResource resource = new ByteArrayResource(csvBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "employee_data.csv");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(csvBytes.length)
                    .body(resource);
        }
        return ResponseEntity.noContent().build();

    }




    @GetMapping("/commitment/{periodId}")
    public ResponseEntity<Object> getIndicatorsCommitment(@PathVariable Long periodId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(indicatorService.getIndicatorCommitment(periodId));
        }catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
        }
    }

    @GetMapping("/downloadCommitment-csv/{periodId}")
    public ResponseEntity<ByteArrayResource> downloadCommitmenCSVFile(@PathVariable Long periodId) {
        List<EmployeeDataCommitment> employeeDataCommitments = indicatorService.generateCommitmentCSVFile(periodId);

        if (employeeDataCommitments != null && !employeeDataCommitments.isEmpty()) {
            String csvContent = indicatorService.generateCSVContentCommitment(employeeDataCommitments);

            byte[] csvBytes = csvContent.getBytes(StandardCharsets.UTF_8);

            ByteArrayResource resource = new ByteArrayResource(csvBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "employee_data.csv");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(csvBytes.length)
                    .body(resource);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/downloadCommitment-csv")
    public ResponseEntity<ByteArrayResource> downloadCommitmenCSVFile() {
        List<EmployeeDataCommitment> employeeDataCommitments = indicatorService.generateCommitmentCSVFile();

        if (employeeDataCommitments != null && !employeeDataCommitments.isEmpty()) {
            String csvContent = indicatorService.generateCSVContentCommitment(employeeDataCommitments);

            byte[] csvBytes = csvContent.getBytes(StandardCharsets.UTF_8);

            ByteArrayResource resource = new ByteArrayResource(csvBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "employee_data.csv");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(csvBytes.length)
                    .body(resource);
        }

        return ResponseEntity.noContent().build();
    }


}









