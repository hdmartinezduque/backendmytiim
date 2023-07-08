package co.com.template.Repositories;

import co.com.template.Repositories.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findByGroupId(Long groupId);

    List<Group> findAllByGroupId(Long groupId);

}

