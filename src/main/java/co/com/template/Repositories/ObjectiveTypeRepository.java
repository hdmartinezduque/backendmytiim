package co.com.template.Repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import co.com.template.Repositories.entities.ObjectiveType;

@Repository
public interface ObjectiveTypeRepository  extends JpaRepository<ObjectiveType, Long> {

    ObjectiveType findByObjectiveTypeId(Long objectiveTypeId);

}

