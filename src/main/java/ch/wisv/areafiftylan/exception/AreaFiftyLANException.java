package ch.wisv.areafiftylan.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.Consumer;

@Slf4j
public class AreaFiftyLANException extends RuntimeException {

    public enum LogLevelEnum {
        ERROR(log::error),
        WARN(log::warn),
        INFO(log::info),
        DEBUG(log::debug),
        TRACE(log::trace);

        private final Consumer<String> logger;

        LogLevelEnum(Consumer<String> logger) {
            this.logger = logger;
        }

        public void logMessage(String message) {
            logger.accept(message);
        }
    }

    public AreaFiftyLANException(String message) {
        this(LogLevelEnum.WARN, message);
    }

    public AreaFiftyLANException(LogLevelEnum logLevel, String message) {
        super(message);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String un = (auth == null || auth.getName() == null) ? "'ANONYMOUS" : auth.getName();
        logLevel.logMessage("[User: "+un+"] "+message);
    }
}
