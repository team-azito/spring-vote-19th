package ceos.vote.vote.application.dto.response;

import ceos.vote.user.domain.TeamName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@AllArgsConstructor
public class DemodayVoteResponse {

    private TeamName teamName;
    private long voteCount;
}
