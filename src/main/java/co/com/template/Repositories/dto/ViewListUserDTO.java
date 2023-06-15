package co.com.template.Repositories.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class ViewListUserDTO implements Serializable {


    private Long userId;
    private String userName;
    private String userLastName;
    private Long statusId;



}