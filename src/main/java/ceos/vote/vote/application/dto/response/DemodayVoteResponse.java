package ceos.vote.vote.application.dto.response;

import ceos.vote.user.domain.TeamName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


public record DemodayVoteResponse(
        TeamName teamName,
        long voteCount
) {}
