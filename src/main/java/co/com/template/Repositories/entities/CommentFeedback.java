package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_comment_feedback")
public class CommentFeedback implements Serializable {

	private static final long serialVersionUID = 5022341805021141326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_feedback_id")
	private Long commentFeedbackId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "comment_feedback_comment_id")
	private Comment comment;

	@Column(name="comment_feedback_describe")
	private String commentFeedbackDescribe;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "comment_feedback_user_from_id", nullable = false)
	private User userFrom;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "comment_feedback_status_id", nullable = false)
	private Status status;


	@JoinColumn(name = "comment_feedback_type", nullable = false)
	private Boolean commentFeedbackType;

	@CreationTimestamp
	@JoinColumn(name = "comment_feedback_date", nullable = false)
	private LocalDateTime commentFeedbackDate;
        



}