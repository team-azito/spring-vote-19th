package ceos.vote.vote.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import ceos.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("P")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartLeaderVote extends Vote {

    @JoinColumn(name = "part_leader_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User partLeader;

    public PartLeaderVote(final User partLeader, final User user) {
        this.partLeader = partLeader;
        this.user = user;
    }
}
