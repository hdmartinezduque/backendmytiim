package co.com.template.Repositories;

import co.com.template.Repositories.entities.PollUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollUserRepository extends JpaRepository<PollUser, Long> {

    List<PollUser> findByUserUserIdAndStatusStatusId(Long userId, Long statusId);

    PollUser findByUserUserIdAndPollPollId(Long userId, Long pollId);

    List<PollUser> findByPollPollId(Long pollId);
}
