package in.dagoan.commandImpl.auth;

import in.dagoan.command.user.GetUserDetailCommand;
import in.dagoan.entity.document.User;
import in.dagoan.model.response.GetUserDetailResponse;
import in.dagoan.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetUserDetailCommandImpl implements GetUserDetailCommand {

    private UserRepository userRepository;

    @Autowired
    public GetUserDetailCommandImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<GetUserDetailResponse> execute(String email) {
        return userRepository.findFirstByUserEmail(email)
                .switchIfEmpty(Mono.error(new NotFoundException("Email not found!")))
                .map(this::toResponse);
    }

    private GetUserDetailResponse toResponse(User user) {
        GetUserDetailResponse response = new GetUserDetailResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

}

