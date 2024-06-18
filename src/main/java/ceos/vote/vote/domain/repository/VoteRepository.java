package ceos.vote.vote.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ceos.vote.vote.application.dto.response.DemodayVoteResponse;
import ceos.vote.vote.domain.Vote;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(value =
            "select new ceos.vote.vote.application.dto.response.DemodayVoteResponse(v.teamName, count(*)) "
            + "from DemoDayVote v "
            + "group by v.teamName "
            + "order by count(*) desc"
    )
    List<DemodayVoteResponse> getDemodayVoteCount();
}
