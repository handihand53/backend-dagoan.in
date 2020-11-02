package in.dagoan.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import in.dagoan.ApiPath;
import in.dagoan.command.auth.RegisterUserCommand;
import in.dagoan.commandImpl.auth.RegisterUserCommandImpl;
import in.dagoan.entity.document.User;
import in.dagoan.entity.form.UserRoleForm;
import in.dagoan.model.request.LoginUserRequest;
import in.dagoan.model.request.RegisterUserRequest;
import in.dagoan.model.response.LoginUserResponse;
import in.dagoan.model.response.RegisterUserResponse;
import in.dagoan.repository.UserRepository;
import in.dagoan.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private CommandExecutor commandExecutor;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(CommandExecutor commandExecutor, AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider) {
        this.commandExecutor = commandExecutor;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping(value = ApiPath.REGISTER,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<RegisterUserResponse>> registerUser(@RequestBody RegisterUserRequest request) {
        return commandExecutor.execute(RegisterUserCommand.class, request)
                .log("#CommandExecutor - Executing RegisterUserCommand...")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

       @PostMapping(value = ApiPath.LOGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginUserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserEmail(),
                        request.getUserPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserRoleForm roleForm = tokenProvider.generateUserData(request.getUserEmail());
        return ResponseEntity.ok(new LoginUserResponse(roleForm.getUserId(), roleForm.getUserRoles(), jwt));
    }

}
