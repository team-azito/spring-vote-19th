package ceos.vote.vote.presentation;

import static ceos.vote.common.exception.ExceptionCode.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ceos.vote.common.exception.BadRequestException;
import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.config.SecurityConfig;
import ceos.vote.jwt.JwtUtil;
import ceos.vote.jwt.exception.InvalidJwtException;
import ceos.vote.user.application.UserService;
import ceos.vote.user.domain.Part;
import ceos.vote.user.domain.TeamName;
import ceos.vote.user.domain.repository.UserRepository;
import ceos.vote.vote.application.VoteService;
import ceos.vote.vote.application.dto.response.DemodayVoteResponse;
import ceos.vote.vote.application.dto.response.PartLeaderVoteResponse;
import ceos.vote.vote.domain.PartLeaderVote;
import ceos.vote.vote.domain.repository.VoteRepository;
import ceos.vote.vote.fixture.VoteFixture;
import ceos.vote.vote.presentation.docs.VoteDocs;
import ceos.vote.vote.presentation.dto.request.DemodayVoteCreateRequest;
import ceos.vote.vote.presentation.dto.request.PartLeaderVoteCreateRequest;
import java.util.List;
import java.util.Optional;

@WebMvcTest(VoteController.class)
@AutoConfigureRestDocs
@Import(SecurityConfig.class)
public class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private VoteService voteService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("데모데이 투표 결과 조회에 성공한다.")
    void testGetDemodayResult_Success() throws Exception {
        // given
        given(voteService.getDemoDayVoteResult()).willReturn(List.of(
                new DemodayVoteResponse(TeamName.AZITO, 5),
                new DemodayVoteResponse(TeamName.BEAT_BUDDY, 4),
                new DemodayVoteResponse(TeamName.BULDOG, 3),
                new DemodayVoteResponse(TeamName.COUPLE_LOG, 2),
                new DemodayVoteResponse(TeamName.TIG, 1)
        ));

        // when & then
        mockMvc.perform(get("/api/v1/votes/demoday/result"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(VoteDocs.getDemodayVoteResultDocument());
    }

    @Test
    @DisplayName("백엔드 파트장 투표 결과 조회에 성공한다.")
    void testGetPartLeaderVoteForBackendResult_Success() throws Exception {
        // given
        given(voteService.getPartLeaderVoteResult(Part.BACK_END)).willReturn(List.of(
                new PartLeaderVoteResponse("이도현1", 10),
                new PartLeaderVoteResponse("이도현3", 9),
                new PartLeaderVoteResponse("이도현2", 7),
                new PartLeaderVoteResponse("이도현4", 5)
        ));

        // when & then
        mockMvc.perform(
                get("/api/v1/votes/part-leader/result")
                        .param("part", "BACK_END")
                ).andDo(print())
                .andDo(VoteDocs.getPartLeaderVoteResultDocument())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("프론트엔드 파트장 투표 결과 조회에 성공한다.")
    void testGetPartLeaderVoteForFrontendResult_Success() throws Exception {
        // given
        given(voteService.getPartLeaderVoteResult(Part.FRONT_END)).willReturn(List.of(
                new PartLeaderVoteResponse("조유담1", 10),
                new PartLeaderVoteResponse("조유담3", 9),
                new PartLeaderVoteResponse("조유담2", 7),
                new PartLeaderVoteResponse("조유담4", 5)
        ));

        // when & then
        mockMvc.perform(
                        get("/api/v1/votes/part-leader/result")
                                .param("part", "FRONT_END")
                ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("존재하지 않는 파트를 쿼리스트링에 넣으면 파트장 투표 결과 조회에 실패한다.")
    void testGetPartLeaderVoteResult_Fail() throws Exception {
        // given

        // when & then
        mockMvc.perform(get("/api/v1/votes/part-leader/result")
                        .param("part", "FRONTEND")
                ).andDo(print())
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/v1/votes/part-leader/result")
                        .param("part", "BACKEND")
                ).andDo(print())
                .andDo(VoteDocs.getPartLeaderVoteResultFailDocument())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("데모데이 투표에 성공한다.")
    void testVoteDemoday_Success() throws Exception {
        // given
        DemodayVoteCreateRequest request = new DemodayVoteCreateRequest(TeamName.BEAT_BUDDY);
        given(jwtUtil.getUsername(anyString())).willReturn("user");
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(VoteFixture.DEMO_DAY_VOTE_1.getVoteUser()));

        // when & then
        mockMvc.perform(post("/api/v1/votes/demoday")
                        .header("Authorization", "Bearer abcd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(VoteDocs.voteDemodayDocument("vote/demoday/success"));
    }

    @Test
    @DisplayName("로그인하지 않았다면 데모데이 투표에 실패한다.")
    void testVoteDemodayWhenNotLoggedIn_Fail() throws Exception {
        // given
        DemodayVoteCreateRequest request = new DemodayVoteCreateRequest(TeamName.AZITO);
        doThrow(new InvalidJwtException(ExceptionCode.INVALID_JWT)).when(jwtUtil).validateJwt(any());

        // when & then
        mockMvc.perform(post("/api/v1/votes/demoday")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").value(INVALID_JWT.getMessage()))
                .andDo(print())
                .andDo(VoteDocs.voteDemodayDocument("vote/demoday/fail/notLoggedIn"));
    }

    @Test
    @DisplayName("데모데이 투표에 중복으로 투표하면 실패한다.")
    void testVoteDemodayWhenDuplicatedVote_Fail() throws Exception {
        // given
        DemodayVoteCreateRequest request = new DemodayVoteCreateRequest(TeamName.BEAT_BUDDY);
        given(jwtUtil.getUsername(anyString())).willReturn("user");
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(VoteFixture.DEMO_DAY_VOTE_1.getVoteUser()));
        doThrow(new BadRequestException(ExceptionCode.ALREADY_VOTED)).when(voteService).voteTeam(any(), anyString());

        // when & then
        mockMvc.perform(post("/api/v1/votes/demoday")
                        .header("Authorization", "Bearer abcd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ALREADY_VOTED.getMessage()))
                .andDo(print())
                .andDo(VoteDocs.voteDemodayDocument("vote/demoday/fail/duplicated"));
    }

    @Test
    @DisplayName("같은 팀이라면 데모데이 투표에 실패한다.")
    void testVoteDemodayWhenVoteForSameTeam_Fail() throws Exception {
        // given
        String testUsername = VoteFixture.DEMO_DAY_VOTE_1.getVoteUser().getUsername();
        DemodayVoteCreateRequest request = new DemodayVoteCreateRequest(TeamName.AZITO);
        given(jwtUtil.getUsername(anyString())).willReturn(testUsername);
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(VoteFixture.DEMO_DAY_VOTE_1.getVoteUser()));
        given(voteService.voteTeam(any(), anyString())).willThrow(new BadRequestException(VOTE_FOR_SAME_TEAM));

        // when & then
        mockMvc.perform(post("/api/v1/votes/demoday")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer abcd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(VOTE_FOR_SAME_TEAM.getMessage()))
                .andDo(print())
                .andDo(VoteDocs.voteDemodayDocument("vote/demoday/fail/myTeam"));
    }

    @Test
    @DisplayName("파트장 투표에 성공한다.")
    void testVotePartLeader_Success() throws Exception {
        // given
        PartLeaderVote vote = VoteFixture.BACKEND_PART_LEADER_VOTE;
        PartLeaderVoteCreateRequest request = new PartLeaderVoteCreateRequest(vote.getPartLeader().getUsername());
        given(jwtUtil.getUsername(anyString())).willReturn(vote.getVoteUser().getUsername());
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(vote.getVoteUser()));

        // when & then
        mockMvc.perform(post("/api/v1/votes/part-leader")
                        .header("Authorization", "Bearer abcd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(VoteDocs.votePartLeaderDocument("vote/part-leader/success"));
    }

    @Test
    @DisplayName("다른 파트에 투표하면 파트장 투표에 실패한다.")
    void testVotePartLeaderWhenVoteForDifferentPart_Fail() throws Exception {
        // given
        PartLeaderVote backendVote = VoteFixture.BACKEND_PART_LEADER_VOTE;
        PartLeaderVote frontendVote = VoteFixture.FRONTEND_PART_LEADER_VOTE;
        PartLeaderVoteCreateRequest request = new PartLeaderVoteCreateRequest(frontendVote.getPartLeader().getUsername());
        given(jwtUtil.getUsername(anyString())).willReturn(backendVote.getVoteUser().getUsername());
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(backendVote.getVoteUser()));
        given(voteService.votePartLeader(any(), anyString())).willThrow(new BadRequestException(VOTE_FOR_DIFFERENT_PART));

        // when & then
        mockMvc.perform(post("/api/v1/votes/part-leader")
                        .header("Authorization", "Bearer abcd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(VOTE_FOR_DIFFERENT_PART.getMessage()))
                .andDo(print())
                .andDo(VoteDocs.votePartLeaderDocument("vote/part-leader/fail/differentPart"));;
    }

    @Test
    @DisplayName("파트장 투표에 중복으로 투표하면 실패한다.")
    void testVotePartLeaderWhenDuplicatedVote_Fail() throws Exception {
        // given
        PartLeaderVote frontendVote = VoteFixture.FRONTEND_PART_LEADER_VOTE;
        PartLeaderVoteCreateRequest request = new PartLeaderVoteCreateRequest(frontendVote.getPartLeader().getUsername());
        given(jwtUtil.getUsername(anyString())).willReturn("user");
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(frontendVote.getVoteUser()));
        doThrow(new BadRequestException(ExceptionCode.ALREADY_VOTED)).when(voteService).votePartLeader(any(), anyString());

        // when & then
        mockMvc.perform(post("/api/v1/votes/part-leader")
                        .header("Authorization", "Bearer abcd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ALREADY_VOTED.getMessage()))
                .andDo(print())
                .andDo(VoteDocs.votePartLeaderDocument("vote/part-leader/fail/duplicated"));
    }
}
