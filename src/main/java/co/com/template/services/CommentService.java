package co.com.template.services;

import co.com.template.Repositories.*;
import co.com.template.Repositories.dto.*;
import co.com.template.Repositories.entities.*;
import co.com.template.utils.Constants;
import co.com.template.utils.EmailServiceImpl;
import co.com.template.utils.StatusEnum;
import co.com.template.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
@Log4j2
public class CommentService {


    private final EmailServiceImpl emailService;

    private final CommentRepository commentRepository;

    private final ObjectiveRepository objectiveRepository;

    private final CommentTypeRepository commentTypeRepository;

    private final UserRepository userRepository;

    private final StatusRepository statusRepository;

    private final ConfigurationSystemService configurationService;

    private final CommentUserRepository commentUserRepository;

    private final CommentFeedbackRepository commentFeedbackRepository;


    public ResponseDTO getComment(Long objectiveId) {
        List<Comment> com = commentRepository.findTop20ByObjectiveObjectiveIdOrderByCommentDateDesc(objectiveId);
        List<CommentDTO> commentDTO = new ArrayList<>();


        for (Comment c : com) {
            CommentDTO commentModel = new CommentDTO();
            commentModel.setObjectiveId(c.getObjective().getObjectiveId());
            commentModel.setCommentId(c.getCommentId());
            commentModel.setUserId(c.getUserFrom().getUserId());
            commentModel.setUserName(c.getUserFrom().getUserName());
            commentModel.setUserLastName(c.getUserFrom().getUserLastName());
            String date = Util.convertToDateTimeHourFormatted(c.getCommentDate(), Constants.DATETIME_FORMAT);
            commentModel.setCommentDate(date);
            commentModel.setCommentTypeId(c.getCommentCommentType().getCommentTypeId());
            commentModel.setCommentDescribe(c.getCommentDescribe());


            commentDTO.add(commentModel);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, commentDTO);


    }


    public ResponseDTO setComment(CreateCommentDTO createCommentDTO) {
        try {
            CommentType commentType = commentTypeRepository.findByCommentTypeId(createCommentDTO.getCommentTypeId());
            Objective objective = objectiveRepository.findByObjectiveId(createCommentDTO.getObjectiveId());
            User user = userRepository.findByUserId(createCommentDTO.getUserId());
            Status status = statusRepository.findByStatusId(StatusEnum.ACTIVE_COMMENT.getId());


            Comment comment = new Comment();


            comment.setObjective(objective);
            comment.setCommentCommentType(commentType);
            comment.setUserFrom(user);
            comment.setStatus(status);
            comment.setCommentType(createCommentDTO.getCommentType());
            comment.setCommentDescribe(createCommentDTO.getCommentDescribe());

            commentRepository.save(comment);

            CommentUser commentUser = new CommentUser();

            commentUser.setUserTo(objective.getUser());
            commentUser.setCommentUserComment(comment);

            commentUserRepository.save(commentUser);

            if (createCommentDTO.getCommentTypeId().equals(Constants.DEFAULT_COMMENT_TYPE_ID)) {

                Map<String, Object> data = new HashMap<>();
                data.put(Constants.EMAIL_NAME, comment.getUserFrom().getUserName());
                data.put(Constants.EMAIL_LASTNAME, comment.getUserFrom().getUserLastName());
                data.put(Constants.EMAIL_DATE, Util.convertToDateTimeHourFormatted(comment.getCommentDate(),Constants.DATETIME_FORMAT));
                data.put(Constants.EMAIL_DESCRIBE, comment.getCommentDescribe());
                data.put(Constants.EMAIL_IMAGE_URL, configurationService.getConfigValue(Constants.IMAGE_URL_SYSTEM));
                data.put(Constants.EMAIL_URL, configurationService.getConfigValue(Constants.URL_SYSTEM)+Constants.EMAIL_FEEDBACK_URL);
                emailService.sendMail(data, objective.getUser().getUserEmail(), Constants.SUBJECT_MESSAGE + comment.getUserFrom().getUserName(), Constants.INDEX_TEMPLATE,null);
                return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, createCommentDTO);
            }
        } catch (Exception e) {

            return new ResponseDTO(HttpStatus.BAD_REQUEST, e.getMessage(), null);

        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);

    }

    public ResponseDTO createCommentRecognition(CreateRecognitionDTO createRecognitionDTO) {
        try {
            CommentType commentType = commentTypeRepository.findByCommentTypeId(Constants.DEFAULT_RECOGNITION_STATUS_ID);
            Status status = statusRepository.findByStatusId(StatusEnum.ACTIVE_COMMENT.getId());
            User userFrom = userRepository.findByUserId(createRecognitionDTO.getUserId());


            if (createRecognitionDTO.getType().equals(Constants.USER_TYPE)) {
               this.createCommentRecognitionEmail(createRecognitionDTO, commentType, status, userFrom);


            } else if (createRecognitionDTO.getType().equals(Constants.GROUP_TYPE)) {
                List<Long> ids = new ArrayList<>();
                    createRecognitionDTO.getIds().forEach(id -> {
                    List<User> users = userRepository.findByGroupGroupId(id);
                  ids.addAll(users.stream().map(User::getUserId).toList());

                });
                createRecognitionDTO.setIds(ids);
                this.createCommentRecognitionEmail(createRecognitionDTO, commentType, status, userFrom);

            }

        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);

    }

    public void createCommentRecognitionEmail(CreateRecognitionDTO createRecognitionDTO,
                                                     CommentType commentType, Status status, User userFrom) {

        try {
            Comment comm = new Comment();
            comm.setCommentCommentType(commentType);
            comm.setUserFrom(userFrom);
            comm.setStatus(status);
            comm.setCommentType(Boolean.TRUE);
            comm.setCommentDescribe(createRecognitionDTO.getCommentDescribe());
            comm.setIsGroup( createRecognitionDTO.getType().equals(Constants.USER_TYPE) ? Boolean.FALSE : Boolean.TRUE );

            commentRepository.save(comm);

            List<User> users = userRepository.findByUserIdIn(createRecognitionDTO.getIds());
            List<CommentUser> list = new ArrayList<>();
            for(User userItem : users){
                CommentUser commentUser = new CommentUser();
                commentUser.setCommentUserComment(comm);
                commentUser.setUserTo(userItem);
                list.add(commentUser);
            }
           commentUserRepository.saveAll(list);


            Map<String, Object> data = new HashMap<>();
            data.put(Constants.EMAIL_NAME, comm.getUserFrom().getUserName());
            data.put(Constants.EMAIL_LASTNAME, comm.getUserFrom().getUserLastName());
            data.put(Constants.EMAIL_DATE, Util.convertToDateTimeHourFormatted(comm.getCommentDate(),Constants.DATETIME_FORMAT));
            data.put(Constants.EMAIL_DESCRIBE, comm.getCommentDescribe());
            data.put(Constants.EMAIL_IMAGE_URL, configurationService.getConfigValue(Constants.IMAGE_URL_SYSTEM));
            data.put(Constants.EMAIL_URL, configurationService.getConfigValue(Constants.URL_SYSTEM)+Constants.EMAIL_FEEDBACK_URL);
            List<String> emails = users.stream().map(User::getUserEmail).toList();
            emailService.sendMail(data, null, Constants.SUBJECT_MESSAGE + comm.getUserFrom().getUserName(), Constants.INDEX_TEMPLATE, emails);

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
     public ResponseDTO getCommentRecognition(RecognitionFilterDTO recognitionFilterDTO) {
         try {

             List<Comment> comments = commentRepository.findByCommentCommentTypeCommentTypeIdOrderByCommentDateDesc(Constants.DEFAULT_RECOGNITION_STATUS_ID);


             if (recognitionFilterDTO.getGroupId() != null) {
                 comments = comments.stream()
                         .filter(com -> com.getUserFrom().getGroup().getGroupId().equals(recognitionFilterDTO.getGroupId()))
                         .collect(Collectors.toList());

             }
             if (recognitionFilterDTO.getUserId() != null) {
                 comments = comments.stream()
                         .filter(com -> com.getUserFrom().getUserId().equals(recognitionFilterDTO.getUserId()))
                         .collect(Collectors.toList());

             }

             if (recognitionFilterDTO.getCommentDateInit() != null && recognitionFilterDTO.getCommentDateFinal() != null) {
                 comments = comments.stream()
                         .filter(com -> com.getCommentDate().isAfter(recognitionFilterDTO.getCommentDateInit().atStartOfDay()) &&
                                 com.getCommentDate().isBefore(recognitionFilterDTO.getCommentDateFinal().plusDays(Constants.INCREMENTAL_VALUE).atStartOfDay()))
                         .collect(Collectors.toList());

             }

            List<CommentFilterResponseDTO> listComment = new ArrayList<>();
             for (Comment c : comments) {
                 CommentFilterResponseDTO commentModel = new CommentFilterResponseDTO();
                 long commentFeedbackCount = commentFeedbackRepository.findByCommentCommentId(c.getCommentId()).stream().count();

                 commentModel.setCommentId(c.getCommentId());
                 commentModel.setUserId(c.getUserFrom().getUserId());
                 commentModel.setUserName(c.getUserFrom().getUserName());
                 commentModel.setUserLastName(c.getUserFrom().getUserLastName());
                 String date = Util.convertToDateTimeHourFormatted(c.getCommentDate(), Constants.DATETIME_FORMAT);
                 commentModel.setCommentTypeId(c.getCommentCommentType().getCommentTypeId());
                 commentModel.setCommentDate(date);
                 commentModel.setCommentDescribe(c.getCommentDescribe());
                 commentModel.setCountFeedback(commentFeedbackCount);

                 List<CommentUser> users = commentUserRepository.findByCommentUserCommentCommentId(commentModel.getCommentId());
                 List<Long> ids = users.stream().map(CommentUser::getUserTo).map(User::getUserId).collect(Collectors.toList());
                 List<User> userList = userRepository.findByUserIdIn(ids);
                 List<Group> groupList = userList.stream().map(User::getGroup).distinct().toList();
                 if(c.getIsGroup())
                    commentModel.setUser(Constants.AT_VALUE+groupList.stream().map(Group::getGroupDescribe)
                             .collect(Collectors.joining(Constants.COMMA_SEPARATOR+Constants.SPACE_SEPARATOR+Constants.AT_VALUE)));
                 else
                    commentModel.setUser(Constants.AT_VALUE+userList.stream().map(User::getUser)
                         .collect(Collectors.joining(Constants.COMMA_SEPARATOR+Constants.SPACE_SEPARATOR+Constants.AT_VALUE)));

                 listComment.add(commentModel);
             }
             listComment = listComment.stream().skip((recognitionFilterDTO.getSet()-Constants.ONE_VALUE)*Constants.ONE_HUNDRED_LIMIT).limit(Constants.ONE_HUNDRED_LIMIT).toList();
             return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, listComment);

         }catch (Exception err){

             return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
         }

     }



   }

