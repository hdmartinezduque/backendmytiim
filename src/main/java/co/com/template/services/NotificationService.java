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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;

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
    private final ConfigurationSystemService configurationService;

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
                            TypePollEnum.FOLLOW.getDescribe(), period.getStartPeriod(),
                            period.getStartPeriod().plusDays(Long.parseLong(configurationService.getConfigValue(Constants.SYSTEM_INCREMENTAL_DAYS))),
                            Constants.INCREMENTAL_VALUE);
                    this.createPollQuestionsToNewPoll(newPoll, TypePollEnum.FOLLOW.getId());
                }
                else{
                    if(LocalDate.now().isAfter((poll.getEnd()))){
                        Poll newPoll = new Poll(period, statusRepository.findByStatusId(StatusEnum.ACTIVE_POLL.getId()),
                                TypePollEnum.FOLLOW.getStart().concat(String.valueOf(Integer.sum(poll.getIndex(),Constants.INCREMENTAL_VALUE))).
                                                concat(period.getDescribe().replaceAll(Constants.DASH_VALUE,Constants.EMPTY_MESSAGE)),
                                TypePollEnum.FOLLOW.getDescribe(), poll.getEnd().plusDays(Constants.INCREMENTAL_VALUE),
                                poll.getEnd().plusDays(Constants.INCREMENTAL_VALUE).plusDays(Long.parseLong(configurationService.getConfigValue(Constants.SYSTEM_INCREMENTAL_DAYS))),
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

    private void sendNotifications(List<User> users, Poll poll){
        users.forEach(user -> {
            try {
                notificationLogService.saveLog(String.format(Constants.LOG_SEND_EMAIL, user.getUserEmail()), TypeNotificationEnum.AUTOMATIC_POLLS.getId());
                Map<String, Object> data = new HashMap<>();
                data.put(Constants.EMAIL_NAME, user.getUserName());
                data.put(Constants.EMAIL_URL, configurationService.getConfigValue(Constants.URL_SYSTEM)+Constants.URL_POLL);
                data.put(Constants.EMAIL_IMAGE_URL, configurationService.getConfigValue(Constants.IMAGE_URL_SYSTEM));
                emailService.sendMail(data, user.getUserEmail(), String.format(Constants.SUBJECT_NOTIFICATION_EMAIL,poll.getCode(),poll.getDescribe().toLowerCase()),
                        Constants.NOTIFICATION_TEMPLATE, null);
            } catch (Exception e) {
                notificationLogService.saveLog(Constants.LOG_SEND_EMAIL_ERROR.concat(user.getUserEmail()).concat(Constants.COMMA_SEPARATOR).concat(e.getMessage())
                        , TypeNotificationEnum.AUTOMATIC_POLLS.getId());
            }
        });
    }

    private void saveNotifications(Poll poll){
        try{
            List<User> users = userRepository.findByStatusStatusIdAndActivatedDateLessThan(StatusEnum.ACTIVE_USER.getId(),
                    LocalDate.now().minusDays(Long.parseLong(configurationService.getConfigValue(Constants.SYSTEM_INCREMENTAL_DAYS))));
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
            notificationLogService.saveLog(String.format(Constants.LOG_SAVE_NOTIFICATION,poll.getCode()), TypeNotificationEnum.AUTOMATIC_POLLS.getId());
            this.sendNotifications(users, poll);
        }catch(Exception err){
            notificationLogService.saveLog(String.format(Constants.LOG_SAVE_NOTIFICATION_ERROR,poll.getCode()).concat(err.getMessage())
                    , TypeNotificationEnum.AUTOMATIC_POLLS.getId());
        }
    }

    @Transactional
    private void createPollQuestionsToNewPoll(Poll newPoll, Long typePollId){
        try {
            List<PollQuestion> pollQuestions = new ArrayList<>();
            List<FollowClosePoll> items = followClosePollRepository.findByPollTypePollTypeId(typePollId);
            if(CollectionUtils.isEmpty(items)){
                notificationLogService.saveLog(Constants.NO_CONFIGURATION_POLL_QUESTIONS+newPoll.getCode(), TypeNotificationEnum.AUTOMATIC_POLLS.getId());
            }
            else {
                items.forEach(i -> pollQuestions.add(new PollQuestion(null, newPoll, i.getQuestion(), i.getRequired())));
                pollRepository.save(newPoll);
                pollQuestionRepository.saveAll(pollQuestions);
                notificationLogService.saveLog(Constants.LOG_SAVE_POLL_QUESTIONS + newPoll.getCode(), TypeNotificationEnum.AUTOMATIC_POLLS.getId());
                this.saveNotifications(newPoll);
            }
        }catch(Exception err){
            notificationLogService.saveLog(Constants.LOG_SAVE_POLL_QUESTIONS_ERROR.concat(newPoll.getCode()).concat(Constants.DASH_VALUE)
                    .concat(err.getMessage()), TypeNotificationEnum.AUTOMATIC_POLLS.getId());
        }
    }
}
