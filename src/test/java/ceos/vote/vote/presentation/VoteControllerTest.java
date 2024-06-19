package ceos.vote.vote.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import ceos.vote.config.SecurityConfig;
import ceos.vote.jwt.JwtUtil;
import ceos.vote.user.domain.Part;
import ceos.vote.user.domain.TeamName;
import ceos.vote.user.domain.repository.UserRepository;
import ceos.vote.vote.application.VoteService;
import ceos.vote.vote.application.dto.response.DemodayVoteResponse;
import ceos.vote.vote.application.dto.response.PartLeaderVoteResponse;
import ceos.vote.vote.presentation.docs.VoteDocs;
import java.util.List;

@WebMvcTest(VoteController.class)
@AutoConfigureRestDocs
@Import(SecurityConfig.class)
public class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}
