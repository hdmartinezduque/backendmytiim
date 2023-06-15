package co.com.template.Repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class QuestionResponseDTO implements Serializable {
    private Long id;
    private String describe;
    private Long answerTypeId;
    private Boolean required;
    private List<String> options;



}
