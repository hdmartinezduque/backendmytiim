package co.com.template.Repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.com.template.Repositories.entities.Objective;

import java.util.List;

@Repository
public interface ObjectiveRepository  extends JpaRepository<Objective, Long> {

    List<Objective> findByGroupGroupIdAndUserUserId(Long GroupGroupId, Long UserUserId);

    List<Objective> findByGroupGroupId(Long GroupGroupId);

    List<Objective> findByUserUserId(Long UserUserId);

    Objective findByObjectiveId(Long objectiveId);

}

