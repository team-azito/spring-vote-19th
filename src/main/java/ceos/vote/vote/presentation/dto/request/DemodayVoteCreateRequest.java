package ceos.vote.vote.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import ceos.vote.user.domain.TeamName;
import lombok.Getter;

@Getter
public class DemodayVoteCreateRequest {
    @NotNull
    TeamName teamName;
}
