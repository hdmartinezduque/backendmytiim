package co.com.template.Repositories;

import co.com.template.Repositories.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByStatusId(Long id);

    List<Status> findByStatusTypeOrderByStatusId(String type);

}