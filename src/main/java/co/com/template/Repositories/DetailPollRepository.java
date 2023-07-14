package co.com.template.Repositories;

import co.com.template.Repositories.entities.DetailPoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetailPollRepository extends JpaRepository<DetailPoll, Long> {

    List<DetailPoll> findByAnswerDateBetween(LocalDate startDate, LocalDate endDate);

    List<DetailPoll> findByPollCodeStartsWithAndPollPeriodPeriodId(String code, Long periodId);
}
