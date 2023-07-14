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
@Table(name ="t_comment_user")
public class CommentUser implements Serializable {

	private static final long serialVersionUID = 5022341805021141326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_user_id")
	private Long commentUserId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "comment_user_comment_id", nullable = false)
	private Comment commentUserComment;


	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "comment_user_to_id", nullable = false)
	private User userTo;




}