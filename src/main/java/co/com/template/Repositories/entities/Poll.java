package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
