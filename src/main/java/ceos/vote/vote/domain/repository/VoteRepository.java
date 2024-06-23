package ceos.vote.vote.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ceos.vote.user.domain.Part;
import ceos.vote.user.domain.User;
import ceos.vote.vote.application.dto.response.DemodayVoteResponse;
import ceos.vote.vote.application.dto.response.PartLeaderVoteResponse;
import ceos.vote.vote.domain.Vote;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(value = """
            select new ceos.vote.vote.application.dto.response.DemodayVoteResponse(v.teamName, count(*))
              from DemoDayVote v
             group by v.teamName
             order by count(*) desc
            """)
    List<DemodayVoteResponse> getDemodayVoteCount();

    @Query(value = """
            select new ceos.vote.vote.application.dto.response.PartLeaderVoteResponse(v.partLeader.name, count(*))
              from PartLeaderVote v 
             where v.partLeader.part = :part 
             group by v.partLeader 
             order by count(*) desc
            """)
    List<PartLeaderVoteResponse> getPartLeaderVoteCount(@Param("part") Part part);

    @Query(value = """
            select count(1) > 0 
              from DemoDayVote v 
             where v.voteUser.username = :username
            """)
    boolean existsDemodayVotesByUsername(@Param("username") String username);

    @Query(value = """
            select count(1) > 0
            from PartLeaderVote v
            where v.voteUser.username = :username
            """)
    boolean existsPartLeaderVotesByUsername(@Param("username") String username);
}
