package co.com.template.Repositories.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class CreateCommentDTO implements Serializable {


    private Long objectiveId;
    private Long commentTypeId;
    private Long userId;
    private Long statusId;
    private Boolean commentType;
    private String commentDescribe;

}
