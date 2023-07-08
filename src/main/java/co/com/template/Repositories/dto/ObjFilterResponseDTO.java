package co.com.template.Repositories.dto;

import co.com.template.Repositories.entities.ObjectiveType;
import co.com.template.Repositories.entities.Status;
import lombok.Data;

import java.io.Serializable;

@Data
public class ObjFilterResponseDTO implements Serializable {

    private Long objectiveId;
    private String objectiveDescribe;
    private Status status;
    private ObjectiveType objectiveType;
    private Long userId;
    private Long groupId;


}
