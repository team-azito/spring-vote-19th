package ceos.vote.vote.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import ceos.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

    @Builder
    public PartLeaderVote(final long id, final User user, final User partLeader) {
        this.id = id;
        this.user = user;
        this.partLeader = partLeader;
    }
}
