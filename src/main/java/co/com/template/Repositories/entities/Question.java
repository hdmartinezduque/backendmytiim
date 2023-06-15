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
@Table(name ="t_question")
public class Question implements Serializable {
    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name="question_describe")
    private String describe;

    @Column(name="question_answer_type_id")
    private Long answerTypeId;

    @Column(name="question_options")
    private String options;

    @Column(name="question_status_id")
    private Long statusId;

}
