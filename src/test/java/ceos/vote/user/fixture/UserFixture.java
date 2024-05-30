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

    private UserFixture() {
    }
}
