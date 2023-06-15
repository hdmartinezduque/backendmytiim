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
@Table(name ="t_comment_type")
public class CommentType implements Serializable {

	private static final long serialVersionUID = 5022341805021141326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_type_id")
	private Long commentTypeId;

	@Column(name="comment_type_describe")
	private String commentTypeDescribe;

	@Column(name="comment_type_status_id")
	private Integer commentTypeStatusId;



}