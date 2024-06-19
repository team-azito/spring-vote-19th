package ceos.vote.vote.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import ceos.vote.common.domain.BaseTimeEntity;
import ceos.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "votes")
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "vote_type")
public class Vote extends BaseTimeEntity {

    @Id
    @Column(name = "vote_id")
    @GeneratedValue
    protected long id;

    @JoinColumn(name = "vote_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    protected User user;
}
