package co.com.template.Repositories.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PeriodDTO implements Serializable {

    private Long periodId;
    private String describe;

    public PeriodDTO(Long periodId, String describe) {
        this.periodId = periodId;
        this.describe = describe;
    }
}
