package in.dagoan.commandImpl.user;

import in.dagoan.command.user.GetUserDetailCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.document.User;
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
public class GetUserDetailCommandImpl implements GetUserDetailCommand {
    private UserRepository userRepository;

    @Autowired
    public GetUserDetailCommandImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<GetUserDetailResponse> execute(GetUserRequest request) {
        return userRepository.findFirstByUserId(request.getUserId())
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
