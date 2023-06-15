package co.com.template.services;

import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.com.template.Repositories.ObjectiveTypeRepository;

@Service
@Transactional
public class ObjectiveTypeService {

    @Autowired
    private ObjectiveTypeRepository objectiveTypeRepository;

    public ResponseDTO getObjectiveType(){
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, objectiveTypeRepository.findAll());
    }
    
}
