package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Entity
@Table(name ="t_notification_log")
public class NotificationLog implements Serializable {

    private static final long serialVersionUID = 5022341805021141326L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_log_id")
    private Long logId;

    @Column(name="notification_log_describe")
    private String describe;

    @Column(name="notification_log_type_id")
    private Long typeId;
}
