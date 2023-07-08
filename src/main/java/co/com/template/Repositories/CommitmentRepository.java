package co.com.template.Repositories;


import co.com.template.Repositories.entities.Commitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, Long> {

    Commitment findByCommitmentId(Long commitmentId);

    List<Commitment> findByObjectiveObjectiveId(Long objectiveId);

    List<Commitment> findByCreateDateBetween(LocalDate startPeriod, LocalDate endPeriod);

}
