package co.com.template.services;

import co.com.template.Repositories.CommentTypeRepository;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class CommentTypeService {

    @Autowired
    private CommentTypeRepository commentTypeRepository;


    public ResponseDTO getCommentType()   {
        try{
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, commentTypeRepository.findAll());
        }catch(Exception err){
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);

        }
    }
}
