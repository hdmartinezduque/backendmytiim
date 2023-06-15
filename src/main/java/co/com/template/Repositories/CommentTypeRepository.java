package co.com.template.Repositories;

import co.com.template.Repositories.entities.CommentType;
import co.com.template.Repositories.entities.ObjectiveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentTypeRepository extends JpaRepository<CommentType, Long> {

    CommentType findByCommentTypeId(Long commentTypeId);

}

