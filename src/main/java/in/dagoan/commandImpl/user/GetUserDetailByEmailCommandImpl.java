package in.dagoan.commandImpl.user;

import in.dagoan.command.user.GetUserDetailByEmailCommand;
import in.dagoan.command.user.GetUserDetailCommand;
import in.dagoan.entity.document.User;
import in.dagoan.model.request.user.GetUserEmailRequest;
import in.dagoan.model.request.user.GetUserRequest;
import in.dagoan.model.response.GetUserDetailResponse;
import in.dagoan.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetUserDetailByEmailCommandImpl implements GetUserDetailByEmailCommand {
    private UserRepository userRepository;

    @Autowired
    public GetUserDetailByEmailCommandImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<GetUserDetailResponse> execute(GetUserEmailRequest request) {
        return userRepository.findFirstByUserEmail(request.getUserEmail())
                .switchIfEmpty(Mono.error(new NotFoundException("User not found!")))
                .map(user -> toResponse(user));
    }

    private GetUserDetailResponse toResponse(User user) {
        GetUserDetailResponse response = new GetUserDetailResponse();
        response.setUserEmail(user.getUserEmail());
        response.setUserId(user.getUserId());
        response.setUserName(user.getUserName());
        return response;
    }
}
