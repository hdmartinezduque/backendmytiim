package co.com.template.Repositories.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class RecognitionFilterDTO implements Serializable {

    private Long set;
    private Long userId;
    private Long groupId;
    private LocalDate commentDateInit;
    private LocalDate commentDateFinal;

}

