package co.com.template.Repositories;

import co.com.template.Repositories.entities.ConfigurationSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationSystemRepository extends JpaRepository<ConfigurationSystem, Long> {

    ConfigurationSystem findBySystemId(Long id);
}
