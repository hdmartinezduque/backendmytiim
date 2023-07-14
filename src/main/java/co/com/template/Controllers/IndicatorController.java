package co.com.template.Controllers;

import co.com.template.Repositories.dto.*;
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

import java.io.UnsupportedEncodingException;
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

    @GetMapping("/pollContinues/{periodId}")
    public ResponseEntity<Object> getIndicatorsPollContinues(@PathVariable Long periodId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(indicatorService.getIndicatorPollContinues(periodId));
        }catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
        }
    }


    @GetMapping("/downloadPollContinues-csv/{periodId}")
    public ResponseEntity<ByteArrayResource> downloadPollContinuesCSVFile(@PathVariable Long periodId) {
        List<EmployeeDataPollContinues> employeeDataList = indicatorService.generatePollContinuesCSVFile(periodId);

        if (employeeDataList != null && !employeeDataList.isEmpty()) {
            String csvContent = indicatorService.generateCSVContentPollContinues(employeeDataList);

            byte[] csvBytes;
            try {
                csvBytes = csvContent.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Error encoding CSV content: " + e.getMessage());
            }

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

    @GetMapping("/closedPeriod/{periodId}")
    public ResponseEntity<Object> getIndicatorsClosedPeriod(@PathVariable Long periodId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(indicatorService.getIndicatorClosedPeriod(periodId));
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
        }
    }


    @GetMapping("/downloadClosedPeriod-csv/{periodId}")
    public ResponseEntity<ByteArrayResource> downloadClosedPeriodCSVFile(@PathVariable Long periodId) {
        List<EmployeeDataClosedPeriod> employeeDataList = indicatorService.generateClosedPeriodCSVFile(periodId);

        if (employeeDataList != null && !employeeDataList.isEmpty()) {
            String csvContent = indicatorService.generateCSVContentClosedPeriod(employeeDataList);

            byte[] csvBytes;
            try {
                csvBytes = csvContent.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Error encoding CSV content: " + e.getMessage());
            }

            ByteArrayResource resource = new ByteArrayResource(csvBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "employee_data_closed_period.csv");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(csvBytes.length)
                    .body(resource);
        }

        return ResponseEntity.noContent().build();
    }


}









