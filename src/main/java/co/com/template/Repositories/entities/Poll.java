package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name ="t_poll")
public class Poll implements Serializable {

    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_id")
    private Long pollId;

    @Column(name="poll_describe")
    private String describe;

    @Column(name="poll_code")
    private String code;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "poll_status_id", nullable = false)
    private Status status;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "poll_period_id", nullable = false)
    private Period period;

    @Column(name="poll_start")
    private LocalDate start;

    @Column(name="poll_end")
    private LocalDate end;

    @Column(name="poll_index")
    private Integer index;

    public Poll(Period period, Status status, String code, String describe, LocalDate start, LocalDate end, Integer index){
        this.setPeriod(period);
        this.setStatus(status);
        this.setIndex(index);
        this.setCode(code);
        this.setDescribe(describe);
        this.setStart(start);
        this.setEnd(end);
    }

}
