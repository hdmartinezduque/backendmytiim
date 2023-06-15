package co.com.template.Repositories.dto;



import lombok.Data;

import java.time.LocalDate;

@Data
public class CommitmentEditionDTO {

    private Long commitmentId;
    private String commitmentDescribe;
    private LocalDate commitmentDate;
    private Long measureId;
    private Double commitmentAdvance;
    private Double commitmentGoal;
}
