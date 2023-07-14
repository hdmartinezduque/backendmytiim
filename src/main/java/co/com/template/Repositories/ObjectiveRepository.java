package co.com.template.Repositories;

import co.com.template.Repositories.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.com.template.Repositories.entities.Objective;
import co.com.template.Repositories.entities.Period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ObjectiveRepository  extends JpaRepository<Objective, Long> {

    List<Objective> findByGroupGroupIdAndUserUserId(Long GroupGroupId, Long UserUserId);

    List<Objective> findByGroupGroupId(Long GroupGroupId);

    List<Objective> findByUserUserId(Long UserUserId);

    Objective findByObjectiveId(Long objectiveId);

    List<Objective> findByCreateDateBetween(LocalDateTime startPeriod, LocalDateTime endPeriod);

    int countByUserAndCreateDateBetween(User user, LocalDate startPeriod, LocalDate endPeriod);

    List<Objective> findByPeriodPeriodIdAndUserUserId(Long periodId, Long userId);

    public int countByUserAndPeriod(User user, Period period);

    public boolean existsByUserUserId(long userId);


}

