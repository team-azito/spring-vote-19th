package ceos.vote.user.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

import ceos.vote.user.domain.Part;
import ceos.vote.user.domain.TeamName;
import ceos.vote.user.domain.User;
import lombok.Builder;

@Builder
public record UserCreateRequest(@NotBlank(message = "아이디는 필수 입력 값입니다.")
                                String username,
                                @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
                                @Pattern(regexp = "(?i)^(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,20}$",
                                        message = "비밀번호는 8~20자의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.")
                                String password,
                                @NotBlank(message = "이메일은 필수 입력 값입니다.")
                                @Email(message = "이메일 형식이 올바르지 않습니다.")
                                String email,
                                @NotBlank(message = "이름은 필수 입력 값입니다.")
                                String name,
                                @NotBlank(message = "파트는 필수 입력 값입니다.")
                                String part,
                                @NotBlank(message = "팀 이름은 필수 입력 값입니다.")
                                String teamName) {

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .name(name)
                .part(Part.findPart(part))
                .teamName(TeamName.findTeamName(teamName))
                .build();
    }
}
