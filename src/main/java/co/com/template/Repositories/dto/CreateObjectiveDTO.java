package co.com.template.Repositories.dto;

import co.com.template.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateObjectiveDTO implements Serializable {

    @NotEmpty(message = Constants.OBJECTIVE_DESCRIBE_REQUIRED)
    private String objectiveDescribe;

    @NotNull(message = Constants.OBJECTIVE_TYPE_ID_REQUIRED)
    private Long objectiveTypeId;

    @NotNull(message = Constants.USER_ID_REQUIRED)
    private Long userId;

    @NotEmpty(message = Constants.COMMITMENT_REQUIRED)
    private List<CommitmentDTO> commitments;

    @NotNull(message = Constants.PERIOD_REQUIRED)
    private Long periodId;

    private Long alignGroupId;
    private Long alignUserId;
    private Long alignObjectiveId;
}
