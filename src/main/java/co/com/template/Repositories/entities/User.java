package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 5022341805021141326L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name="user_password")
    private String userPassword;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_last_name")
    private String userLastName;

    @Column(name="user_phone")
    private Long userPhone;

    @Column(name="user_profile_id")
    private Integer userProfileId;

    @Column(name="user_email")
    private String userEmail;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_status_id", nullable = false)
    private Status status;

    @Column(name="user_user")
    public String user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "t_roll_user",
            joinColumns = @JoinColumn(name = "roll_roll_user_id"),
            inverseJoinColumns = @JoinColumn(name = "roll_roll_id"))
    private Set<Roll> rolls = new HashSet<>();

}
