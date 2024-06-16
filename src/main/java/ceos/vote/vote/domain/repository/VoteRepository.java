package ceos.vote.vote.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ceos.vote.vote.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
