package ceos.vote.vote.application;

import static ceos.vote.common.exception.ExceptionCode.ALREADY_VOTED;
import static ceos.vote.common.exception.ExceptionCode.INVALID_PART_LEADER;
import static ceos.vote.common.exception.ExceptionCode.INVALID_USERNAME;
import static ceos.vote.common.exception.ExceptionCode.VOTE_FOR_DIFFERENT_PART;
import static ceos.vote.common.exception.ExceptionCode.VOTE_FOR_SAME_TEAM;

import org.springframework.stereotype.Service;

import ceos.vote.common.exception.BadRequestException;
import ceos.vote.user.domain.Part;
import ceos.vote.user.domain.TeamName;
import ceos.vote.user.domain.User;
import ceos.vote.user.domain.repository.UserRepository;
import ceos.vote.vote.application.dto.response.DemodayVoteResponse;
import ceos.vote.vote.application.dto.response.PartLeaderVoteResponse;
import ceos.vote.vote.domain.DemoDayVote;
import ceos.vote.vote.domain.PartLeaderVote;
import ceos.vote.vote.domain.repository.VoteRepository;
import ceos.vote.vote.presentation.dto.request.DemodayVoteCreateRequest;
import ceos.vote.vote.presentation.dto.request.PartLeaderVoteCreateRequest;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {

    final private VoteRepository voteRepository;
    final private UserRepository userRepository;

    public PartLeaderVote votePartLeader(PartLeaderVoteCreateRequest request, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BadRequestException(INVALID_USERNAME)
        );

        User partLeader = userRepository.findByUsername(request.partLeaderUsername())
                .orElseThrow(
                        () -> new BadRequestException(INVALID_PART_LEADER)
                );

        if (user.isDifferentPart(partLeader)) {
            throw new BadRequestException(VOTE_FOR_DIFFERENT_PART);
        }

        if (voteRepository.existsPartLeaderVotesByUsername(username)) {
            throw new BadRequestException(ALREADY_VOTED);
        }

        PartLeaderVote vote = new PartLeaderVote(partLeader, user);
        return voteRepository.save(vote);
    }

    public DemoDayVote voteTeam(DemodayVoteCreateRequest request, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BadRequestException(INVALID_USERNAME)
        );

        if (voteRepository.existsDemodayVotesByUsername(username)) {
            throw new BadRequestException(ALREADY_VOTED);
        }

        if (user.isSameTeam(request.teamName())) {
            throw new BadRequestException(VOTE_FOR_SAME_TEAM);
        }

        DemoDayVote vote = new DemoDayVote(request.teamName(), user);
        return voteRepository.save(vote);
    }

    public List<DemodayVoteResponse> getDemoDayVoteResult() {
        List<DemodayVoteResponse> responses = voteRepository.getDemodayVoteCount();
        for (TeamName teamName : TeamName.values()) {
            boolean found = false;
            for (DemodayVoteResponse response: responses) {
                if (response.name() == teamName) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                responses.add(new DemodayVoteResponse(teamName, 0));
            }
        }
        return responses;
    }

    public List<PartLeaderVoteResponse> getPartLeaderVoteResult(Part part) {
        List<PartLeaderVoteResponse> responses = voteRepository.getPartLeaderVoteCount(part);
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (!user.isSamePart(part)) {
                continue;
            }
            boolean found = false;
            for (PartLeaderVoteResponse response: responses) {
                if (user.isSameUser(response.username())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                responses.add(new PartLeaderVoteResponse(user.getUsername(), user.getName(), 0));
            }
        }
        return responses;
    }
}
