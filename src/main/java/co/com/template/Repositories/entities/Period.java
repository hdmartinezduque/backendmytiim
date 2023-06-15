package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_period")
public class Period implements Serializable {
    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_id")
    private Long periodId;

    @Column(name="period_describe")
    private String describe;

    @Column(name="period_start_period")
    private LocalDate startPeriod;

    @Column(name="period_end_period")
    private LocalDate endPeriod;

    @Column(name="period_start_poll")
    private LocalDate startPoll;

    @Column(name="period_end_poll")
    private LocalDate endPoll;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "period_status_id", nullable = false)
    private Status status;

}
