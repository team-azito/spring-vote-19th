package ceos.vote.vote.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import ceos.vote.user.domain.User;
import lombok.Getter;

@Getter
public class PartLeaderVoteCreateRequest {
    @NotNull
    String partLeaderUsername;
}
