package in.dagoan.command.user;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.user.GetUserRequest;
import in.dagoan.model.response.GetUserDetailResponse;

public interface GetUserDetailCommand extends Command<GetUserRequest, GetUserDetailResponse>  {
}
