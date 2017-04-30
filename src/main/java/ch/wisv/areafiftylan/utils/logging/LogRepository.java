package ch.wisv.areafiftylan.utils.logging;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface LogRepository extends PagingAndSortingRepository<LogEntry, Long> {

}
