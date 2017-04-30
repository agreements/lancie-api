package ch.wisv.areafiftylan.utils.logging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class LogServiceImpl implements LogService {
    private LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public Page<LogEntry> listAllByPage(Pageable pageable) {
        return logRepository.findAll(pageable);
    }

    @Override
    public void log(LogType type, String message, Throwable throwable) {
        logRepository.save(new LogEntry(type, message, throwable));
    }
}
