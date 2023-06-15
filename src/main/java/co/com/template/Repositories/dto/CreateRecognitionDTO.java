package co.com.template.Repositories.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateRecognitionDTO implements Serializable {


    private Long type;
    private Long userId;
    private String commentDescribe;
    private List<Long> ids;



}
