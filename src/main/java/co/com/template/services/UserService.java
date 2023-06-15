package co.com.template.services;


import co.com.template.Repositories.dto.ObjectiveUserDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.UserRepository;
import co.com.template.Repositories.dto.ViewListUserDTO;
import co.com.template.Repositories.entities.User;
import co.com.template.exception.CustomException;
import co.com.template.utils.Constants;
import co.com.template.utils.StatusEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Log4j2
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public ResponseDTO getUserForGroup(Long groupId) {
        List<User> list = userRepository.findByGroupGroupId(groupId);
        List<ObjectiveUserDTO> dtoList = new ArrayList<>();
        for (User user : list) {
            ObjectiveUserDTO objectiveUserDTO = new ObjectiveUserDTO();
            objectiveUserDTO.setUserId(user.getUserId());
            objectiveUserDTO.setUserName(user.getUserName());
            objectiveUserDTO.setUserLastName(user.getUserLastName());
            dtoList.add(objectiveUserDTO);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, dtoList);
    }


    public ResponseDTO getUser() {
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, userRepository.findAll());
    }

    public ResponseDTO getUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new CustomException(Constants.USER_NOT_EXISTS_ERROR, HttpStatus.OK);
        }
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, userRepository.findByUserId(userId));
    }

    public ResponseDTO getListActiveUser() {
        List<User> user = userRepository.findByStatusStatusId(StatusEnum.ACTIVE_USER.getId());
        List<ViewListUserDTO> viewListUserDTO = new ArrayList<>();

        for (User u: user){
            ViewListUserDTO users = new ViewListUserDTO();
            users.setUserId(u.getUserId());
            users.setUserName(u.getUserName());
            users.setUserLastName(u.getUserLastName());
            users.setStatusId(u.getStatus().getStatusId());
            viewListUserDTO.add(users);


        }
            return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, viewListUserDTO);

    }


}
