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
@Table(name ="t_poll_question")
public class PollQuestion implements Serializable {
    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_question_id")
    private Long pollQuestionId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "poll_question_poll_id", nullable = false)
    private Poll poll;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "poll_question_question_id", nullable = false)
    private Question question;

    @Column(name="poll_question_required")
    private Boolean isRequired;

}
