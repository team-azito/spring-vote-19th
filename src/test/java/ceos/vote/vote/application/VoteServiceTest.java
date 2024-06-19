package ceos.vote.vote.application;

import static ceos.vote.vote.fixture.VoteFixture.BACKEND_PART_LEADER_VOTE;
import static ceos.vote.vote.fixture.VoteFixture.DEMO_DAY_VOTE_1;
import static ceos.vote.vote.fixture.VoteFixture.DEMO_DAY_VOTE_FOR_MY_TEAM;
import static ceos.vote.vote.fixture.VoteFixture.FRONTEND_PART_LEADER_VOTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ceos.vote.common.exception.BadRequestException;
import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.user.domain.repository.UserRepository;
import ceos.vote.vote.domain.DemoDayVote;
import ceos.vote.vote.domain.PartLeaderVote;
import ceos.vote.vote.domain.repository.VoteRepository;
import ceos.vote.vote.fixture.VoteFixture;
import ceos.vote.vote.presentation.dto.request.DemodayVoteCreateRequest;
import ceos.vote.vote.presentation.dto.request.PartLeaderVoteCreateRequest;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VoteService voteService;

    @Test
    @DisplayName("데모데이 투표에 성공한다.")
    public void testVoteDemoday_Success() {
        // given
        DemodayVoteCreateRequest request = new DemodayVoteCreateRequest(DEMO_DAY_VOTE_1.getTeamName());
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(DEMO_DAY_VOTE_1.getUser()));
        given(voteRepository.save(any(DemoDayVote.class))).willReturn(DEMO_DAY_VOTE_1);

        // when
        DemoDayVote vote = voteService.voteTeam(request, DEMO_DAY_VOTE_1.getUser().getUsername());

        // then
        assertThat(vote).usingRecursiveComparison().isEqualTo(DEMO_DAY_VOTE_1);
    }

    @Test
    @DisplayName("같은 팀에 투표하면 데모데이 투표에 실패한다.")
    public void testVoteDemoday_Fail_VoteForSameTeam() {
        // given
        DemodayVoteCreateRequest request = new DemodayVoteCreateRequest(DEMO_DAY_VOTE_FOR_MY_TEAM.getTeamName());
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(DEMO_DAY_VOTE_FOR_MY_TEAM.getUser()));

        // when & then
        assertThatThrownBy(() -> voteService.voteTeam(request, DEMO_DAY_VOTE_FOR_MY_TEAM.getUser().getUsername()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionCode.VOTE_FOR_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("파트장 투표에 성공한다.")
    public void testVotePartLeader_Success() {
        // given
        PartLeaderVoteCreateRequest request = new PartLeaderVoteCreateRequest(VoteFixture.BACKEND_PART_LEADER_VOTE.getPartLeader().getUsername());
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(BACKEND_PART_LEADER_VOTE.getUser()));
        given(voteRepository.save(any(PartLeaderVote.class))).willReturn(BACKEND_PART_LEADER_VOTE);

        // when
        PartLeaderVote vote = voteService.votePartLeader(request, BACKEND_PART_LEADER_VOTE.getPartLeader().getUsername());

        // then
        assertThat(vote).usingRecursiveComparison().isEqualTo(BACKEND_PART_LEADER_VOTE);
    }

    @Test
    @DisplayName("다른 파트에 투표하면 파트장 투표에 실패한다.")
    public void testVotePartLeader_Fail_VoteForDifferentTeam() {
        // given
        String frontendUsername = FRONTEND_PART_LEADER_VOTE.getPartLeader().getUsername();
        String backendUsername = BACKEND_PART_LEADER_VOTE.getPartLeader().getUsername();
        PartLeaderVoteCreateRequest frontendPartLeaderRequest = new PartLeaderVoteCreateRequest(frontendUsername);
        given(userRepository.findByUsername(frontendUsername)).willReturn(Optional.of(FRONTEND_PART_LEADER_VOTE.getUser()));
        given(userRepository.findByUsername(backendUsername)).willReturn(Optional.of(BACKEND_PART_LEADER_VOTE.getUser()));

        // when & then
        assertThatThrownBy(() -> voteService.votePartLeader(frontendPartLeaderRequest, backendUsername)).isInstanceOf(
                BadRequestException.class)
                .hasMessage(ExceptionCode.VOTE_FOR_DIFFERENT_PART.getMessage());
    }
}
