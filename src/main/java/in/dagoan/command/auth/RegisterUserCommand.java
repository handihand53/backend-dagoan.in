package in.dagoan.command.auth;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.RegisterUserRequest;
import in.dagoan.model.response.RegisterUserResponse;

public interface RegisterUserCommand extends Command<RegisterUserRequest, RegisterUserResponse> {
}