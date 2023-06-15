package co.com.template.services;

import co.com.template.Repositories.CommitmentRepository;
import co.com.template.Repositories.MeasureRepository;
import co.com.template.Repositories.ObjectiveRepository;
import co.com.template.Repositories.dto.*;
import co.com.template.Repositories.entities.Commitment;
import co.com.template.Repositories.entities.Measure;
import co.com.template.Repositories.entities.Objective;
import co.com.template.utils.Constants;
import co.com.template.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
@Log4j2
public class CommitmentService {


    private final CommitmentRepository commitmentRepository;

    private final MeasureRepository measureRepository;

    private final ObjectiveRepository objectiveRepository;


    public ResponseDTO getCommitment() {
        List<Commitment> commitment = commitmentRepository.findAll();
        List<CommitmentDTO> commitmentDTO = new ArrayList<>();
        for (Commitment c : commitment) {
            CommitmentDTO commit = new CommitmentDTO();
            commit.setCommitmentDescribe(c.getCommitmentDescribe());
            commit.setCommitmentDate(c.getCommitmentDate().toString());
            commit.setCommitmentAdvance(c.getCommitmentAdvance());
            commit.setCommitmentGoal(c.getCommitmentGoal());
            commit.setMeasureId(c.getMeasure().getMeasureId());
            commitmentDTO.add(commit);
        }
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE,commitmentDTO);
    }


    public ResponseDTO getCommitmentByObjectiveId(Long objectiveId) {
        List<Commitment> com = commitmentRepository.findByObjectiveObjectiveId(objectiveId);
        List<CommitmentDTO> commitmentDTO = new ArrayList<>();
        for (Commitment c : com) {
            CommitmentDTO Commit = new CommitmentDTO();
            Commit.setCommitmentId(c.getCommitmentId());
            Commit.setCommitmentDescribe(c.getCommitmentDescribe());
            String date = Util.convertToDateTimeFormatted(c.getCommitmentDate(), Constants.DATE_FORMAT);
            Commit.setCommitmentDate(date);
            Commit.setCommitmentAdvance(c.getCommitmentAdvance());
            Commit.setCommitmentGoal(c.getCommitmentGoal());
            Commit.setMeasureId(c.getMeasure().getMeasureId());
            commitmentDTO.add(Commit);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, commitmentDTO);
    }



    public ResponseDTO SetCommitment(CreateCommitmentDTO createCommitmentDTO) {

        Measure measure = measureRepository.findByMeasureId(createCommitmentDTO.getMeasureId());
        Objective objective = objectiveRepository.findByObjectiveId(createCommitmentDTO.getObjectiveId());
        Commitment commitment = new Commitment();
        commitment.setCommitmentDescribe(createCommitmentDTO.getCommitmentDescribe());
        commitment.setCommitmentDate(createCommitmentDTO.getCommitmentDate());
        commitment.setMeasure(measure);
        commitment.setCommitmentAdvance(createCommitmentDTO.getCommitmentAdvance());
        commitment.setCommitmentGoal(createCommitmentDTO.getCommitmentGoal());
        commitment.setObjective(objective);
        commitmentRepository.save(commitment);
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, Boolean.TRUE);
    }

    public ResponseDTO updateCommitment(CommitmentEditionDTO commitment) {

        Commitment comm = commitmentRepository.findByCommitmentId(commitment.getCommitmentId());

        if(Objects.isNull(comm)) {
            return new ResponseDTO(HttpStatus.OK,Constants.OBJECT_NOT_EXISTS_ERROR, null);
        }

        Measure measure = measureRepository.findByMeasureId(commitment.getMeasureId());

        comm.setCommitmentDescribe(commitment.getCommitmentDescribe());
        comm.setCommitmentDate(commitment.getCommitmentDate());
        comm.setMeasure(measure);
        comm.setCommitmentAdvance(commitment.getCommitmentAdvance());
        comm.setCommitmentGoal(commitment.getCommitmentGoal());
        commitmentRepository.save(comm);
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, Boolean.TRUE);
    }

    public ResponseDTO advanceCommitment(AdvanceCommitmentDTO commitment) {
        Commitment comm = commitmentRepository.findByCommitmentId(commitment.getCommitmentId());
        if(Objects.isNull(comm)) {
            return new ResponseDTO(HttpStatus.OK,Constants.OBJECT_NOT_EXISTS_ERROR, null);
        }
        comm.setCommitmentAdvance(commitment.getCommitmentAdvance());
        commitmentRepository.save(comm);
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, Boolean.TRUE);
    }


}
