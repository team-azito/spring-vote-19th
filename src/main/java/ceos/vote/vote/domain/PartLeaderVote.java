package ceos.vote.vote.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import ceos.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("P")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartLeaderVote extends Vote {

    @JoinColumn(name = "part_leader_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User partLeader;
}
