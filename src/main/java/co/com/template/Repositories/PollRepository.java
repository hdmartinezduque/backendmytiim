package co.com.template.Repositories;

import co.com.template.Repositories.entities.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    Poll findByPollId(Long pollId);

    Poll findByStatusStatusIdAndCodeStartsWith(Long statusId, String typePoll);

    List<Poll> findByStatusStatusIdAndEndLessThan(Long statusId,LocalDate today);

    Poll findTop1ByPeriodPeriodIdAndCodeStartsWithOrderByIndexDesc(Long periodId, String typePoll);
}
