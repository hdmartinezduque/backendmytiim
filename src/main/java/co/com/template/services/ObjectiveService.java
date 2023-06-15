package co.com.template.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import co.com.template.Repositories.*;
import co.com.template.Repositories.dto.*;
import co.com.template.Repositories.entities.*;
import co.com.template.utils.Constants;
import co.com.template.utils.StatusEnum;
import jakarta.persistence.Entity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.com.template.exception.CustomException;
import org.springframework.http.HttpStatus;

@Service
@Transactional
@Log4j2
public class ObjectiveService {

    @Autowired
    private ObjectiveRepository objectiveRepository;

    @Autowired
    private ObjectiveTypeRepository objectiveTypeRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommitmentRepository commitmentRepository;

    @Autowired
    private MeasureRepository measureRepository;

    public ResponseDTO getObjective() {
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, objectiveRepository.findAll());
    }

    public ResponseDTO setObjective(CreateObjectiveDTO request){
        try{
            ObjectiveType type = objectiveTypeRepository.findByObjectiveTypeId(request.getObjectiveTypeId());
            Status status = statusRepository.findByStatusId(StatusEnum.ACTIVE_OBJECTIVE.getId());
            User user = userRepository.findByUserId(request.getUserId());
            Objective entity = new Objective();
            entity.setObjectiveDescribe(request.getObjectiveDescribe());
            entity.setObjectiveQualify(Constants.MIN_VALUE_QUALIFY);
            entity.setObjectiveType(type);
            entity.setStatus(status);
            entity.setUser(user);
            entity.setGroup(user.getGroup());

            entity = objectiveRepository.save(entity);
            long objectiveId = entity.getObjectiveId();

            List<Commitment> commitmentsEntity = new ArrayList<>();
            List<CommitmentDTO> commitmentDTOS = request.getCommitments();

            for (CommitmentDTO c : commitmentDTOS) {
                Commitment commitment = new Commitment();
                commitment.setObjective(entity);
                commitment.setCommitmentDescribe(c.getCommitmentDescribe());
                commitment.setCommitmentDate(LocalDate.parse(c.getCommitmentDate().substring(Constants.ZERO_INDEX,Constants.TEN_INDEX)));
                commitment.setCommitmentGoal(c.getCommitmentGoal());

                Long measureId = c.getMeasureId();
                Measure measure = measureRepository.findByMeasureId(measureId);
                commitment.setMeasure(measure);

                commitment.setCommitmentAdvance(c.getCommitmentAdvance());

                commitmentsEntity.add(commitment);

            }

            entity.setCommitments(commitmentsEntity);

            commitmentRepository.saveAll(commitmentsEntity);

            return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, Boolean.TRUE);
        }catch(Exception err){
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(), null);
        }
    }


    public ResponseDTO updateObjective(Long objectiveId, ObjectiveEditionDTO objective) {

        Objective entity = objectiveRepository.findByObjectiveId(objectiveId);

        if (Objects.isNull(entity)) {
            return new ResponseDTO(HttpStatus.OK,Constants.OBJECT_NOT_EXISTS_ERROR, null);
        }

        ObjectiveType type = objectiveTypeRepository.findByObjectiveTypeId(objective.getObjectiveTypeId());

        entity.setObjectiveDescribe(objective.getObjectiveDescribe());
        entity.setObjectiveType(type);
        objectiveRepository.save(entity);
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, Boolean.TRUE);
    }


    public ResponseDTO deleteObjective(Long id)  {
        objectiveRepository.deleteById(id);
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, Boolean.TRUE);
    }

    public ResponseDTO getObjectiveById(Long objectiveId) {
        Objective objective = objectiveRepository.findByObjectiveId(objectiveId);
        if (Objects.isNull(objectiveId)) {
            throw new CustomException(Constants.OBJECT_NOT_EXISTS_ERROR, HttpStatus.OK);
        }
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, new ObjetiveDTO(objective));
    }

    public ResponseDTO getObjectiveForGroupAndForUser(Long GroupGroupId, Long UserUserId) {
        List<Objective> list = objectiveRepository.findByGroupGroupIdAndUserUserId(GroupGroupId, UserUserId);
        List<ObjectiveAlignedDTO> dtoList = new ArrayList<>();
        for (Objective obj : list) {
            ObjectiveAlignedDTO objAligDTO = new ObjectiveAlignedDTO();
            objAligDTO.setObjectiveId(obj.getObjectiveId());
            objAligDTO.setObjectiveDescribe(obj.getObjectiveDescribe());
            dtoList.add(objAligDTO);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, dtoList);
    }



    public ResponseDTO getObjectiveForUser(Long UserUserId) {
        List<Objective> list = objectiveRepository.findByUserUserId(UserUserId);
        List<ObjectiveUserDTO> dtoList = new ArrayList<>();
        for (Objective obj : list) {
            ObjectiveUserDTO objectiveUserDTO = new ObjectiveUserDTO();
            objectiveUserDTO.setUserId(obj.getUser().getUserId());
            objectiveUserDTO.setUserName(obj.getUser().getUserName());
            objectiveUserDTO.setUserLastName(obj.getUser().getUserLastName());
            dtoList.add(objectiveUserDTO);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, dtoList);
    }



    public ResponseDTO getObjectiveForGroup(Long GroupGroupId) {
        List<Objective> list = objectiveRepository.findByGroupGroupId(GroupGroupId);
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, list);
    }



    public ResponseDTO closeObjective(Long objectiveId, CloseObjectiveDTO objective) {
        try{
            Objective entity = objectiveRepository.findByObjectiveId(objectiveId);
            if (Objects.isNull(entity)) {
                return new ResponseDTO(HttpStatus.OK,Constants.OBJECT_NOT_EXISTS_ERROR, null);
            }

            if (objective.getStatusId().equals(StatusEnum.CLOSED_OBJECTIVE.getId()) &&
                    (objective.getObjectiveQualify() <=Constants.MIN_VALUE_QUALIFY || objective.getObjectiveQualify()>Constants.MAX_VALUE_QUALIFY)){
                return new ResponseDTO(HttpStatus.OK,Constants.INVALID_RATING, null);
            }

            if(!objective.getStatusId().equals(StatusEnum.CLOSED_OBJECTIVE.getId()) ) {
                entity.setObjectiveQualify(Constants.MIN_VALUE_QUALIFY);
            }
            else
                entity.setObjectiveQualify(objective.getObjectiveQualify());

            entity.setObjectiveObservations(objective.getObjectiveObservations());
            Status status=statusRepository.findByStatusId(objective.getStatusId());
            entity.setStatus(status);
            objectiveRepository.save(entity);
            return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, Boolean.TRUE);

        }catch(Exception err){
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(), null);
        }

    }

    public ResponseDTO getObjectivesByUserId(Long userId) {
        try{
            List<Objective> objectives = objectiveRepository.findByUserUserId(userId);
            List<ObjetiveDTO> result = objectives.stream().map(obj -> new ObjetiveDTO(obj)).collect(Collectors.toList());
            return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, result);
        }catch(Exception err){
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }

    }

}

