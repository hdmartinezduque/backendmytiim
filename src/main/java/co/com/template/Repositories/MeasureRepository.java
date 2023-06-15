package co.com.template.Repositories;

import co.com.template.Repositories.entities.Measure;
import co.com.template.Repositories.entities.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface MeasureRepository extends JpaRepository<Measure, Long> {

        Measure findByMeasureId(Long measureId);

    }


