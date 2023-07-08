package co.com.template.services;


import co.com.template.Repositories.FollowClosePollRepository;
import co.com.template.Repositories.PollQuestionRepository;
import co.com.template.Repositories.PollRepository;
import co.com.template.Repositories.PollUserRepository;
import co.com.template.Repositories.StatusRepository;
import co.com.template.Repositories.UserRepository;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.entities.*;
import co.com.template.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@EnableScheduling
public class NotificationService {

    private final PeriodService periodService;
    private final PollRepository pollRepository;
    private final FollowClosePollRepository followClosePollRepository;
    private final PollUserRepository pollUserRepository;
    private final StatusRepository statusRepository;
    private final PollQuestionRepository pollQuestionRepository;
    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;
    private final NotificationLogService notificationLogService;

    @Scheduled(cron = "${cron.job.schedule}")
    public ResponseDTO createPollNotifications(){
        try{
            notificationLogService.saveLog(Constants.LOG_START, TypeNotificationEnum.AUTOMATIC_POLLS.getId());
            this.inactivePreviousPolls();
            Period period = periodService.getActualPeriod();
            if(!existsActivePoll(TypePollEnum.CLOSE.getStart())){
                Period previousPeriod = periodService.getPreviousPeriod();
                Poll newPoll = new Poll(previousPeriod, statusRepository.findByStatusId(StatusEnum.ACTIVE_POLL.getId()),
                        TypePollEnum.CLOSE.getStart().concat(previousPeriod.getDescribe().replaceAll(Constants.DASH_VALUE,Constants.EMPTY_MESSAGE)),
                        TypePollEnum.CLOSE.getDescribe(), previousPeriod.getStartPoll(), previousPeriod.getEndPoll(), null);
                this.createPollQuestionsToNewPoll(newPoll, TypePollEnum.CLOSE.getId());
            }
            if(!existsActivePoll(TypePollEnum.FOLLOW.getStart())){
                Poll poll = pollRepository.findTop1ByPeriodPeriodIdAndCodeStartsWithOrderByIndexDesc(period.getPeriodId(), TypePollEnum.FOLLOW.getStart());
                if(Objects.isNull(poll)){
                    Poll newPoll = new Poll(period, statusRepository.findByStatusId(StatusEnum.ACTIVE_POLL.getId()),
                            TypePollEnum.FOLLOW.getStart().concat(Constants.INCREMENTAL_VALUE.toString()).concat(period.getDescribe().replaceAll(Constants.DASH_VALUE,Constants.EMPTY_MESSAGE)),
                            TypePollEnum.FOLLOW.getDescribe(), period.getStartPeriod(), period.getStartPeriod().plusDays(Constants.INCREMENTAL_DAYS), Constants.INCREMENTAL_VALUE);
                    this.createPollQuestionsToNewPoll(newPoll, TypePollEnum.FOLLOW.getId());
                }
                else{
                    if(LocalDate.now().isAfter((poll.getEnd()))){
                        Poll newPoll = new Poll(period, statusRepository.findByStatusId(StatusEnum.ACTIVE_POLL.getId()),
                                TypePollEnum.FOLLOW.getStart().concat(String.valueOf(Integer.sum(poll.getIndex(),Constants.INCREMENTAL_VALUE))).
                                                concat(period.getDescribe().replaceAll(Constants.DASH_VALUE,Constants.EMPTY_MESSAGE)),
                                TypePollEnum.FOLLOW.getDescribe(), poll.getEnd().plusDays(Constants.INCREMENTAL_VALUE),
                                poll.getEnd().plusDays(Constants.INCREMENTAL_VALUE).plusDays(Constants.INCREMENTAL_DAYS),
                                Integer.sum(poll.getIndex(),Constants.INCREMENTAL_VALUE));
                        this.createPollQuestionsToNewPoll(newPoll, TypePollEnum.FOLLOW.getId());
                    }
                }
            }
            notificationLogService.saveLog(Constants.LOG_END, TypeNotificationEnum.AUTOMATIC_POLLS.getId());
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);
        }catch(Exception err){
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }
    private Boolean existsActivePoll(String typePoll){
        return Objects.nonNull(pollRepository.findByStatusStatusIdAndCodeStartsWith(StatusEnum.ACTIVE_POLL.getId(), typePoll));
    }

    private void inactivePreviousPolls(){
        LocalDate today =LocalDate.now();
        List<Poll> polls = pollRepository.findByStatusStatusIdAndEndLessThan(StatusEnum.ACTIVE_POLL.getId(), today);
        if(!CollectionUtils.isEmpty(polls)){
            Status status = statusRepository.findByStatusId(StatusEnum.INACTIVE_POLL.getId());
            polls.forEach(p -> p.setStatus(status));
            pollRepository.saveAll(polls);
        }
    }

    @Async
    private void sendNotifications(List<User> users){
        users.forEach(user -> {
            try {
                notificationLogService.saveLog(String.format(Constants.LOG_SEND_EMAIL, user.getUserEmail()), TypeNotificationEnum.AUTOMATIC_POLLS.getId());
                Map<String, Object> data = new HashMap<>();
                data.put(Constants.EMAIL_NAME, user.getUserName());
                data.put(Constants.EMAIL_POLL_URL, Constants.URL_POLL);
                data.put(Constants.EMAIL_IMAGE_URL, Constants.URL_IMAGE);
                emailService.sendMail(data, user.getUserEmail(), Constants.SUBJECT_NOTIFICATION_EMAIL, Constants.NOTIFICATION_TEMPLATE);
            } catch (Exception e) {
                notificationLogService.saveLog(Constants.LOG_SEND_EMAIL_ERROR.concat(user.getUserEmail()).concat(Constants.COMMA_SEPARATOR).concat(e.getMessage())
                        , TypeNotificationEnum.AUTOMATIC_POLLS.getId());
            }
        });
    }

    private void saveNotifications(Poll poll){
        try{
            List<User> users = userRepository.findByStatusStatusIdAndActivatedDateLessThan(
                    StatusEnum.ACTIVE_USER.getId(), LocalDate.now().minusDays(Constants.INCREMENTAL_DAYS));
            Status statusInProgress = statusRepository.findByStatusId(StatusEnum.IN_PROGRESS_POLL_USER.getId());
            List<PollUser> userNotifications = new ArrayList<>();
            users.forEach(user -> {
                PollUser pollUser = new PollUser();
                pollUser.setUser(user);
                pollUser.setPoll(poll);
                pollUser.setCreatedDate(LocalDate.now());
                pollUser.setStatus(statusInProgress);
                userNotifications.add(pollUser);
            });
            pollUserRepository.saveAll(userNotifications);
            notificationLogService.saveLog(String.format(Constants.LOG_SAVE_NOTIFICATION,poll.getCode()).concat(users
                    .stream().map(User::getUserEmail).collect(Collectors.joining(Constants.COMMA_SEPARATOR)))
                    , TypeNotificationEnum.AUTOMATIC_POLLS.getId());
            this.sendNotifications(users);
        }catch(Exception err){
            notificationLogService.saveLog(String.format(Constants.LOG_SAVE_NOTIFICATION_ERROR,poll.getCode()).concat(err.getMessage())
                    , TypeNotificationEnum.AUTOMATIC_POLLS.getId());
        }
    }

    @Transactional
    private void createPollQuestionsToNewPoll(Poll newPoll, Long typePollId){
        try {
            pollRepository.save(newPoll);
            List<PollQuestion> pollQuestions = new ArrayList<>();
            List<FollowClosePoll> items = followClosePollRepository.findByPollTypePollTypeId(typePollId);
            items.forEach(i -> pollQuestions.add(new PollQuestion(null, newPoll, i.getQuestion(), i.getRequired())));
            pollQuestionRepository.saveAll(pollQuestions);
            notificationLogService.saveLog(Constants.LOG_SAVE_POLL_QUESTIONS+newPoll.getCode(), TypeNotificationEnum.AUTOMATIC_POLLS.getId());
            this.saveNotifications(newPoll);
        }catch(Exception err){
            notificationLogService.saveLog(Constants.LOG_SAVE_POLL_QUESTIONS_ERROR.concat(newPoll.getCode()).concat(Constants.DASH_VALUE)
                    .concat(err.getMessage()), TypeNotificationEnum.AUTOMATIC_POLLS.getId());
        }
    }
}
