package ceos.vote.vote.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import ceos.vote.user.domain.TeamName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record DemodayVoteCreateRequest (
        @NotNull(message = "팀 이름은 필수입니다.") TeamName teamName
) {}
