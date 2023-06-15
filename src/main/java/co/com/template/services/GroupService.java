package co.com.template.services;


import co.com.template.Repositories.GroupRepository;
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
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public ResponseDTO getGroup() {
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, groupRepository.findAll());
    }

}
