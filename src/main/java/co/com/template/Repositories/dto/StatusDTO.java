package co.com.template.Repositories.dto;

import co.com.template.Repositories.entities.Status;
import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

@Data

public class StatusDTO implements Serializable {
    private Long statusId;

    private String statusDescribe;

    public StatusDTO(Status entity) {
        this.statusId = entity.getStatusId();
        this.statusDescribe = entity.getStatusDescribe();
    }
}
