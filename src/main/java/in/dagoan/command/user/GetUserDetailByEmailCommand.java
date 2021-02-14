package in.dagoan.command.user;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.user.GetUserEmailRequest;
import in.dagoan.model.request.user.GetUserRequest;
import in.dagoan.model.response.GetUserDetailResponse;

public interface GetUserDetailByEmailCommand extends Command<GetUserEmailRequest, GetUserDetailResponse>  {
}
