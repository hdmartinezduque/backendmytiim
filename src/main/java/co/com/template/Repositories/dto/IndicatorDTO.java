package co.com.template.Repositories.dto;

import lombok.Data;

@Data
public class IndicatorDTO {
    private int totalUsers;
    private double percentageCreated;
    private double percentageNoCreated;

    public IndicatorDTO(int totalUsers, double percentageCreated, double percentageNoCreated) {
        this.totalUsers = totalUsers;
        this.percentageCreated = percentageCreated;
        this.percentageNoCreated = percentageNoCreated;
    }
}