package ceos.vote.vote.fixture;

import static ceos.vote.user.fixture.UserFixture.BACKEND_PART_LEADER;
import static ceos.vote.user.fixture.UserFixture.FRONTEND_PART_LEADER;
import static ceos.vote.user.fixture.UserFixture.USER_1;
import static ceos.vote.user.fixture.UserFixture.USER_2;

import ceos.vote.user.domain.TeamName;
import ceos.vote.vote.domain.DemoDayVote;
import ceos.vote.vote.domain.PartLeaderVote;

public class VoteFixture {

    public static final DemoDayVote DEMO_DAY_VOTE_1 = DemoDayVote.builder()
            .id(1L)
            .user(USER_1)
            .teamName(TeamName.BEAT_BUDDY)
            .build();

    public static final DemoDayVote DEMO_DAY_VOTE_FOR_MY_TEAM = DemoDayVote.builder()
            .id(1L)
            .user(USER_1)
            .teamName(TeamName.AZITO)
            .build();

    public static final PartLeaderVote BACKEND_PART_LEADER_VOTE = PartLeaderVote.builder()
            .id(2L)
            .user(USER_1)
            .partLeader(BACKEND_PART_LEADER)
            .build();

    public static final PartLeaderVote FRONTEND_PART_LEADER_VOTE = PartLeaderVote.builder()
            .id(3L)
            .user(USER_2)
            .partLeader(FRONTEND_PART_LEADER)
            .build();
}
