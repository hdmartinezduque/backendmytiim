package co.com.template.Repositories.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ObjectiveFilterDTO implements Serializable {

    private Long set;
    private Long statusId;
    private Long objectiveTypeId;
    private Long userId;
    private Long groupId;


}
