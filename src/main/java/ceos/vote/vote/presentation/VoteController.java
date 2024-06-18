package ceos.vote.vote.presentation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceos.vote.vote.application.VoteService;
import ceos.vote.vote.application.dto.response.DemodayVoteResponse;
import ceos.vote.vote.presentation.dto.request.DemodayVoteCreateRequest;
import ceos.vote.vote.presentation.dto.request.PartLeaderVoteCreateRequest;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes")
public class VoteController {

    private final VoteService voteService;

    @GetMapping("/demoday/result")
    public ResponseEntity<List<DemodayVoteResponse>> getDemodayVotes() {
        List<DemodayVoteResponse> responses = voteService.getDemoDayVotes();
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/demoday")
    public ResponseEntity<Void> voteForDemoday(@RequestBody @Valid DemodayVoteCreateRequest request, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        voteService.voteTeam(request, username);
        return ResponseEntity.created(URI.create("")).build();
    }

    @GetMapping("/part-leader")
    public ResponseEntity<Void> getPartLeaderVotes() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/part-leader")
    public ResponseEntity<Void> voteForPartLeader(@RequestBody @Valid PartLeaderVoteCreateRequest request, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        voteService.votePartLeader(request, username);
        return ResponseEntity.created(URI.create("")).build();
    }
}
