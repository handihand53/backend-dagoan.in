package in.dagoan.commandImpl.auth;

import in.dagoan.command.auth.RegisterUserCommand;
import in.dagoan.entity.document.User;
import in.dagoan.enums.UserRole;
import in.dagoan.model.request.RegisterUserRequest;
import in.dagoan.model.response.RegisterUserResponse;
import in.dagoan.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class RegisterUserCommandImpl implements RegisterUserCommand {

    private UserRepository userRepository;

    @Autowired
    public RegisterUserCommandImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Mono<RegisterUserResponse> execute(RegisterUserRequest request) {
//        if (this.isUserEmailMatch(request.getUserEmail())) {
//            return Mono.defer(() -> {
//                Exception ex = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already Exist");
//                return Mono.error(ex);
//            });
//        }
        return Mono.fromCallable(() -> toUser(request))
                .flatMap(user -> userRepository.save(user))
                .map(this::toResponse);
    }

    private User toUser(RegisterUserRequest request) {
        User user = User.builder()
                .userId(UUID.randomUUID())
                .userCreatedAt(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(request, user);
        user.setUserPassword(passwordEncoder().encode(user.getUserPassword()));
        user.setUserRoles(getUserRole());
        return user;
    }

    private boolean isUserEmailMatch(String email) {
        return userRepository.findFirstByUserEmail(email) != null;
    }

    private List<UserRole> getUserRole() {
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_USER);
        return roles;
    }

    private RegisterUserResponse toResponse(User user) {
        RegisterUserResponse response = new RegisterUserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }
}
