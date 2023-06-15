package co.com.template.Repositories.dto;


import lombok.Data;

import java.io.Serializable;


@Data
public class CommentDTO implements Serializable{


    private Long objectiveId;
    private Long commentId;
    private Long userId;
    private String userName;
    private String userLastName;
    private String commentDate;
    private Long commentTypeId;
    private String commentDescribe;




}
