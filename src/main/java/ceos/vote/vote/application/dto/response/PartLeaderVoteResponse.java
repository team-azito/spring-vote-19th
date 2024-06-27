package ceos.vote.vote.application.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record PartLeaderVoteResponse (
    String username,
    String name,
    long voteCount
) {}
