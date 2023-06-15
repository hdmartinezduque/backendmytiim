package co.com.template.Repositories;

import co.com.template.Repositories.entities.DetailPoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailPollRepository extends JpaRepository<DetailPoll, Long> {
}
