package ceos.vote.vote.fixture;

import ceos.vote.user.domain.Part;
import ceos.vote.user.domain.TeamName;
import ceos.vote.user.domain.User;
import ceos.vote.vote.domain.DemoDayVote;

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
}
