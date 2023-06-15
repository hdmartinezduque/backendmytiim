package co.com.template.Repositories;

import co.com.template.Repositories.entities.Poll;
import co.com.template.Repositories.entities.PollQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollQuestionRepository extends JpaRepository<PollQuestion, Long> {

    PollQuestion findByPollQuestionId(Long pollQuestionId);
    PollQuestion findByPollPollIdAndQuestionQuestionId(Long pollId, Long questionID);

    List<PollQuestion> findByPollPollId(Long pollId);
}
