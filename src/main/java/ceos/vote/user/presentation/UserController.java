package ceos.vote.user.presentation;

import java.net.URI;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceos.vote.user.application.UserService;
import ceos.vote.user.presentation.dto.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserCreateRequest request) {
        Long userId = userService.createUser(request);
        return ResponseEntity.created(URI.create("/api/v1/users/" + userId)).build();
    }
}
