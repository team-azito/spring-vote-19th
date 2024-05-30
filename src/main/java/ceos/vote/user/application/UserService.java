package ceos.vote.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ceos.vote.common.exception.ExceptionCode;
import ceos.vote.user.domain.repository.UserRepository;
import ceos.vote.user.exception.AlreadyExistException;
import ceos.vote.user.presentation.dto.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new AlreadyExistException(ExceptionCode.ALREADY_EXIST_USERNAME);
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new AlreadyExistException(ExceptionCode.ALREADY_EXIST_EMAIL);
        }
        return userRepository.save(request.toEntity()).getId();
    }
}
