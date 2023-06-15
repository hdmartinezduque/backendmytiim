package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_group")

public class Group implements Serializable{

	private static final long serialVersionUID = 5022341805021141326L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private Long groupId;

	@Column(name="group_describe")
	private String groupDescribe;

	@Column(name="group_code")
	private String groupCode;



}




