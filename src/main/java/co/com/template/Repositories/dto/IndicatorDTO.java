package co.com.template.Repositories.dto;

import lombok.Data;

@Data
public class IndicatorDTO {
    private int totalUsers;
    private double percentageCreated;
    private double percentageNoCreated;
    private Long periodId;

    public IndicatorDTO(int totalUsers, double percentageCreated, double percentageNoCreated, Long periodId) {
        this.totalUsers = totalUsers;
        this.percentageCreated = percentageCreated;
        this.percentageNoCreated = percentageNoCreated;
        this.periodId = periodId;
    }
}