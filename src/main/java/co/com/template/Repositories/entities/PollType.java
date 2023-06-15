package co.com.template.Repositories.entities;


import co.com.template.utils.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_poll_type")
public class PollType implements Serializable {
    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_type_id")
    private Long pollTypeId;

    @NotBlank(message = Constants.DESCRIPTION_REQUIRED)
    @Column(name="poll_type_describe")
    private String pollTypeDescribe;




}