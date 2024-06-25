package ceos.vote.user.application.dto.response;

import ceos.vote.user.domain.User;
import lombok.Builder;

@Builder
public record UserResponse(Long id, String username, String email, String name, String part, String teamName) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getPart().getValue(), user.getTeamName().getName());
    }
}
