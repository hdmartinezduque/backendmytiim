package co.com.template.Repositories.dto;

import lombok.Data;
import java.io.Serializable;


@Data
public class CommitmentDTO implements Serializable {

    private Long commitmentId;
    private String commitmentDescribe;
    private String commitmentDate;
    private Double commitmentGoal;
    private Long measureId;
    private Double commitmentAdvance;


}
