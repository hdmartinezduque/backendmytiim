package co.com.template.Repositories.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AnswerPollDTO implements Serializable {
    private Long pollId;
    private Long userId;
    private List<AnswerDTO> answers;
}
