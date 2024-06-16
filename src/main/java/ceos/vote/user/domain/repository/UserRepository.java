package ceos.vote.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ceos.vote.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
