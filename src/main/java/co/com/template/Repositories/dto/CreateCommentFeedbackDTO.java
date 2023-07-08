package co.com.template.Repositories.dto;


import lombok.Data;

import java.io.Serializable;


@Data
public class CreateCommentFeedbackDTO implements Serializable{


    private Long commentId;
    private Long userId;
    private String commentFeedbackDescribe;


}
