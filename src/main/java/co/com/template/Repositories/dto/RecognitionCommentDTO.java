package co.com.template.Repositories.dto;


import lombok.Data;

import java.io.Serializable;


@Data
public class RecognitionCommentDTO implements Serializable{



    private Long commentId;
    private Long userId;
    private String userName;
    private String userLastName;
    private String commentDate;
    private Long commentTypeId;
    private String commentDescribe;
    public String user;




}
