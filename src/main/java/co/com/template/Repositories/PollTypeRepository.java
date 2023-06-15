package co.com.template.Repositories;

import co.com.template.Repositories.entities.PollType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollTypeRepository extends JpaRepository<PollType, Long> {

    PollType findByPollTypeId(Long pollTypeId);

}

