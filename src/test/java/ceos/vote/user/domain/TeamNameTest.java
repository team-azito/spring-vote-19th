package ceos.vote.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.user.exception.UserException;

class TeamNameTest {

    @Test
    @DisplayName("팀 이름이 존재하는 경우 팀 이름을 반환한다.")
    void testFindTeamName_Success() {
        // given
        String teamName = "Azito";

        // when
        TeamName result = TeamName.findTeamName(teamName);

        // then
        assertThat(result).isEqualTo(TeamName.AZITO);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Azitoo", "BitBuddy", "Bul", "Couple", "PIG"})
    @DisplayName("팀 이름이 존재하지 않는 경우 예외를 발생시킨다.")
    void testFindTeamName_Fail(String teamName) {
        assertThatThrownBy(() -> TeamName.findTeamName(teamName))
                .isInstanceOf(UserException.class)
                .hasMessageContaining( ExceptionCode.INVALID_TEAM_NAME.getMessage());
    }
}
