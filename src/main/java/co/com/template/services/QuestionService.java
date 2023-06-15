package co.com.template.services;

import co.com.template.Repositories.QuestionRepository;
import co.com.template.Repositories.dto.QuestionDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.entities.Question;
import co.com.template.utils.Constants;
import co.com.template.utils.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public ResponseDTO getActiveQuestions(){
        try{
            List<QuestionDTO> result = new ArrayList<>();
            List<Question> questions = questionRepository.findByStatusId(StatusEnum.ACTIVE_QUESTION.getId());
            questions.stream().forEach( question -> {
                result.add(new QuestionDTO(question.getQuestionId(), question.getDescribe()));
            });
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, result);

        }catch(Exception err){
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }
}
