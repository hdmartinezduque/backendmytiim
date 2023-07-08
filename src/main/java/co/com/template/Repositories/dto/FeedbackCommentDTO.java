package co.com.template.Repositories.dto;


import lombok.Data;

import java.io.Serializable;


@Data
public class FeedbackCommentDTO implements Serializable{



    private Long commentFeedbackId;
    private Long userId;
    private String userName;
    private String userLastName;
    private String commentFeedbackDate;
    private Long commentFeedbackType;
    private String commentFeedbackDescribe;




}
