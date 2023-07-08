package co.com.template.Repositories;

import co.com.template.Repositories.entities.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface PeriodRepository extends JpaRepository<Period, Long>  {

    Period findByPeriodId(Long periodId);

    Period findByEndPeriodGreaterThanEqualAndStartPeriodLessThanEqual(LocalDate today,LocalDate todayEnd);

    Period findByEndPollGreaterThanEqualAndStartPollLessThanEqual(LocalDate today,LocalDate todayEnd);

    Period findTopByOrderByEndPeriodDesc();

    List<Period> findByStatusStatusIdOrderByStartPeriodAsc(Long periodId);
}
