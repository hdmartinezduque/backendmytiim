package co.com.template.Repositories.dto;

import lombok.Data;

@Data
public class ObjectiveEditionDTO {

    private String objectiveDescribe;
    private Long objectiveTypeId;
    private Long periodId;

    private Long alignGroupId;
    private Long alignUserId;
    private Long alignObjectiveId;
}
