package co.com.template.services;

import co.com.template.Repositories.*;
import co.com.template.Repositories.dto.CreatePollQuestionDTO;
import co.com.template.Repositories.dto.PollQuestionDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.entities.FollowClosePoll;
import co.com.template.Repositories.entities.Period;
import co.com.template.Repositories.entities.PollType;
import co.com.template.Repositories.entities.Question;
import co.com.template.utils.Constants;
import co.com.template.utils.TypePollEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class FollowClosePollService {

    private final QuestionRepository questionRepository;
    private final FollowClosePollRepository followClosePollRepository;
    private final PollTypeRepository pollTypeRepository;
    private final PeriodRepository periodRepository;

    public ResponseDTO createQuestionOfPoll(CreatePollQuestionDTO request){
        try{
            List<FollowClosePoll> list = followClosePollRepository.findByPollTypePollTypeId(request.getPollTypeId());
            FollowClosePoll questionExist = list.stream().filter( l -> l.getQuestion().getQuestionId().equals(request.getQuestionId())).findFirst().orElse(null);
            if(Objects.nonNull(questionExist))
                return new ResponseDTO(HttpStatus.OK, Constants.QUESTION_EXIST_ON_POLL, Boolean.FALSE);

            PollType type = pollTypeRepository.findByPollTypeId(request.getPollTypeId()==null ? TypePollEnum.FOLLOW.getId() : request.getPollTypeId());
            FollowClosePoll item = new FollowClosePoll();
            Question question = questionRepository.findByQuestionId(request.getQuestionId());
            item.setQuestion(question);
            item.setRequired(request.getIsRequired());
            item.setPollType(type);

            if(Objects.nonNull(request.getPollTypeId()) && !request.getPollTypeId().equals(TypePollEnum.FOLLOW.getId())) {
                Period period = periodRepository.findByPeriodId(request.getPeriodId());
                item.setPeriod(period);
            }
            followClosePollRepository.save(item);
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);

        }catch(Exception err){
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }
    public ResponseDTO getQuestionsOfPoll(Long pollTypeId){
        try{
            List<PollQuestionDTO> result = new ArrayList<>();

            Long type = pollTypeId == null ? TypePollEnum.FOLLOW.getId() : pollTypeId;
            List<FollowClosePoll> pollQuestions = followClosePollRepository.findByPollTypePollTypeId(type);
            for(FollowClosePoll pq : pollQuestions){
                result.add(new PollQuestionDTO(pq.getFollowClosePollId(), pq.getQuestion().getDescribe(),
                        pq.getRequired(), pq.getQuestion().getQuestionId(),
                        Objects.isNull(pq.getPeriod()) ? Constants.EMPTY_MESSAGE : pq.getPeriod().getDescribe()));
            }
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, result);

        }catch(Exception err){
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }

    public ResponseDTO deleteQuestionOfPoll(Long pollQuestionId) {
        try {
            FollowClosePoll followClosePoll = followClosePollRepository.findByFollowClosePollId(pollQuestionId);
            if (Objects.isNull(followClosePoll)) {
                return new ResponseDTO(HttpStatus.OK, Constants.OBJECT_NOT_EXISTS_ERROR, null);
            }
            followClosePollRepository.delete(followClosePoll);
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);

        } catch (Exception err) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }

}
