package ch.wisv.areafiftylan.exception;

import ch.wisv.areafiftylan.users.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.BiConsumer;

@Slf4j
public class AreaFiftyLANException extends RuntimeException {

    public enum LogLevelEnum {
        ERROR(log::error),
        WARN(log::warn),
        INFO(log::info),
        DEBUG(log::debug),
        TRACE(log::trace);

        private final BiConsumer<String, Object> logger;

        LogLevelEnum(BiConsumer<String, Object> logger) {
            this.logger = logger;
        }

        public void logMessage(String message, Object object) {
            logger.accept(message, object);
        }
    }

    public AreaFiftyLANException(String message) {
        this(LogLevelEnum.WARN, message);
    }

    public AreaFiftyLANException(LogLevelEnum logLevel, String message) {
        this(logLevel, (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(), message);
    }

    public AreaFiftyLANException(LogLevelEnum logLevel, User principal , String message) {
        super(message);
        logLevel.logMessage(message, principal);
    }
}
