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
import ceos.vote.vote.presentation.dto.request.DemodayVoteCreateRequest;
import ceos.vote.vote.presentation.dto.request.PartLeaderVoteCreateRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes")
public class VoteController {

    private final VoteService voteService;

    @GetMapping("/demoday")
    public ResponseEntity<Void> getDemodayVotes() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/demoday")
    public void voteForDemoday(@RequestBody @Valid DemodayVoteCreateRequest request, Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        voteService.voteTeam(request, username);
    }

    @GetMapping("/part-leader")
    public ResponseEntity<Void> getPartLeaderVotes() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/part-leader")
    public void voteForPartLeader(@RequestBody PartLeaderVoteCreateRequest request) {
        System.out.println(request);
    }

}
