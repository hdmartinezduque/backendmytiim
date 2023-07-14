package co.com.template.Repositories;

import co.com.template.Repositories.entities.Comment;
import co.com.template.Repositories.entities.Commitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findTop20ByCommentTypeTrueOrderByCommentDateDesc();

    Comment findByCommentId(Long commentId);

    List<Comment> findTop20ByObjectiveObjectiveIdOrderByCommentDateDesc(Long objectiveId);


    List<Comment> findByCommentCommentTypeCommentTypeIdOrderByCommentDateDesc(Long typeId);

    List<Comment> findTop20ByOrderByCommentDateDesc();




}

