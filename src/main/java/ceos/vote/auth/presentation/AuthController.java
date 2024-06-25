package ceos.vote.auth.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceos.vote.user.application.UserService;
import ceos.vote.user.application.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> checkAuth(@AuthenticationPrincipal String username) {
        UserResponse response = userService.findByUsername(username);
        return ResponseEntity.ok().body(response);
    }
}
