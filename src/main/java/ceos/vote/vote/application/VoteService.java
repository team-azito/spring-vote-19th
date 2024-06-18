package ceos.vote.vote.application;

import static ceos.vote.common.exception.ExceptionCode.ALREADY_VOTED;
import static ceos.vote.common.exception.ExceptionCode.INVALID_PART_LEADER;
import static ceos.vote.common.exception.ExceptionCode.INVALID_USERNAME;
import static ceos.vote.common.exception.ExceptionCode.VOTE_FOR_DIFFERENT_PART;
import static ceos.vote.common.exception.ExceptionCode.VOTE_FOR_SAME_TEAM;

import org.springframework.stereotype.Service;

import ceos.vote.common.exception.BadRequestException;
import ceos.vote.user.domain.Part;
import ceos.vote.user.domain.User;
import ceos.vote.user.domain.repository.UserRepository;
import ceos.vote.vote.application.dto.response.DemodayVoteResponse;
import ceos.vote.vote.application.dto.response.PartLeaderVoteResponse;
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
                () -> new BadRequestException(INVALID_USERNAME)
        );

        User partLeader = userRepository.findByUsername(request.getPartLeaderUsername())
                .orElseThrow(
                        () -> new BadRequestException(INVALID_PART_LEADER)
                );

        if (voteRepository.existsPartLeaderVotesByUsername(username)) {
            throw new BadRequestException(ALREADY_VOTED);
        }

        if (user.getPart() != partLeader.getPart()) {
            throw new BadRequestException(VOTE_FOR_DIFFERENT_PART);
        }

        PartLeaderVote vote = new PartLeaderVote(partLeader, user);
        voteRepository.save(vote);
    }

    public void voteTeam(DemodayVoteCreateRequest request, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BadRequestException(INVALID_USERNAME)
        );

        System.out.println(username);

        if (voteRepository.existsDemodayVotesByUsername(username)) {
            throw new BadRequestException(ALREADY_VOTED);
        }

        if (user.getTeamName() == request.getTeamName()) {
            throw new BadRequestException(VOTE_FOR_SAME_TEAM);
        }

        DemoDayVote vote = new DemoDayVote(request.getTeamName(), user);
        voteRepository.save(vote);
    }

    public List<DemodayVoteResponse> getDemoDayVoteResult() {
        return voteRepository.getDemodayVoteCount();
    }

    public List<PartLeaderVoteResponse> getPartLeaderVoteResult(Part part) {
        return voteRepository.getPartLeaderVoteCount(part);
    }
}
