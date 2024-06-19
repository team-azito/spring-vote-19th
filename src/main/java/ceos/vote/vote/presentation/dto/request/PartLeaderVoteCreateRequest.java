package ceos.vote.vote.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import ceos.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PartLeaderVoteCreateRequest {
    @NotNull
    private String partLeaderUsername;
}
