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
@Table(name ="t_poll_user")
public class PollUser implements Serializable {
    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_user_id")
    private Long pollUserId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "poll_user_poll_id", nullable = false)
    private Poll poll;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "poll_user_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "poll_user_status_id", nullable = false)
    private Status status;

    @Column(name="poll_user_created_date")
    private LocalDate createdDate;

    @Column(name="poll_user_ended_date")
    private LocalDate endedDate;
}
