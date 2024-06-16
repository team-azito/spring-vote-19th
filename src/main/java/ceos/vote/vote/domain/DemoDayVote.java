package ceos.vote.vote.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import ceos.vote.user.domain.TeamName;

@Entity
@DiscriminatorValue("D")
public class DemoDayVote extends Vote {

    private TeamName teamName;
}
