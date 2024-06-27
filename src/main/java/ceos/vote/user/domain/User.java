package ceos.vote.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import ceos.vote.common.domain.BaseTimeEntity;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;

    private String email;

    private String name;

    private Part part;

    private TeamName teamName;

    @Builder
    public User(Long id, String username, String password, String email, String name, Part part,
            TeamName teamName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.part = part;
        this.teamName = teamName;
    }

    public boolean isDifferentPart(User user) {
        return this.part != user.getPart();
    }

    public boolean isSameTeam(TeamName teamName) {
        return this.teamName == teamName;
    }

    public boolean isSameUser(String username) {
        return this.username.equals(username);
    }

    public boolean isSamePart(Part part) {
        return this.part == part;
    }
}
