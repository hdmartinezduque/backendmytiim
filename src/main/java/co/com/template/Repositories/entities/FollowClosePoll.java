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
@Table(name ="t_follow_close_poll")
public class FollowClosePoll implements Serializable {
    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_close_poll_id")
    private Long followClosePollId;

    @Column(name="follow_close_poll_required")
    private Boolean required;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "follow_close_poll_period_id", nullable = false)
    private Period period;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "follow_close_poll_question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "follow_close_poll_poll_type_id", nullable = false)
    private PollType pollType;

}
