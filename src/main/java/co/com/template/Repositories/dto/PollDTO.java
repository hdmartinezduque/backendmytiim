package co.com.template.Repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PollDTO implements Serializable {
    private Long pollId;
    private String code;
    private String describe;
}
