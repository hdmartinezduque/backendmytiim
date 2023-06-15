package co.com.template.Repositories.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Entity
@Table(name ="t_detail_poll")
public class DetailPoll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_poll_id")
    private Long detailPollId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "detail_poll_poll_id", nullable = false)
    private Poll poll;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "detail_poll_question_id", nullable = false)
    private Question question;

    @Column(name="detail_poll_answer")
    private String answer;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "detail_poll_user_id", nullable = false)
    private User user;

    @Column(name="detail_poll_answer_date")
    private LocalDate answerDate;

    public DetailPoll(Poll poll, Question question, String answer, User user, LocalDate answerDate) {
        this.poll = poll;
        this.question = question;
        this.answer = answer;
        this.user = user;
        this.answerDate = answerDate;
    }
}
