package co.com.template.utils;

import com.sun.mail.imap.protocol.ID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    ACTIVE_OBJECTIVE(1L),
    DELAYED_OBJECTIVE(2L),
    ON_RISK_OBJECTIVE(3L),
    ON_PAUSE_OBJECTIVE(4L),
    CLOSED_OBJECTIVE(5L),
    ACTIVE_USER(6L),
    ACTIVATION_PENDING_USER(7L),
    ACTIVE_COMMENT(8L),
    ACTIVE_QUESTION(10L),
    ACTIVE_PERIOD(12L),
    ACTIVE_POLL(14L),
    INACTIVE_POLL(15L),
    IN_PROGRESS_POLL_USER(16L),
    ENDED_POLL_USER(17L);



    private final Long id;


}
