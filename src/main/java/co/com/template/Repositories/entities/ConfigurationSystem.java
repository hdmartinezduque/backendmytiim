package co.com.template.Repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table( name = "t_configuration_system")
public class ConfigurationSystem implements Serializable {
    @Serial
    private static final long serialVersionUID = 5022341805021141326L;
    @Id
    @Column(name = "configuration_system_id")
    private Long systemId;
    @Column(name="configuration_system_describe")
    private String describe;
    @Column(name="configuration_system_value")
    private String value;
}
