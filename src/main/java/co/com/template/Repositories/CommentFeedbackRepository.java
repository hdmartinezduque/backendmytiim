package co.com.template.Repositories;

import co.com.template.Repositories.entities.Comment;
import co.com.template.Repositories.entities.CommentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentFeedbackRepository extends JpaRepository<CommentFeedback, Long> {

    List<CommentFeedback> findByComment(Comment comment);

   List<CommentFeedback> findByCommentCommentId(Long commentId);



}

