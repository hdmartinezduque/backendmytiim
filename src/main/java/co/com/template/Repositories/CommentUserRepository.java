package co.com.template.Repositories;


import co.com.template.Repositories.entities.CommentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentUserRepository extends JpaRepository<CommentUser, Long> {

    List<CommentUser> findByCommentUserCommentCommentId(Long commentId);



}

