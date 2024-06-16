package ceos.vote.vote.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import ceos.vote.user.domain.User;

@Entity
@DiscriminatorValue("P")
public class PartLeaderVote extends Vote {

    @JoinColumn(name = "part_leader_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User partLeader;
}
