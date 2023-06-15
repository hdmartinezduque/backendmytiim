package co.com.template.Repositories;

import co.com.template.Repositories.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByGroupGroupId(Long GroupGroupId);

    User findByUserId(Long userId);


    User findByUser(String user);

    List<User> findByStatusStatusId(Long StatusStatusId);



}
