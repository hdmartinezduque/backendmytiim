package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_commitment")
@SequenceGenerator(name = "commitmentSeq", sequenceName = "t_commitment_commitment_id_seq", allocationSize = 1)
public class Commitment implements Serializable {

    private static final long serialVersionUID = 5022341805021141326L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commitmentSeq")
    @Column(name = "commitment_id")
    private Long commitmentId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "commitment_objective_id", nullable = false)
    private Objective objective;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "commitment_measure_id", nullable = false)
    private Measure measure;

    @Column(name = "commitment_describe")
    private String commitmentDescribe;

    @Column(name = "commitment_date")
    private LocalDate commitmentDate;

    @Column(name = "commitment_advance")
    private Double commitmentAdvance;

    @Column(name = "commitment_goal")
    private Double commitmentGoal;

    @Column(name="commitment_created_date")
    private LocalDate createDate;

}
