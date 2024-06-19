package ceos.vote.vote.fixture;

import ceos.vote.user.domain.Part;
import ceos.vote.user.domain.TeamName;
import ceos.vote.user.domain.User;
import ceos.vote.vote.domain.DemoDayVote;
import ceos.vote.vote.domain.PartLeaderVote;

public class VoteFixture {

    private static final User USER_1 = User.builder()
            .id(1L)
            .username("hello")
            .password("hotekvkx124!")
            .email("test@email.com")
            .name("이도현")
            .part(Part.BACK_END)
            .teamName(TeamName.AZITO)
            .build();

    private static final User USER_2 = User.builder()
            .id(2L)
            .username("hello2")
            .password("hotekvkx124!")
            .email("test2@email.com")
            .name("조유담")
            .part(Part.FRONT_END)
            .teamName(TeamName.AZITO)
            .build();

    private static final User BACKEND_PART_LEADER = User.builder()
            .id(3L)
            .username("hello3")
            .password("hotekvkx124!")
            .email("test3@email.com")
            .name("이도현2")
            .part(Part.BACK_END)
            .teamName(TeamName.AZITO)
            .build();

    private static final User FRONTEND_PART_LEADER = User.builder()
            .id(4L)
            .username("hello4")
            .password("hotekvkx124!")
            .email("test4@email.com")
            .name("조유담2")
            .part(Part.FRONT_END)
            .teamName(TeamName.AZITO)
            .build();

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
