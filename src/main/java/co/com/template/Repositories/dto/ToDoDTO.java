package co.com.template.Repositories.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ToDoDTO implements Serializable {

    private List<PollDTO> follow;
    private List<PollDTO> closures;

}
