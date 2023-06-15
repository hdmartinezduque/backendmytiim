package co.com.template.Repositories.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateCommitmentDTO implements Serializable {

    private Long objectiveId;
    private String commitmentDescribe;
    private LocalDate commitmentDate;
    private Long measureId;
    private Double commitmentAdvance;
    private Double commitmentGoal;
    private List<CommitmentDTO> commitmentDTO;
}
