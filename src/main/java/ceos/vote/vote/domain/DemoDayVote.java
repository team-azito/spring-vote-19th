package ceos.vote.vote.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import ceos.vote.user.domain.TeamName;
import ceos.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("D")
public class DemoDayVote extends Vote {

    private TeamName teamName;

    public DemoDayVote(TeamName teamName, User user) {
        this.user = user;
        this.teamName = teamName;
    }

    @Builder
    public DemoDayVote(long id, TeamName teamName, User user) {
        this.id = id;
        this.teamName = teamName;
        this.user = user;
    }
}
