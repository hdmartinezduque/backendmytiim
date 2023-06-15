package co.com.template.Repositories.dto;

import co.com.template.Repositories.entities.Poll;
import co.com.template.utils.Constants;
import co.com.template.utils.Util;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class PollQuestionResponseDTO implements Serializable {

    private Long pollId;
    private String describe;
    private String pollCode;
    private String endDate;
    private List<QuestionResponseDTO> questions;


    public PollQuestionResponseDTO(Poll entityPoll){
        this.pollId = entityPoll.getPollId();
        this.describe = entityPoll.getDescribe();
        this.pollCode = entityPoll.getCode();
        this.endDate = Util.convertToDateTimeFormatted(entityPoll.getPeriod().getEndPoll(), Constants.DATE_FORMAT);

    }

}
