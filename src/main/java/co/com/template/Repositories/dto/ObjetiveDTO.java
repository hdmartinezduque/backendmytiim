package co.com.template.Repositories.dto;

import co.com.template.Repositories.entities.Objective;
import co.com.template.Repositories.entities.ObjectiveType;
import co.com.template.Repositories.entities.Status;
import lombok.Data;
import java.io.Serializable;

@Data
public class ObjetiveDTO implements Serializable {


    private Long objectiveId;
    private String objectiveDescribe;
    private Long objectiveUserId;
    private Integer objectiveGroupId;
    private Status status;
    private ObjectiveType objectiveType;
    private Long objectiveQualify;
    private String objectiveObservations;

    public ObjetiveDTO(Objective objetive) {
        this.objectiveId = objetive.getObjectiveId();
        this.objectiveDescribe = objetive.getObjectiveDescribe();
        this.objectiveUserId = objetive.getObjectiveId();
        this.status = objetive.getStatus();
        this.objectiveType = objetive.getObjectiveType();

    }
}
