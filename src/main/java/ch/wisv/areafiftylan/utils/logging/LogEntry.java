package ch.wisv.areafiftylan.utils.logging;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@NoArgsConstructor
public class LogEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    private Timestamp timestamp;
    @Getter
    private LogType type;
    @Getter
    private String message;
    @Getter
    private String username;
    @Getter
    private Throwable throwable;

    public LogEntry(LogType type, String message, Throwable throwable) {
        this.timestamp = Timestamp.from(Instant.now());
        this.username = SecurityContextHolder.getContext().getAuthentication().getName();

        this.type = type;
        this.message = message;
        this.throwable = throwable;
    }
}
