package co.com.template.Repositories.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnswerDTO implements Serializable {

    private Long questionId;
    private String value;
}
