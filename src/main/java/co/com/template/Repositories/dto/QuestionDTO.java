package co.com.template.Repositories.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionDTO implements Serializable {

    private Long questionId;
    private String describe;

    public QuestionDTO(Long questionId, String describe) {
        this.questionId = questionId;
        this.describe = describe;
    }
}
