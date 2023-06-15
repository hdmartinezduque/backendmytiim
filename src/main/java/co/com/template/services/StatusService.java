package co.com.template.services;

import co.com.template.Repositories.StatusRepository;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.dto.StatusDTO;
import co.com.template.Repositories.entities.Status;
import co.com.template.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@Log4j2
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;


    public ResponseDTO getStatus()   {
        try{
            return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, statusRepository.findAll());
        }catch(Exception err){
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }


    public ResponseDTO updateStatus(Long statusId, Status status) {
        Status item = statusRepository.findByStatusId(statusId);
        if (Objects.isNull(item))   {
            return new ResponseDTO(HttpStatus.OK, Constants.STATUS_NOT_EXISTS_ERROR, null);
        }
        item.setStatusDescribe(status.getStatusDescribe());
        statusRepository.save(item);
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, Boolean.TRUE);
    }
    public ResponseDTO getStatusByType(String statusType)   {
        try{
            List<Status> entities = statusRepository.findByStatusTypeOrderByStatusId(statusType);
            List<StatusDTO> list = new ArrayList<>();
            for(Status entity : entities){
                StatusDTO item = new StatusDTO(entity);
                list.add(item);
            }
            return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, list);
        }catch(Exception err){
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }
}