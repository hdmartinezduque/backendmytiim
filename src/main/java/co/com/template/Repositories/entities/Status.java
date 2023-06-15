package co.com.template.Repositories.entities;

import co.com.template.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_status")
public class Status implements Serializable{

    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long statusId;

    @Column(name="status_describe")
    private String statusDescribe;

    @Column(name="status_type")
    private String statusType;

}
