package co.com.template.Repositories;

import co.com.template.Repositories.entities.FollowClosePoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowClosePollRepository extends JpaRepository<FollowClosePoll, Long> {

    FollowClosePoll findByFollowClosePollId(Long followClosePollId);

    List<FollowClosePoll> findByPollTypePollTypeId(Long pollTypeId);

}

