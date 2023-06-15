package co.com.template.Repositories.dto;

import lombok.Data;

@Data
public class PollQuestionDTO {
    private Long id;
    private String questionDescribe;
    private Long questionId;
    private Boolean isRequired;

    public PollQuestionDTO(Long id, String questionDescribe, Boolean isRequired,Long questionId) {
        this.id = id;
        this.questionDescribe = questionDescribe;
        this.isRequired = isRequired;
        this.questionId = questionId;
    }
}
