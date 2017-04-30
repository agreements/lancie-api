package ch.wisv.areafiftylan.utils.logging;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends PagingAndSortingRepository<LogEntry, Long> {

}
