package co.com.template.Repositories.entities;

import co.com.template.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_objective_type")
@Component
public class ObjectiveType implements Serializable {

	private static final long serialVersionUID = 5022341805021141326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "objective_type_id")
	private Long objectiveTypeId;

	@NotBlank(message = Constants.DESCRIPTION_REQUIRED)
	@Column(name="objective_type_describe")
	private String objectiveTypeDescribe;

	@Column(name="objective_type_status_id")
	private Integer objectiveTypeStatusId;

}