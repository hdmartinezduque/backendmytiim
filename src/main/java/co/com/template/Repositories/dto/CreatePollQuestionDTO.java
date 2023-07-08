package co.com.template.Repositories.dto;

import lombok.Data;

@Data
public class CreatePollQuestionDTO {
    private Long questionId;
    private Boolean isRequired;
    private Long pollTypeId;
    private Long periodId;

}
