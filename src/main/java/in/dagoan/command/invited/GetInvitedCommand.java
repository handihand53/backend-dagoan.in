package in.dagoan.command.invited;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.invited.GetInvitedRequest;
import in.dagoan.model.response.invited.InvitedResponse;

public interface GetInvitedCommand extends Command<GetInvitedRequest, InvitedResponse> {
}
