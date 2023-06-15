package co.com.template.services;

import co.com.template.Repositories.*;
import co.com.template.Repositories.dto.*;
import co.com.template.Repositories.entities.*;
import co.com.template.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class PollService {

    private final PollRepository pollRepository;
    private final PollQuestionRepository pollQuestionRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final DetailPollRepository detailPollRepository;

    public ResponseDTO getQuestionsOfPoll(Long pollId){
        try{
            Poll poll = pollRepository.findByPollId(pollId);
            List<PollQuestion> questions = pollQuestionRepository.findByPollPollId(poll.getPollId());
            PollQuestionResponseDTO response = new PollQuestionResponseDTO(poll);
            List<QuestionResponseDTO> questionList= new ArrayList<>();
            questions.stream().forEach(q ->{
                List<String> options = Objects.isNull(q.getQuestion().getOptions())? null :  Arrays.stream(q.getQuestion().getOptions().split(Constants.COMMA_SEPARATOR)).toList();
                QuestionResponseDTO questResponse = new QuestionResponseDTO(q.getQuestion().getQuestionId(),q.getQuestion().getDescribe(),
                        q.getQuestion().getAnswerTypeId(), q.getIsRequired(), options);
                questionList.add(questResponse);
            });
            response.setQuestions(questionList);
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, response);
        }catch(Exception err){
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }

    public ResponseDTO saveAnswersPoll(AnswerPollDTO request){
        try{
            Poll poll = pollRepository.findByPollId(request.getPollId());
            if(Objects.isNull(poll))
                return new ResponseDTO(HttpStatus.BAD_REQUEST, Constants.POLL_NOT_EXISTS_ERROR, null);

            User user = userRepository.findByUserId(request.getUserId());
            List<DetailPoll> answers = new ArrayList<>();
            request.getAnswers().stream().forEach(item -> {
                Question question = questionRepository.findByQuestionId(item.getQuestionId());
                DetailPoll answer = new DetailPoll(poll,question, item.getValue(), user, LocalDate.now());
                answers.add(answer);
            });
            detailPollRepository.saveAll(answers);
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);
        }catch(Exception err){
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }
}
