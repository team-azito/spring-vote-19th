package ceos.vote.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.user.exception.UserException;

class PartTest {

    @Test
    @DisplayName("파트가 존재하는 경우 파트를 반환한다.")
    void testValidPart() {
        // given
        String part = "백엔드";

        // when
        Part result = Part.findPart(part);

        // then
        assertThat(result).isEqualTo(Part.BACK_END);
    }

    @ParameterizedTest
    @ValueSource(strings = {"프론트", "백"})
    void testInvalidPart(String part) {
        assertThatThrownBy(() -> Part.findPart(part))
            .isInstanceOf(UserException.class)
            .hasMessageContaining(ExceptionCode.INVALID_PART.getMessage());
    }
}
