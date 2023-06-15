package co.com.template.Repositories.dto;

import co.com.template.Repositories.entities.PollQuestion;
import lombok.Data;

@Data
public class CreatePollQuestionDTO {
    private Long questionId;
    private Boolean isRequired;
    private Long pollTypeId;
    private Long periodId;

}
