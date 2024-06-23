package ceos.vote.vote.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import ceos.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record PartLeaderVoteCreateRequest (
        @NotBlank(message = "파트장 username은 필수입니다.") String partLeaderUsername
) {}
