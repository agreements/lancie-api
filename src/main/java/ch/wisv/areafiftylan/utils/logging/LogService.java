package ch.wisv.areafiftylan.utils.logging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface LogService {

    Page<LogEntry> listAllByPage(Pageable pageable);

    void log(LogType type, String message, Throwable throwable);

}