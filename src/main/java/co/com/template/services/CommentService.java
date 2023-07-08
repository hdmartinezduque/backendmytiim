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

    private final GroupRepository groupRepository;


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
            comment.setUserTo(user);
            comment.setStatus(status);
            comment.setCommentType(createCommentDTO.getCommentType());
            comment.setCommentDescribe(createCommentDTO.getCommentDescribe());

            commentRepository.save(comment);

            if (createCommentDTO.getCommentTypeId().equals(Constants.DEFAULT_COMMENT_TYPE_ID)) {

                Map<String, Object> data = new HashMap<>();
                data.put(Constants.EMAIL_NAME, comment.getUserFrom().getUserName());
                data.put(Constants.EMAIL_LASTNAME, comment.getUserFrom().getUserLastName());
                data.put(Constants.EMAIL_DATE, Util.convertToDateTimeHourFormatted(comment.getCommentDate(),Constants.DATETIME_FORMAT));
                data.put(Constants.EMAIL_DESCRIBE, comment.getCommentDescribe());
                emailService.sendMail(data, objective.getUser().getUserEmail(), Constants.SUBJECT_MESSAGE + comment.getUserFrom().getUserName(), Constants.INDEX_TEMPLATE);
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
                createRecognitionDTO.getIds().stream().forEach(id -> {
                    User idUser = userRepository.findByUserId(id);
                    this.createCommentRecognitionEmail(idUser, createRecognitionDTO, commentType, status, userFrom);

                });
            } else if (createRecognitionDTO.getType().equals(Constants.GROUP_TYPE)) {
                createRecognitionDTO.getIds().stream().forEach(id -> {
                    List<User> idUser = userRepository.findByGroupGroupId(id);
                    idUser.stream().forEach(user -> {
                        this.createCommentRecognitionEmail(user, createRecognitionDTO, commentType, status, userFrom);
                    });

                });
            }

        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);

    }

    public void createCommentRecognitionEmail(User user, CreateRecognitionDTO createRecognitionDTO,
                                                     CommentType commentType, Status status, User userFrom) {
        try {
            Comment comm = new Comment();
            comm.setCommentCommentType(commentType);
            comm.setUserFrom(userFrom);
            comm.setUserTo(user);


            comm.setStatus(status);
            comm.setCommentType(Boolean.TRUE);
            comm.setCommentDescribe(createRecognitionDTO.getCommentDescribe());

            commentRepository.save(comm);
            Map<String, Object> data = new HashMap<>();
            data.put(Constants.EMAIL_NAME, comm.getUserFrom().getUserName());
            data.put(Constants.EMAIL_LASTNAME, comm.getUserFrom().getUserLastName());
            data.put(Constants.EMAIL_DATE, Util.convertToDateTimeHourFormatted(comm.getCommentDate(),Constants.DATETIME_FORMAT));
            data.put(Constants.EMAIL_DESCRIBE, comm.getCommentDescribe());
            emailService.sendMail(data, comm.getUserTo().getUserEmail(), Constants.SUBJECT_MESSAGE + comm.getUserFrom().getUserName(), Constants.INDEX_TEMPLATE);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
     public ResponseDTO getCommentRecognition(RecognitionFilterDTO recognitionFilterDTO) {
         try {

             List<Comment> comments = commentRepository.findTop20ByCommentCommentTypeCommentTypeIdOrderByCommentDateDesc(Constants.DEFAULT_RECOGNITION_STATUS_ID);


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
                                 com.getCommentDate().isBefore(recognitionFilterDTO.getCommentDateFinal().atStartOfDay()))
                         .collect(Collectors.toList());

             }

            List<CommentFilterResponseDTO> listComment = new ArrayList<>();
             for (Comment c : comments) {
                 CommentFilterResponseDTO commentModel = new CommentFilterResponseDTO();

                 commentModel.setCommentId(c.getCommentId());
                 commentModel.setUserId(c.getUserFrom().getUserId());
                 commentModel.setUserName(c.getUserFrom().getUserName());
                 commentModel.setUserLastName(c.getUserFrom().getUserLastName());
                 String date = Util.convertToDateTimeHourFormatted(c.getCommentDate(), Constants.DATETIME_FORMAT);
                 commentModel.setCommentTypeId(c.getCommentCommentType().getCommentTypeId());
                 commentModel.setCommentDate(date);
                 commentModel.setCommentDescribe(c.getCommentDescribe());
                 commentModel.setUser(c.getUserTo().getUser());

                 listComment.add(commentModel);
             }
             listComment = listComment.stream().skip((recognitionFilterDTO.getSet()-Constants.ONE_VALUE)*Constants.ONE_HUNDRED_LIMIT).limit(Constants.ONE_HUNDRED_LIMIT).toList();
             return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, listComment);

         }catch (Exception err){

             return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
         }

     }



   }

