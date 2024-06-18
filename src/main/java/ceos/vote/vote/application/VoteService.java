package ceos.vote.vote.application;

import org.springframework.stereotype.Service;

import ceos.vote.user.domain.User;
import ceos.vote.user.domain.repository.UserRepository;
import ceos.vote.vote.application.dto.response.DemodayVoteResponse;
import ceos.vote.vote.domain.DemoDayVote;
import ceos.vote.vote.domain.PartLeaderVote;
import ceos.vote.vote.domain.repository.VoteRepository;
import ceos.vote.vote.presentation.dto.request.DemodayVoteCreateRequest;
import ceos.vote.vote.presentation.dto.request.PartLeaderVoteCreateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {

    final private VoteRepository voteRepository;
    final private UserRepository userRepository;

    public void votePartLeader(PartLeaderVoteCreateRequest request, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("username not exist")
        );

        User partLeader = userRepository.findByUsername(request.getPartLeaderUsername())
                .orElseThrow(
                        () -> new IllegalArgumentException("part leader username not exist")
                );
        PartLeaderVote vote = new PartLeaderVote(partLeader, user);
        voteRepository.save(vote);
    }

    public void voteTeam(DemodayVoteCreateRequest request, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("username not exist")
        );
        DemoDayVote vote = new DemoDayVote(request.getTeamName(), user);
        voteRepository.save(vote);
    }

    public List<DemodayVoteResponse> getDemoDayVotes() {
        return voteRepository.getDemodayVoteCount();
    }
}
