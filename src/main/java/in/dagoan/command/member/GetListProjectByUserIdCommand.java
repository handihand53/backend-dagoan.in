package in.dagoan.command.member;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.member.GetListProjectWithUserIdRequest;
import in.dagoan.model.response.member.GetListProjectWithUserIdResponse;

public interface GetListProjectByUserIdCommand extends Command<GetListProjectWithUserIdRequest, GetListProjectWithUserIdResponse> {
}