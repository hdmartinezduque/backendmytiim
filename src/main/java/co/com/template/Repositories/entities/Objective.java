package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_objective")
public class Objective implements Serializable {

	private static final long serialVersionUID = 5022341805021141326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "objective_id")
	private Long objectiveId;

	@Column(name="objective_describe")
	private String objectiveDescribe;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "objective_user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "objective_group_id", nullable = false)
	private Group group;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "objective_status_id", nullable = false)
	private Status status;
        
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "objective_objective_type_id", nullable = false)
	private ObjectiveType objectiveType;

	@Column(name="objective_qualify")
	private Long objectiveQualify;

	@Column(name="objective_observations")
	private String objectiveObservations;

	@Column(name="objective_created_date")
	private LocalDateTime createDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "objective_period_id")
	private Period period;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "objective_align_group_id")
	private Group alignGroup;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "objective_align_user_id")
	private User alignUser;

	@Column(name = "objective_align_objective_id")
	private Long alignObjectiveId;

}