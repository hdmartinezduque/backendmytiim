package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_comment")
public class Comment implements Serializable {

	private static final long serialVersionUID = 5022341805021141326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long commentId;

	@Column(name="comment_describe")
	private String commentDescribe;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "comment_user_from_id", nullable = false)
	private User userFrom;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "comment_user_to_id", nullable = false)
	private User userTo;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "comment_status_id", nullable = false)
	private Status status;

	@CreationTimestamp
	@JoinColumn(name = "comment_date", nullable = false)
	private LocalDateTime commentDate;

	@JoinColumn(name = "comment_type", nullable = false)
	private Boolean commentType;
        
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "comment_comment_type_id", nullable = false)
	private CommentType commentCommentType;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "comment_objective_id", nullable = false)
	private Objective objective;


}