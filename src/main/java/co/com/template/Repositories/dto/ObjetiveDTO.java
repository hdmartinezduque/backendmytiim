package co.com.template.Repositories.dto;

import co.com.template.Repositories.entities.Objective;
import co.com.template.Repositories.entities.ObjectiveType;
import co.com.template.Repositories.entities.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.io.Serializable;

@Data
public class ObjetiveDTO implements Serializable {


    private Long objectiveId;
    private String objectiveDescribe;
    private Long objectiveUserId;
    private Long objectiveGroupId;
    private Status status;
    private ObjectiveType objectiveType;
    private Long objectiveQualify;
    private String objectiveObservations;
    private Long periodId;

    public ObjetiveDTO(Objective objective) {
        this.objectiveId = objective.getObjectiveId();
        this.objectiveDescribe = objective.getObjectiveDescribe();
        this.objectiveUserId = objective.getObjectiveId();
        this.status = objective.getStatus();
        this.objectiveType = objective.getObjectiveType();
        this.periodId = objective.getPeriod().getPeriodId();
    }
}
