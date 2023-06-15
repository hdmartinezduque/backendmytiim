package co.com.template.services;


import co.com.template.Repositories.FollowClosePollRepository;
import co.com.template.Repositories.PollRepository;
import co.com.template.Repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class FollowClosePollService {

    private final FollowClosePollRepository followClosePollRepository;
    private final QuestionRepository questionRepository;
    private final PollRepository pollRepository;


}
