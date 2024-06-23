package ceos.vote.user.fixture;

import ceos.vote.user.domain.Part;
import ceos.vote.user.domain.TeamName;
import ceos.vote.user.domain.User;

public final class UserFixture {

    public static final User USER_1 = User.builder()
            .id(1L)
            .username("hello")
            .password("hotekvkx124!")
            .email("test@email.com")
            .name("이도현")
            .part(Part.BACK_END)
            .teamName(TeamName.AZITO)
            .build();

    public static final User USER_2 = User.builder()
            .id(2L)
            .username("hello2")
            .password("hotekvkx124!")
            .email("test2@email.com")
            .name("조유담")
            .part(Part.FRONT_END)
            .teamName(TeamName.AZITO)
            .build();

    public static final User BACKEND_PART_LEADER = User.builder()
            .id(3L)
            .username("hello3")
            .password("hotekvkx124!")
            .email("test3@email.com")
            .name("이도현2")
            .part(Part.BACK_END)
            .teamName(TeamName.AZITO)
            .build();

    public static final User FRONTEND_PART_LEADER = User.builder()
            .id(4L)
            .username("hello4")
            .password("hotekvkx124!")
            .email("test4@email.com")
            .name("조유담2")
            .part(Part.FRONT_END)
            .teamName(TeamName.AZITO)
            .build();

    private UserFixture() {
    }
}
