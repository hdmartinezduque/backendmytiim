package co.com.template.Repositories.dto;

import co.com.template.Repositories.entities.Objective;
import co.com.template.Repositories.entities.ObjectiveType;
import co.com.template.Repositories.entities.Status;
import lombok.Data;
import java.io.Serializable;
import java.util.Objects;

@Data
public class ObjectiveDTO implements Serializable {


    private Long objectiveId;
    private String objectiveDescribe;
    private Long objectiveUserId;
    private Long objectiveGroupId;
    private Status status;
    private ObjectiveType objectiveType;
    private Long objectiveQualify;
    private String objectiveObservations;
    private Long periodId;
    private Long alignObjectiveId;
    private Long alignGroupId;
    private Long alignUserId;

    public ObjectiveDTO(Objective objective) {
        this.objectiveId = objective.getObjectiveId();
        this.objectiveDescribe = objective.getObjectiveDescribe();
        this.objectiveUserId = objective.getObjectiveId();
        this.status = objective.getStatus();
        this.objectiveType = objective.getObjectiveType();
        this.periodId = Objects.isNull(objective.getPeriod()) ? null : objective.getPeriod().getPeriodId();
        this.alignObjectiveId = objective.getAlignObjectiveId();
        this.alignGroupId = Objects.isNull(objective.getAlignGroup()) ? null : objective.getAlignGroup().getGroupId();
        this.alignUserId = Objects.isNull(objective.getAlignUser()) ? null : objective.getAlignUser().getUserId();
    }
}
